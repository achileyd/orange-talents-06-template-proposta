package br.com.zupacademy.achiley.proposta.propostas;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.achiley.proposta.propostas.analise.AnalisadorDeProposta;
import br.com.zupacademy.achiley.proposta.shared.ContextoTransacional;

@RestController
public class PropostasController {
	
	private PropostaRepository repository;
	
	private ContextoTransacional transacional;
	
	private AnalisadorDeProposta analisador;
	
	
	@Autowired
	public PropostasController(PropostaRepository repository, ContextoTransacional transacional,
			AnalisadorDeProposta analisador) {
		this.repository = repository;
		this.transacional = transacional;
		this.analisador = analisador;
	}

	@PostMapping(value="/propostas")
	@Transactional
	public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
		Optional <Proposta> possivelProposta = repository.findByDocumento(request.getDocumento());
		
		if(!possivelProposta.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Proposta proposta = request.converter();
		transacional.persiste(proposta);
		
		StatusDaPropostaEnum resultadoDaAnalise = analisador.analisa(proposta);
		proposta.atualizaStatusDaProposta(resultadoDaAnalise);
		
		transacional.atualiza(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").build(proposta.getId());
		
		return ResponseEntity.created(uri).build();
	}

}
