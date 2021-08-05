package br.com.zupacademy.achiley.proposta.carteiras;

import java.net.URI;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;
import br.com.zupacademy.achiley.proposta.cartao.CartaoRepository;
import br.com.zupacademy.achiley.proposta.shared.ContextoTransacional;

@RestController
public class NovaAssociacaoDeCarteiraController {
	private CartaoRepository repository;
	private ContextoTransacional transacional;
	private AssociadorDeCarteiras associador;
	
	private final Logger log = LoggerFactory.getLogger(NovaAssociacaoDeCarteiraController.class);
	
	@Autowired
	public NovaAssociacaoDeCarteiraController(CartaoRepository repository, ContextoTransacional transacional,
			AssociadorDeCarteiras associador) {
		this.repository = repository;
		this.transacional = transacional;
		this.associador = associador;
	}

	@PostMapping(value = "cartoes/{id}/carteiras")
	@Transactional
	public ResponseEntity<?> associar(@PathVariable Long id, @RequestBody NovaAssociacaoDeCarteiraRequest request, UriComponentsBuilder uriBuilder) {
		
		Cartao cartao = repository.findById(id)
						.orElseThrow(() 
						-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartao inexistente"));
		
		Carteira novaCarteira =request.converter(cartao);
		
		if (cartao.possuiAssociacaoComEstaCarteira(novaCarteira)){
            log.info("A tentativa de bloqueio falhou." +
            		    " O cartão {} já esta associado a uma carteira {}", cartao.getNumero(), request.getCarteira());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão ja está associado a esta carteira");
        }
		
		associador.associaCarteira(cartao, request);
		transacional.persiste(novaCarteira);
		
		cartao.associaCarteira(novaCarteira);
		transacional.atualiza(cartao);
		
		URI uri = uriBuilder.path("/cartoes/{id}/carteiras/{carteiraId}").build(id, novaCarteira.getId());
		
		return ResponseEntity.created(uri).build();
		
	}
}