package br.com.atf.functional.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.atf.functional.validator.UrlValidator;

@ConstraintComposition(CompositionType.AND)
@NotEmpty(message = "{portal.url.empty}")
@ReportAsSingleViolation
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UrlValidator.class})
@Documented
public @interface Url {
	
	String message() default "{portal.url.empty}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
