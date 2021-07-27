package br.com.zupacademy.achiley.proposta.propostas;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ConsultaPropostaController {
	
	private PropostaRepository repository;
	
	private final Logger logger = LoggerFactory.getLogger(ConsultaPropostaController.class);
	
	@Autowired
	public ConsultaPropostaController(PropostaRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/propostas/{id}")
	public ResponseEntity<DetalhesDaPropostaResponse> consultar(@PathVariable Long id) {
		Optional<Proposta> proposta = repository.findById(id);
		
		if(!proposta.isPresent()) {
			logger.info("Proposta {} nao encontrada.", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		logger.info("Proposta {} consultada.", id);
		return ResponseEntity.ok(new DetalhesDaPropostaResponse(proposta.get()));
	}
}
