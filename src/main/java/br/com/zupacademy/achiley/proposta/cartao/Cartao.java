package br.com.zupacademy.achiley.proposta.cartao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.achiley.proposta.biometria.Biometria;
import br.com.zupacademy.achiley.proposta.propostas.Proposta;
import io.jsonwebtoken.lang.Assert;

@Entity
@Table(name = "cartoes")
public class Cartao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@OneToOne
	@JoinColumn(unique = true)
	@Valid
	private Proposta proposta;
	@NotBlank
	@Column(nullable = false)
	private String numero;
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
	private Set<Biometria> biometrias = new HashSet<>();
	
	@Deprecated
	public Cartao() {

	}
	
	public Cartao(@NotNull @Valid Proposta proposta, @NotBlank String numero) {
		Assert.notNull(proposta, "O objeto proposta nao pode estar nulo");
		Assert.hasLength(numero, "O campo numero do cartao nao pode estar em branco");
		
		this.proposta = proposta;
		this.numero = numero;
	}
	
	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public void adicionaBiometria(Biometria fingerPrint) {
		Assert.notNull(fingerPrint, "O fingerPrint nao pode estar em branco");
		this.biometrias.add(fingerPrint);
	}
}
