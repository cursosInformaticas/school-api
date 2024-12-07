package edu.school.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoDetails {

    private Alumno alumno;
    private List<Curso> cursos;
    private List<Asignatura> asignaturas;
    private List<Asistencia> asistencias;
    private List<Horario> horarios;
    private List<Examen> examenes;
    private List<Calificacion> calificaciones;
    private List<EventoEscolar> eventos;

}

