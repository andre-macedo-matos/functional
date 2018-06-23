package br.com.atf.functional.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.atf.functional.validator.UrlValidator;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UrlValidator.class})
@ReportAsSingleViolation
@NotEmpty
public @interface Url {
	
	String message() default "{portal.url.empty}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
