package br.com.zupacademy.achiley.proposta.carteiras;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;

@Entity
@Table(name = "carteiras")
public class Carteira {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    @Email
    @Column(nullable = false)
	private String email;
    @Enumerated(EnumType.STRING)
    private CarteirasEnum nomeCarteira;
    @NotNull
    @Valid
    @ManyToOne
	private Cartao cartao;
    
    @Deprecated
    public Carteira() {
    	
    }

	public Carteira(@NotBlank @Email String email, CarteirasEnum nomeCarteira, @NotNull @Valid Cartao cartao) {
		this.email = email;
		this.nomeCarteira = nomeCarteira;
		this.cartao = cartao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartao == null) ? 0 : cartao.hashCode());
		result = prime * result + ((nomeCarteira == null) ? 0 : nomeCarteira.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carteira other = (Carteira) obj;
		if (cartao == null) {
			if (other.cartao != null)
				return false;
		} else if (!cartao.equals(other.cartao))
			return false;
		if (nomeCarteira != other.nomeCarteira)
			return false;
		return true;
	}

	public String getId() {
		return id;
	}
    
}
