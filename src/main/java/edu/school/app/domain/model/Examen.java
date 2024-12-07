package edu.school.app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Examen {
    private Long id;
    @NotNull(message = "El nombre del examen es obligatoria")
    public String nombre;
    @NotNull(message = "La fecha del examen es obligatoria")
    public LocalDate fecha;
    private Asignatura asignatura;  // Relación con Asignatura
}
