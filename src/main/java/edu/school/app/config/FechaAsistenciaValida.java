package edu.school.app.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FechaAsistenciaValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaAsistenciaValida {
    String message() default "La fecha de asistencia no es válida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
