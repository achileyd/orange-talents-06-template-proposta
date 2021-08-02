package br.com.zupacademy.achiley.proposta.cartao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartaoResponse {
    @JsonProperty("id")
    private final String numeroDoCartao;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CartaoResponse(String numeroDoCartao2) {
        this.numeroDoCartao = numeroDoCartao2;
    }

	public String getNumeroDoCartao() {
		return numeroDoCartao;
	}
}
