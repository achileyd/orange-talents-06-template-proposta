package br.com.zupacademy.achiley.proposta.cartao;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zupacademy.achiley.proposta.propostas.Proposta;

public class CartaoRequest {
	@JsonProperty("idProposta")
	@NotNull
	private final Long propostaId;

	public CartaoRequest(Proposta proposta) {
		this.propostaId = proposta.getId();
	}
	
}
