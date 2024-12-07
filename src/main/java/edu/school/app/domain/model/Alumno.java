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
public class Alumno {

    private Long id;
    @NotNull(message = "El nombre del alumno es obligatoria")
    private String nombre;
    @NotNull(message = "El apellido del alumno es obligatoria")
    private String apellido;
    @NotNull(message = "El email del alumno es obligatoria")
    private String email;
    private Tutor tutor;
    private boolean hasDependencies;
    private AnioEscolar anioEscolar;


}
