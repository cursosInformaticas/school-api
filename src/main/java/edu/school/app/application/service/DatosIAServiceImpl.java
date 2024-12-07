package edu.school.app.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school.app.domain.model.*;
import edu.school.app.domain.repository.*;
import edu.school.app.domain.service.DatosIAService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
public class DatosIAServiceImpl implements DatosIAService {

    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final HorarioRepository horarioRepository;
    private final ExamenRepository examenRepository;
    private final CalificacionRepository calificacionRepository;

    public List<Map<String, Object>> getAllDataForAI() {
        // Recolectar datos
        List<Alumno> alumnos = alumnoRepository.findAllAlumnos();
        List<Curso> cursos = cursoRepository.findAllCursos();
        List<Asignatura> asignaturas = asignaturaRepository.findAllAsignaturas();
        List<Asistencia> asistencias = asistenciaRepository.findAllAsistencias();
        List<Horario> horarios = horarioRepository.findAllHorarios();
        List<Examen> examenes = examenRepository.findAllExamenes();
        List<Calificacion> calificaciones = calificacionRepository.findAllCalificaciones();

        // Consolidar en una lista de mapas para procesamiento IA
        List<Map<String, Object>> dataForAI = new ArrayList<>();

        for (Alumno alumno : alumnos) {
            Map<String, Object> record = new HashMap<>();
            record.put("alumno", alumno);
            record.put("cursos", cursos.stream()
                    .filter(curso -> curso.getAlumnos().stream().anyMatch(a -> a.getId().equals(alumno.getId())))
                    .collect(Collectors.toList()));
            record.put("asignaturas", asignaturas.stream()
                    .filter(asignatura -> asignatura.getCurso().getAlumnos().stream().anyMatch(a -> a.getId().equals(alumno.getId())))
                    .collect(Collectors.toList()));
            record.put("asistencias", asistencias.stream()
                    .filter(asistencia -> asistencia.getAlumno().getId().equals(alumno.getId()))
                    .collect(Collectors.toList()));
            record.put("horarios", horarios.stream()
                    .filter(horario -> horario.getAsignatura().getCurso().getAlumnos().stream().anyMatch(a -> a.getId().equals(alumno.getId())))
                    .collect(Collectors.toList()));
            record.put("examenes", examenes.stream()
                    .filter(examen -> examen.getAsignatura().getCurso().getAlumnos().stream().anyMatch(a -> a.getId().equals(alumno.getId())))
                    .collect(Collectors.toList()));
            record.put("calificaciones", calificaciones.stream()
                    .filter(calificacion -> calificacion.getAlumno().getId().equals(alumno.getId()))
                    .collect(Collectors.toList()));
            dataForAI.add(record);
        }

        return dataForAI;
    }

    @Override
    public String exportDataToJSON(List<Map<String, Object>> data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir datos a JSON", e);
        }
    }

    @Override
    public String exportDataToCSV(List<Map<String, Object>> data) {
        StringBuilder csvBuilder = new StringBuilder();

        // Agregar encabezados
        csvBuilder.append("Alumno ID,Alumno Nombre,Alumno Apellido,Alumno Email,Curso,Asignatura,Asistencia,Horario,Examen,Calificación\n");

        // Iterar sobre los datos para construir las filas del CSV
        for (Map<String, Object> record : data) {
            Alumno alumno = (Alumno) record.get("alumno");
            List<Curso> cursos = (List<Curso>) record.get("cursos");
            List<Asignatura> asignaturas = (List<Asignatura>) record.get("asignaturas");
            List<Asistencia> asistencias = (List<Asistencia>) record.get("asistencias");
            List<Horario> horarios = (List<Horario>) record.get("horarios");
            List<Examen> examenes = (List<Examen>) record.get("examenes");
            List<Calificacion> calificaciones = (List<Calificacion>) record.get("calificaciones");

            // Construir una fila con los datos
            csvBuilder.append(alumno.getId()).append(",")
                    .append(alumno.getNombre()).append(",")
                    .append(alumno.getApellido()).append(",")
                    .append(alumno.getEmail()).append(",")
                    .append(cursos.size()).append(",")
                    .append(asignaturas.size()).append(",")
                    .append(asistencias.size()).append(",")
                    .append(horarios.size()).append(",")
                    .append(examenes.size()).append(",")
                    .append(calificaciones.size()).append("\n");
        }

        return csvBuilder.toString();
    }

}
