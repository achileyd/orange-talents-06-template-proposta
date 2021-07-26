package br.com.zupacademy.achiley.proposta.propostas.analise;

import br.com.zupacademy.achiley.proposta.propostas.StatusDaPropostaEnum;

public enum RespostaDaAnaliseEnum {
	COM_RESTRICAO(StatusDaPropostaEnum.NAO_ELEGIVEL),
	SEM_RESTRICAO(StatusDaPropostaEnum.ELEGIVEL);
	
	private StatusDaPropostaEnum status;

	private RespostaDaAnaliseEnum(StatusDaPropostaEnum status) {
		this.status = status;
	}

	public StatusDaPropostaEnum getStatus() {
		return this.status;
	}
	
}
