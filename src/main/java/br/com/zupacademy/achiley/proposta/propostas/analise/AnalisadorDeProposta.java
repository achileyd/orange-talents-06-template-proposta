package br.com.zupacademy.achiley.proposta.propostas.analise;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.zupacademy.achiley.proposta.integracoes.IntegracaoAnaliseFinanceira;
import br.com.zupacademy.achiley.proposta.propostas.Proposta;
import br.com.zupacademy.achiley.proposta.propostas.StatusDaPropostaEnum;
import feign.FeignException;

@Service
@Validated
public class AnalisadorDeProposta {
	@Autowired
	private IntegracaoAnaliseFinanceira integracoes;

	public StatusDaPropostaEnum analisa(@NotNull @Validated Proposta proposta) {
		try {
			AnaliseRequest solicitacao = new AnaliseRequest(proposta);
			AnaliseResponse resposta = integracoes.solicitaAnalise(solicitacao);
			String resultadoDaAnalise = resposta.getStatus();

			return RespostaDaAnaliseEnum.valueOf(resultadoDaAnalise).getStatus();
		} catch (FeignException.UnprocessableEntity e) {
			return RespostaDaAnaliseEnum.COM_RESTRICAO.getStatus();
		}
	}
}
