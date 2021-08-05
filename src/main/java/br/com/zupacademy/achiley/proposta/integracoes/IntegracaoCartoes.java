package br.com.zupacademy.achiley.proposta.integracoes;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.zupacademy.achiley.proposta.aviso_viagem.NovoAvisoDeViagemRequest;
import br.com.zupacademy.achiley.proposta.cartao.CartaoRequest;
import br.com.zupacademy.achiley.proposta.cartao.CartaoResponse;
import br.com.zupacademy.achiley.proposta.carteiras.NovaAssociacaoDeCarteiraRequest;
import br.com.zupacademy.achiley.proposta.carteiras.NovaAssociacaoDeCarteiraResponse;

@FeignClient(url = "${cartoes.url}", name = "cartoes")
public interface IntegracaoCartoes {

	@GetMapping
	public CartaoResponse solicitaNumeroDoCartao(CartaoRequest request);

	@PostMapping("/{id}/bloqueios")
	public String notificaBloqueio(@PathVariable("id") String numeroDoCartao, 
								   Map<String, String> request);
	
	@PostMapping("/{id}/avisos")
	public String notificaViagem(@PathVariable("id") String numeroDoCartao, 
								   NovoAvisoDeViagemRequest request);
	
	@PostMapping("/{id}/carteiras")
	public NovaAssociacaoDeCarteiraResponse associaCarteira(@PathVariable("id") String numeroDoCartao, 
								   NovaAssociacaoDeCarteiraRequest request);
}
