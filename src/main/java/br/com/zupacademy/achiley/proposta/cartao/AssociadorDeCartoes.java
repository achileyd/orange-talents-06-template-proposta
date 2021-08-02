package br.com.zupacademy.achiley.proposta.cartao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zupacademy.achiley.proposta.integracoes.IntegracaoCartoes;
import br.com.zupacademy.achiley.proposta.propostas.Proposta;
import br.com.zupacademy.achiley.proposta.propostas.PropostaRepository;
import br.com.zupacademy.achiley.proposta.shared.ContextoTransacional;
import feign.FeignException;

@Component
public class AssociadorDeCartoes {
	
	private IntegracaoCartoes integracao;
	private PropostaRepository repository;
	private ContextoTransacional transacional;
	
	private final Logger log = LoggerFactory.getLogger(AssociadorDeCartoes.class);
	
	@Autowired
	public AssociadorDeCartoes(IntegracaoCartoes integracao, PropostaRepository repository,
			ContextoTransacional transacional) {
		this.integracao = integracao;
		this.repository = repository;
		this.transacional = transacional;
	}

	@Scheduled(fixedDelayString = "${associa.cartoes.fixed.delay}")
	public void associa() {
		List<Proposta> propostasElegiveis = repository.findAllPropostasElegiveisSemCartao();
		
		log.info("Foram encontradas {} propostas elegiveis", propostasElegiveis.size());
		for (Proposta proposta : propostasElegiveis) {
			try {
				CartaoResponse response = integracao.solicitaNumeroDoCartao(new CartaoRequest(proposta));
				Cartao cartao = new Cartao(proposta,response.getNumeroDoCartao());
				transacional.persiste(cartao);
				
				proposta.associaCartao(cartao);
				
				transacional.atualiza(proposta);
				
				log.info("Foi criado um novo cartão de número {} para a proposta {}.", cartao.getNumero(), proposta.getId());
			} catch (FeignException e) {
				log.error("Não foi possivel criar o cartão para a proposta {}. Motivo: {}", proposta.getId(), e.getMessage());
			}
			
		}
	}

}
