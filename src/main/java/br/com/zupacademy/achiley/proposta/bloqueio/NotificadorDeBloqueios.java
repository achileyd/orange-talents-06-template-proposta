package br.com.zupacademy.achiley.proposta.bloqueio;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;
import br.com.zupacademy.achiley.proposta.integracoes.IntegracaoCartoes;
import feign.FeignException;

@Component
@Validated
public class NotificadorDeBloqueios {

	@Autowired
	private IntegracaoCartoes integracao;

	private final Logger log = LoggerFactory.getLogger(NotificadorDeBloqueios.class);

	public void notificaBloqueio(@NotNull @Validated Cartao cartao) {
		try {
			String resposta = integracao.notificaBloqueio(cartao.getNumero(), Map.of("sistemaResponsavel", "Propostas"));
		} catch (FeignException e) {
			log.error("Não foi possivel notificar o bloqueio para " +
					  "o cartão {}. Motivo: {}", cartao.getNumero(), e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, 
										"Não foi possível realizar o bloqueio do cartão nesse momento");
		}
	}

}
