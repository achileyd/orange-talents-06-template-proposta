package br.com.zupacademy.achiley.proposta.integracoes;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.zupacademy.achiley.proposta.propostas.cartoes.CartaoRequest;
import br.com.zupacademy.achiley.proposta.propostas.cartoes.CartaoResponse;

@FeignClient(url = "${cartoes.url}", name = "cartoes")
public interface IntegracaoCartoes {

	@GetMapping
	public CartaoResponse solicitaNumeroDoCartao(CartaoRequest request);

	@PostMapping("/{id}/bloqueios")
	public String notificaBloqueio(@PathVariable("id") String numeroDoCartao, 
								   Map<String, String> request);
}
