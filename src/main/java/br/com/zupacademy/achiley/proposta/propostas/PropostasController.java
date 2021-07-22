package br.com.zupacademy.achiley.proposta.propostas;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class PropostasController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@PostMapping(value="/propostas")
	@Transactional
	public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
		Proposta proposta = request.converter();
		manager.persist(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").build(proposta.getId());
		
		return ResponseEntity.created(uri).build();
	}
}
