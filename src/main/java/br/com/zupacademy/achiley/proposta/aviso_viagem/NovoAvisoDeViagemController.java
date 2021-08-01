package br.com.zupacademy.achiley.proposta.aviso_viagem;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupacademy.achiley.proposta.bloqueio.NovaSolicitacaoDeBloqueioController;
import br.com.zupacademy.achiley.proposta.cartao.Cartao;
import br.com.zupacademy.achiley.proposta.cartao.CartaoRepository;
import br.com.zupacademy.achiley.proposta.shared.ContextoTransacional;

@RestController
public class NovoAvisoDeViagemController {
	
	private CartaoRepository repository;
	private ContextoTransacional transacional;

	private final Logger log = LoggerFactory.getLogger(NovaSolicitacaoDeBloqueioController.class);
	
	@Autowired
	public NovoAvisoDeViagemController(CartaoRepository repository, ContextoTransacional transacional) {
		this.repository = repository;
		this.transacional = transacional;
	}
	
	@PostMapping(value = "cartoes/{id}/aviso-de-viagem")
	@Transactional
	public ResponseEntity<?> avisar(@PathVariable Long id, @RequestBody @Valid NovoAvisoDeViagemRequest request,
									HttpServletRequest httpRequest) {
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
		
		AvisoDeViagem novoAviso = request.converter(ip, userAgent, cartao);
		transacional.persiste(novoAviso);
		
		cartao.adicionaAvisoDeViagem(novoAviso);
		transacional.atualiza(cartao);
		
		return ResponseEntity.ok().build();
	}

}
