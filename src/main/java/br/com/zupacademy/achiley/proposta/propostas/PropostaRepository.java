package br.com.zupacademy.achiley.proposta.propostas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long>{
	Optional<Proposta> findByDocumento(String documento);
	
	@Query("from Proposta p left join p.cartao c where p.status = 'ELEGIVEL' and c.id = null")
	List<Proposta> findAllPropostasElegiveisSemCartao();
}
