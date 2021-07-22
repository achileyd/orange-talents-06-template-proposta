package br.com.zupacademy.achiley.proposta.propostas;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import br.com.zupacademy.achiley.proposta.shared.Document;

@Entity
@Table(name="propostas")
public class Proposta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Document
	private String documento;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String endereco;
	@NotNull
	@Positive
	private BigDecimal salario;
	
	@Deprecated
	public Proposta() {
		
	}
	
	public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		Assert.hasLength(documento, "O documento nao pode estar em branco");
		Assert.hasLength(email, "O email nao pode estar em branco");
		Assert.hasLength(nome, "O nome nao pode estar em branco");
		Assert.hasLength(endereco, "O endereco nao pode estar em branco");
		Assert.isTrue(salario.intValue() > 0, "O salario deve ser maior que 0- ASSERT");
		
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Long getId() {
		return id;
	}

}
