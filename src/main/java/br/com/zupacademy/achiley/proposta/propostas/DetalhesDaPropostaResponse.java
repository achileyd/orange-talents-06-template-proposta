package br.com.zupacademy.achiley.proposta.propostas;

public class DetalhesDaPropostaResponse {
	private Long id;
	private StatusDaPropostaEnum status;
	
	public DetalhesDaPropostaResponse(Proposta proposta) {
		this.id = proposta.getId();
		this.status = proposta.getStatus();;
	}

	public Long getId() {
		return id;
	}

	public StatusDaPropostaEnum getStatus() {
		return status;
	}
	
}
