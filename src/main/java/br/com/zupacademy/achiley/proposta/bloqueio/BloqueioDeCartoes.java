package br.com.zupacademy.achiley.proposta.bloqueio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;

@Entity
public class BloqueioDeCartoes {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @NotBlank
    @Column(nullable = false)
    private String ip;
    @NotBlank
    @Column(nullable = false)
    private String userAgent;
    @NotNull
    private LocalDateTime momentoDoBloqueio = LocalDateTime.now();
    @NotNull
    @OneToOne
    @Valid
    private Cartao cartao;
    
    @Deprecated
    public BloqueioDeCartoes() {
    	
    }

	public BloqueioDeCartoes(@NotBlank String ip, @NotBlank String userAgent, @NotNull @Valid Cartao cartao) {
		this.ip = ip;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}
    
    
}
