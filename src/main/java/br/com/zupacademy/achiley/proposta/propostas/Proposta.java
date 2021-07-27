package br.com.zupacademy.achiley.proposta.propostas;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private StatusDaPropostaEnum status = StatusDaPropostaEnum.PENDENTE;
	@Column(unique = true)
	private String cartao;
	
	@Deprecated
	public Proposta() {
		
	}
	
	public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		Assert.hasLength(documento, "O documento nao pode estar em branco");
		Assert.hasLength(email, "O email nao pode estar em branco");
		Assert.hasLength(nome, "O nome nao pode estar em branco");
		Assert.hasLength(endereco, "O endereco nao pode estar em branco");
		Assert.isTrue(salario.intValue() > 0, "O salario deve ser maior que 0");
		
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Proposta other = (Proposta) obj;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void atualizaStatusDaProposta(StatusDaPropostaEnum status) {
		Assert.isTrue(this.status.equals(StatusDaPropostaEnum.PENDENTE) || 
					  this.status.equals(StatusDaPropostaEnum.NAO_ELEGIVEL),
					  "A proposta ja esta definida como elegivel e nao pode ser alterada");
		this.status = status;
	}
	
	public void associaCartao(@NotNull String numeroDoCartaoRecebido) {
		Assert.isTrue(this.status.equals(StatusDaPropostaEnum.ELEGIVEL),
				      "Nao e possivel associar um cartao a uma proposta com o status pendente ou nao elegivel");
		this.cartao = numeroDoCartaoRecebido;
	}
}
