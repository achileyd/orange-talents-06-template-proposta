package br.com.zupacademy.achiley.proposta.biometria;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import io.opentracing.Span;
import io.opentracing.Tracer;
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
public class NovaBiometriaController {
	
	private CartaoRepository repository;
	private ContextoTransacional transacional;

	private final Tracer tracer;
	private final Logger logger = LoggerFactory.getLogger(NovaBiometriaController.class);
	
	@Autowired
	public NovaBiometriaController(CartaoRepository repository, ContextoTransacional transacional, Tracer tracer) {
		this.repository = repository;
		this.transacional = transacional;
		this.tracer = tracer;
	}

	@PostMapping(value = "cartoes/{id}/biometria")
	@Transactional
	public ResponseEntity<?> criar(@PathVariable Long id,
			@RequestBody @Valid NovaBiometriaRequest request, UriComponentsBuilder uriBuilder) {
		Cartao cartao = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartao inexistente"));

		Span activeSpan = tracer.activeSpan().setBaggageItem("user.email", cartao.getProposta().getEmail());

		Biometria biometria = new Biometria(request.getFingerPrint(), cartao);
		transacional.persiste(biometria);

		logger.info("Biometria {} criada com sucesso!", biometria.getId());

		cartao.adicionaBiometria(biometria);
		transacional.atualiza(cartao);

		logger.info("Cartao {} atualizado com nova biometria: {}.", cartao.getId(), biometria.getId() );

		URI uri = uriBuilder.path("/cartoes/{id}/biometria/{biometriaId}").build(id, biometria.getId());

		return ResponseEntity.created(uri).build();
	}
}
