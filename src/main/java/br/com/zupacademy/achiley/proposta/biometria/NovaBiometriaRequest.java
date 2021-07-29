package br.com.zupacademy.achiley.proposta.biometria;

import javax.validation.constraints.NotBlank;

import br.com.zupacademy.achiley.proposta.shared.IsBase64;

public class NovaBiometriaRequest {
	@NotBlank
	@IsBase64
	private String fingerPrint;

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	
}
