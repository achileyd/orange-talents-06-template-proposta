package br.com.zupacademy.achiley.proposta.propostas.analise;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zupacademy.achiley.proposta.propostas.Proposta;
import br.com.zupacademy.achiley.proposta.shared.Document;

public class AnaliseRequest {
	@JsonProperty("documento")
    @Document 
    @NotBlank
    private final String documento;

    @JsonProperty("nome")
    @NotBlank
    private final String nome;

    @JsonProperty("idProposta")
    @NotNull
    private final long propostaId;

    public AnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.propostaId = proposta.getId();
    }
}
