package br.com.zupacademy.achiley.proposta.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {	
	Optional<Usuario> findByEmail(String email);
}