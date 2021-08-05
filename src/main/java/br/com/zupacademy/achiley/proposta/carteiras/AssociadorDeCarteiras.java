package br.com.zupacademy.achiley.proposta.carteiras;

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
public class AssociadorDeCarteiras {
	@Autowired
	private IntegracaoCartoes integracao;

	private final Logger log = LoggerFactory.getLogger(AssociadorDeCarteiras.class);

	public void associaCarteira(@NotNull @Validated Cartao cartao, NovaAssociacaoDeCarteiraRequest request) {
		try {
			NovaAssociacaoDeCarteiraResponse resposta = integracao.associaCarteira(cartao.getNumero(), request);
			log.info("Associacao do cartao {} a carteira {} retornou {}.", cartao.getNumero(), request.getCarteira(),resposta.getResultado());
		} catch (FeignException e) {
			log.error("Não foi possivel associar o cartão {} a carteira {} " +
					  ". Motivo: {}", cartao.getNumero(),request.getCarteira(), e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, 
										"Não foi possível associar o cartão nesse momento");
		}	
	}
}
