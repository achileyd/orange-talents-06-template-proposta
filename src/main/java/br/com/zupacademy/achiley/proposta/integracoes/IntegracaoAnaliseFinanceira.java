package br.com.zupacademy.achiley.proposta.integracoes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.zupacademy.achiley.proposta.propostas.analise.AnaliseRequest;
import br.com.zupacademy.achiley.proposta.propostas.analise.AnaliseResponse;

@FeignClient(url = "${analise.url}", name = "analise")
public interface IntegracaoAnaliseFinanceira {
	
	@PostMapping
	public AnaliseResponse solicitaAnalise(AnaliseRequest request);
}
