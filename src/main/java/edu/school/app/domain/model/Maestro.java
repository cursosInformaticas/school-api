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
public class Maestro {
    private Long id;
    @NotNull(message = "El nombre del maestro es obligatoria")
    public String nombre;
    @NotNull(message = "El apellido del maestro es obligatoria")
    public String apellido;
    @NotNull(message = "La especialidad del maestro es obligatoria")
    public String especialidad;
}
