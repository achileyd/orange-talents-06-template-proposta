package br.com.zupacademy.achiley.proposta.carteiras;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NovaAssociacaoDeCarteiraResponse {
	@JsonProperty("resultado")
	private String resultado;

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
