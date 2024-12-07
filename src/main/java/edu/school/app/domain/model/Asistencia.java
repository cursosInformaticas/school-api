package edu.school.app.domain.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Asistencia {
    private Long id;
    @NotNull(message = "La fecha del asistencia es obligatoria")
    //@FechaAsistenciaValida
    public LocalDateTime fecha;
    public boolean presente;
    private Alumno alumno;
    private Curso curso;
}
