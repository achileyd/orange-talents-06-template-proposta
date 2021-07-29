package br.com.zupacademy.achiley.proposta.shared;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {Base64Validator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsBase64 {
    String message() default "{br.com.zupacademy.achiley.proposta.shared.isBase64}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
