package br.com.atf.functional.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.atf.functional.constraint.NotFoundConstraint;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotFoundConstraint.class})
public @interface NotFound {
	
	String message() default "{portal.url.notfound}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
