package br.com.zupacademy.achiley.proposta.avisoViagem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;

@Entity
@Table(name = "avisos_de_viagem")
public class AvisoDeViagem implements Comparable<AvisoDeViagem>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Column(nullable = false)
	private String destinoDaViagem;
	@Column(nullable = false)
	private LocalDateTime momentoDoAviso = LocalDateTime.now();
	@NotNull
	@Future
	@Column(nullable = false)
	private LocalDate dataTermino; 
	@NotBlank
	@Column(nullable = false)
	private String ip;
	@NotBlank
	@Column(nullable = false)
	private String userAgent;
	@NotNull
	@Valid
	@ManyToOne
	private Cartao cartao;
	
    @Deprecated
    public AvisoDeViagem() {
	}
    
	public AvisoDeViagem(@NotBlank String destinoDaViagem, @NotNull @Future LocalDate dataTermino, @NotBlank String ip,
			@NotBlank String userAgent, @NotNull @Valid Cartao cartao) {
		this.destinoDaViagem = destinoDaViagem;
		this.dataTermino = dataTermino;
		this.ip = ip;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartao == null) ? 0 : cartao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AvisoDeViagem other = (AvisoDeViagem) obj;
		if (cartao == null) {
			if (other.cartao != null)
				return false;
		} else if (!cartao.equals(other.cartao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(AvisoDeViagem a) {
		return this.momentoDoAviso.compareTo(a.momentoDoAviso);
	}
	
}
