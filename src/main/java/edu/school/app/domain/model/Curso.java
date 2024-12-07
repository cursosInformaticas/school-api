package edu.school.app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Curso {

    private Long id;
    @NotNull(message = "El nombre del curso es obligatoria")
    private String nombre;
    @NotNull(message = "La descripcion del curso es obligatoria")
    private String descripcion;

    private GradoEscolar gradoEscolar;
    private Maestro maestro;
    private List<Alumno> alumnos;
}
