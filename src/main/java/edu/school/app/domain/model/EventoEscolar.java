package edu.school.app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoEscolar {
    private Long id;
    @NotNull(message = "El  nombre del evento escolar es obligatoria")
    public String nombre;
    @NotNull(message = "La fecha del evento escolar es obligatoria")
    public LocalDate fecha;
    @NotNull(message = "La descripcion del evento escolar  es obligatoria")
    public String descripcion;
    private List<Alumno> alumnos;  // Relación con Alumnos
    private List<Maestro> maestros; // Relación con Maestros
}
