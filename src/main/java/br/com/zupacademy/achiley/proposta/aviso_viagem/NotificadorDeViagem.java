package br.com.zupacademy.achiley.proposta.aviso_viagem;

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
public class NotificadorDeViagem {
	@Autowired
	private IntegracaoCartoes integracao;

	private final Logger log = LoggerFactory.getLogger(NotificadorDeViagem.class);

	public void notificaViagem(@NotNull @Validated NovoAvisoDeViagemRequest request, @NotNull @Validated Cartao cartao) {
		try {
			String resposta = integracao.notificaViagem(cartao.getNumero(), request);
			log.info("Notificacao de viagem para o cartao {} retornou {}.", cartao.getNumero(), resposta);
		} catch (FeignException e) {
			log.error("Não foi possivel notificar o aviso de viagem para " +
					  "o cartão {}. Motivo: {}", cartao.getNumero(), e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, 
											  "Não foi possível solicitar o aviso de viagem nesse momento");
		}
	}

}
