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
public class NovoSolicitacaoDeBloqueioController {
	
	private CartaoRepository repository;
	private ContextoTransacional transacional;
	
	private final Logger logger = LoggerFactory.getLogger(NovoSolicitacaoDeBloqueioController.class);
	
	@Autowired
	public NovoSolicitacaoDeBloqueioController(CartaoRepository repository, ContextoTransacional transacional) {
		this.repository = repository;
		this.transacional = transacional;
	}
	
	@PostMapping(value = "cartoes/{id}/bloqueio")
	@Transactional
	public ResponseEntity<?> bloquear(@PathVariable Long id, HttpServletRequest httpRequest) {
		String ip = httpRequest.getRemoteAddr();
		String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
		
		if(ip.isBlank() || userAgent.isBlank()) {
			logger.info("Ip ou user-agent vazios." +
					     " user-agent: {}, ip: {}", userAgent, ip);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					  "O ip e o user-agent nao podem estar vazios");
		}
		
		Cartao cartao = repository.findById(id)
						.orElseThrow(() 
						-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartao inexistente"));
		
		if (cartao.isBloqueado()){
            logger.info("A tentativa de bloqueio falhou." +
            		    " O cartão {} já esta bloqueado", cartao.getNumero());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão ja está bloqueado");
        }
		BloqueioDeCartoes novoBloqueio = new BloqueioDeCartoes(ip, userAgent, cartao);
		transacional.persiste(novoBloqueio);
		
		cartao.bloqueia(novoBloqueio);
		transacional.atualiza(cartao);
		
		return ResponseEntity.ok().build();
		
	}
}