package br.com.atf.functional.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.atf.functional.constraint.RedirectedConstraint;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RedirectedConstraint.class})
public @interface Redirected {
	
	String message() default "{portal.url.redirect}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
