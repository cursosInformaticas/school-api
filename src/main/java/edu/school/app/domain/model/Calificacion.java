package edu.school.app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Calificacion {
    private Long id;
    public double puntaje;
    private Examen examen;   // Relación con Examen
    private Alumno alumno;   // Relación con Alumno
}
