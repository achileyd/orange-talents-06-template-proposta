package br.com.zupacademy.achiley.proposta.bloqueio;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;
import br.com.zupacademy.achiley.proposta.cartao.CartaoRepository;
import br.com.zupacademy.achiley.proposta.shared.ContextoTransacional;

@RestController
public class NovaSolicitacaoDeBloqueioController {
	
	private CartaoRepository repository;
	private ContextoTransacional transacional;
	private NotificadorDeBloqueios notificador;
	
	private final Logger log = LoggerFactory.getLogger(NovaSolicitacaoDeBloqueioController.class);
	
	@Autowired
	public NovaSolicitacaoDeBloqueioController(CartaoRepository repository, ContextoTransacional transacional,
			NotificadorDeBloqueios notificador) {
		this.repository = repository;
		this.transacional = transacional;
		this.notificador = notificador;
	}

	@PostMapping(value = "cartoes/{id}/bloqueio")
	@Transactional
	public ResponseEntity<?> bloquear(@PathVariable Long id, HttpServletRequest httpRequest) {
		String ip = httpRequest.getRemoteAddr();
		String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
		
		if(ip.isBlank() || userAgent.isBlank()) {
			log.info("Ip ou user-agent vazios." +
					     " user-agent: {}, ip: {}", userAgent, ip);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					  "O ip e o user-agent nao podem estar vazios");
		}
		
		Cartao cartao = repository.findById(id)
						.orElseThrow(() 
						-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartao inexistente"));
		
		if (cartao.isBloqueado()){
            log.info("A tentativa de bloqueio falhou." +
            		    " O cartão {} já esta bloqueado", cartao.getNumero());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão ja está bloqueado");
        }
		notificador.notificaBloqueio(cartao);
		BloqueioDeCartoes novoBloqueio = new BloqueioDeCartoes(ip, userAgent, cartao);
		transacional.persiste(novoBloqueio);
		
		cartao.bloqueia(novoBloqueio);
		transacional.atualiza(cartao);
		
		return ResponseEntity.ok().build();
		
	}
}