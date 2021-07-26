package br.com.zupacademy.achiley.proposta.shared;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class ContextoTransacional {
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public <T> T persiste(T objeto) {
		manager.persist(objeto);
		return objeto;
	}

	@Transactional
	public <T> T atualiza(T objeto) {
		return manager.merge(objeto);
	}
}
