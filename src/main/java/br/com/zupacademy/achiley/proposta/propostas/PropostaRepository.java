package br.com.zupacademy.achiley.proposta.propostas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PropostaRepository extends JpaRepository<Proposta, Long>{
	Optional<Proposta> findByDocumento(String documento);
	
	@Query("from Proposta p where p.status = 'ELEGIVEL' and p.cartao = null")
	List<Proposta> findAllPropostasElegiveisSemCartao();
}
