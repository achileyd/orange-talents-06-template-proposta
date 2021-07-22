package br.com.zupacademy.achiley.proposta.config.validacao;

import org.springframework.web.server.ResponseStatusException;

public class ResponseStatusExceptionHandlerDto {
	private String message;
	private String reason;

	public ResponseStatusExceptionHandlerDto(ResponseStatusException e) {
		this.message = e.getMessage();
		this.reason = e.getReason();
	}

	public String getMessage() {
		return message;
	}

	public String getReason() {
		return reason;
	}
}
