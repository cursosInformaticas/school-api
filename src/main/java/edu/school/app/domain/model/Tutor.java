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
public class Tutor {
    private Long id;
    @NotNull(message = "El nombre del tutor es obligatoria")
    public String nombre;
    @NotNull(message = "El apellido del tutor es obligatoria")
    public String apellido;
    @NotNull(message = "El email del tutor es obligatoria")
    public String email;
    @NotNull(message = "El telefono del tutor es obligatoria")
    public String telefono;
}
