package edu.school.app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Horario {
    private Long id;
    @NotNull(message = "El dia de la semana es obligatoria")
    public String diaSemana;  // Ejemplo: "Lunes", "Martes"
    @NotNull(message = "La hora de inicio es obligatoria")
    public LocalTime horaInicio;
    @NotNull(message = "La hora fin es obligatoria")
    public LocalTime horaFin;
    private Asignatura asignatura; // Relación con Asignatura
    private Aula aula; // Relación con Aula
}
