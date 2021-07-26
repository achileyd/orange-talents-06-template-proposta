package br.com.zupacademy.achiley.proposta.propostas.analise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnaliseResponse {

    @JsonProperty("resultadoSolicitacao")
    private final String status;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AnaliseResponse(String status) {
        this.status = status;
    }

	public String getStatus() {
		return status;
	}
    
}
