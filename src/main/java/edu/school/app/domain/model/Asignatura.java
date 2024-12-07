package edu.school.app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Asignatura {
    private Long id;
    @NotNull(message = "El nombre de la asignatura es obligatoria")
    private String nombre;
    @NotNull(message = "El descripcion de la asignatura es obligatoria")
    private String descripcion;
    private Curso curso;
}
