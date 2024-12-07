package edu.school.app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Aula {
    private Long id;
    @NotBlank(message = "El nombre del aula no puede estar en blanco")
    public String nombre;
    @NotNull(message = "La capacidad del aula es obligatoria")
    public int capacidad;
}
