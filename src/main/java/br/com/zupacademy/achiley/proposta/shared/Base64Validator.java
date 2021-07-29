package br.com.zupacademy.achiley.proposta.shared;

import java.util.Base64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64Validator implements ConstraintValidator<IsBase64, String> {

	private final Logger logger = LoggerFactory.getLogger(Base64Validator.class);
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			byte[] decode = Base64.getDecoder().decode(value.getBytes());
			return true;
		} catch (IllegalArgumentException e) {
			logger.warn("Ocorreu uma entrada de base64 em formato inválido.");
			return false;
		}
	}
}
