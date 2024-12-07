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
public class AnioEscolar {

    private Long id;
    @NotNull(message = "El anio es obligatoria")
    private Integer anio;
    @NotNull(message = "descripcion del anio obligatoria")
    private String descripcion;


}
