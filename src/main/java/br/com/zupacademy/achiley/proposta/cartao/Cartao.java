package br.com.zupacademy.achiley.proposta.cartao;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.achiley.proposta.aviso_viagem.AvisoDeViagem;
import br.com.zupacademy.achiley.proposta.biometria.Biometria;
import br.com.zupacademy.achiley.proposta.bloqueio.BloqueioDeCartoes;
import br.com.zupacademy.achiley.proposta.carteiras.Carteira;
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
	@Enumerated(EnumType.STRING)
	private StatusDoCartaoEnum status = StatusDoCartaoEnum.DESBLOQUEADO;
	@OneToOne(mappedBy = "cartao",cascade = CascadeType.MERGE)
	private BloqueioDeCartoes bloqueio;
	@OneToMany(mappedBy = "cartao",cascade = CascadeType.MERGE)
	@OrderBy("momentoDoAviso asc")
	private SortedSet<AvisoDeViagem> avisosDeViagem = new TreeSet<>();
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
	private Set<Carteira> carteiras = new HashSet<>();
	
	@Deprecated
	public Cartao() {

	}
	
	public Cartao(@NotNull @Valid Proposta proposta, @NotBlank String numero) {
		Assert.notNull(proposta, "O objeto proposta nao pode estar nulo");
		Assert.hasLength(numero, "O campo numero do cartao nao pode estar em branco");
		
		this.proposta = proposta;
		this.numero = numero;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Cartao other = (Cartao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public StatusDoCartaoEnum getStatus() {
		return status;
	}

	public SortedSet<AvisoDeViagem> getAvisosDeViagem() {
		return avisosDeViagem;
	}

	public void adicionaBiometria(@NotNull Biometria fingerPrint) {
		Assert.notNull(fingerPrint, "O objeto fingerPrint nao pode estar nulo");
		this.biometrias.add(fingerPrint);
	}

	public boolean isBloqueado() {
		if(this.status.equals(StatusDoCartaoEnum.BLOQUEADO)) {
			return true;
		}
		return false;
	}

	public void bloqueia(@NotNull BloqueioDeCartoes bloqueio) {
		Assert.isTrue(this.status.equals(StatusDoCartaoEnum.DESBLOQUEADO), "O cartao ja esta bloqueado");
		Assert.notNull(bloqueio, "O objeto bloqueio nao pode estar nulo");
		this.status = StatusDoCartaoEnum.BLOQUEADO;
		this.bloqueio = bloqueio;
	}

	public void adicionaAvisoDeViagem(@NotNull AvisoDeViagem avisoViagem) {
		Assert.notNull(avisoViagem, "O objeto avisoViagem nao pode estar nulo");
		this.avisosDeViagem.add(avisoViagem);
		
	}
	
	public boolean possuiAssociacaoComEstaCarteira(Carteira carteira) {
		Assert.notNull(carteira, "O objeto carteira nao pode estar nulo");
		if(carteiras.contains(carteira)) return true;
		return false;
	}

	public void associaCarteira(@NotNull Carteira carteira) {
		Assert.notNull(carteira, "O objeto carteira nao pode estar nulo");
		this.carteiras.add(carteira);
		
	}
	
}
