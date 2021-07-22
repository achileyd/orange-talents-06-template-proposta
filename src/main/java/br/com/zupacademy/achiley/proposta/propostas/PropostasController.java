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

@RestController
public class PropostasController {
	
	@Autowired
	private PropostaRepository repository;
	
	@PostMapping(value="/propostas")
	@Transactional
	public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
		Optional <Proposta> optional = repository.findByDocumento(request.getDocumento());
		
		if(!optional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Proposta proposta = request.converter();
		repository.save(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").build(proposta.getId());
		
		return ResponseEntity.created(uri).build();
	}
}
