package br.com.zupacademy.achiley.proposta.aviso_viagem;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zupacademy.achiley.proposta.cartao.Cartao;

public class NovoAvisoDeViagemRequest {
	@NotBlank
	private String destinoDaViagem;
	@NotNull
	@Future
	@JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
	private LocalDate dataTermino;
	
	@Deprecated
	public NovoAvisoDeViagemRequest() {
		
	}
	
	public NovoAvisoDeViagemRequest(@NotBlank String destinoDaViagem, @NotNull @Future LocalDate dataTermino) {
		this.destinoDaViagem = destinoDaViagem;
		this.dataTermino = dataTermino;
	}

	public String getDestinoDaViagem() {
		return destinoDaViagem;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public AvisoDeViagem converter(String ip, String userAgent, Cartao cartao) {
		return new AvisoDeViagem(destinoDaViagem, dataTermino, ip, userAgent, cartao );
	} 
}
