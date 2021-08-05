package br.com.zupacademy.achiley.proposta.carteiras;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;

public class NovaAssociacaoDeCarteiraRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String carteira;
	
	@JsonCreator
	public NovaAssociacaoDeCarteiraRequest(@NotBlank String email, @NotBlank String carteira) {
		this.email = email;
		this.carteira = carteira.replaceAll("\\s+","_");
	}

	public String getEmail() {
		return email;
	}

	public String getCarteira() {
		return carteira;
	}

	public Carteira converter(Cartao cartao) {
		try {
			return new Carteira(email, CarteirasEnum.valueOf(carteira.toUpperCase()), cartao);
		} catch(IllegalArgumentException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carteira invalida"); 
		}
	}	
}
