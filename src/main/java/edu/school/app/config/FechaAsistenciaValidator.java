package edu.school.app.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class FechaAsistenciaValidator implements ConstraintValidator<FechaAsistenciaValida, LocalDate> {

    @Override
    public boolean isValid(LocalDate fecha, ConstraintValidatorContext context) {
        if (fecha == null) {
            return false;
        }
        LocalDate today = LocalDate.now();

        // Validar que la fecha sea hoy
        if (!fecha.equals(today)) {
            return false;
        }

        // Validar que sea de lunes a viernes
        DayOfWeek dayOfWeek = fecha.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }
}
