package br.com.zupacademy.achiley.proposta.biometria;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;

@Entity
@Table(name = "biometrias")
public class Biometria {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    @Column(nullable = false)
    private String fingerPrint;
    @ManyToOne
    @Valid
    private Cartao cartao;
    private LocalDateTime momentoDeAssociacao = LocalDateTime.now();
    
    @Deprecated
    public Biometria() {
    	
    }

	public Biometria(@NotBlank String fingerPrint, @Valid Cartao cartao) {
		this.fingerPrint = fingerPrint;
		this.cartao = cartao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fingerPrint == null) ? 0 : fingerPrint.hashCode());
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
		Biometria other = (Biometria) obj;
		if (fingerPrint == null) {
			if (other.fingerPrint != null)
				return false;
		} else if (!fingerPrint.equals(other.fingerPrint))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}
   
}
