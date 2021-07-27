package br.com.zupacademy.achiley.proposta.integracoes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.zupacademy.achiley.proposta.propostas.cartoes.CartaoRequest;
import br.com.zupacademy.achiley.proposta.propostas.cartoes.CartaoResponse;


@FeignClient(url = "${cartoes.url}", name = "cartoes")
public interface IntegracaoCartoes {
	
	@GetMapping
	public CartaoResponse solicitaNumeroDoCartao(CartaoRequest request);
}
