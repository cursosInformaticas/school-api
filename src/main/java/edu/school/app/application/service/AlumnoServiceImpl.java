package edu.school.app.application.service;

import edu.school.app.config.EmailAlreadyExistsException;
import edu.school.app.domain.model.Alumno;
import edu.school.app.domain.model.Asignatura;
import edu.school.app.domain.model.Asistencia;
import edu.school.app.domain.model.Horario;
import edu.school.app.domain.model.Examen;
import edu.school.app.domain.model.Calificacion;
import edu.school.app.domain.model.EventoEscolar;
import edu.school.app.domain.model.Curso;
import edu.school.app.domain.model.AlumnoDetails;
import edu.school.app.domain.repository.AlumnoRepository;
import edu.school.app.domain.repository.AsignaturaRepository;
import edu.school.app.domain.repository.AsistenciaRepository;
import edu.school.app.domain.repository.HorarioRepository;
import edu.school.app.domain.repository.ExamenRepository;
import edu.school.app.domain.repository.CalificacionRepository;
import edu.school.app.domain.repository.EventoEscolarRepository;
import edu.school.app.domain.repository.CursoRepository;
import edu.school.app.domain.service.AlumnoService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final HorarioRepository horarioRepository;
    private final ExamenRepository examenRepository;
    private final CalificacionRepository calificacionRepository;
    private final EventoEscolarRepository eventoRepository;
    private final CursoRepository cursoRepository;

    @Override
    public Alumno createAlumno(Alumno alumno) {
        if (alumnoRepository.existsByEmail(alumno.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está en uso");
        }
        alumnoRepository.save(alumno);
        return alumno;
    }

    @Override
    public Alumno getAlumnoById(Long id) {
        Alumno alumno = alumnoRepository.findAlumnoById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        alumno.setHasDependencies(alumnoRepository.hasDependencies(id));
        return alumno;
    }

    @Override
    public List<Alumno> getAllAlumnos(int page, int size) {
        List<Alumno> alumnos = alumnoRepository.listAlumnos(page, size);
        for (Alumno alumno : alumnos) {
            alumno.setHasDependencies(alumnoRepository.hasDependencies(alumno.getId()));
        }
        return alumnos;
    }

    @Override
    public Alumno updateAlumno(Long id, Alumno alumno) {
        Alumno existingAlumno = getAlumnoById(id);
        if (!existingAlumno.getEmail().equals(alumno.getEmail()) &&
                alumnoRepository.existsByEmail(alumno.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está en uso por otro alumno");
        }
        existingAlumno.setNombre(alumno.getNombre());
        existingAlumno.setApellido(alumno.getApellido());
        existingAlumno.setEmail(alumno.getEmail());
        existingAlumno.setTutor(alumno.getTutor());
        existingAlumno.setAnioEscolar(alumno.getAnioEscolar());
        alumnoRepository.update(existingAlumno);
        return existingAlumno;
    }

    @Override
    public void deleteAlumno(Long id) {
        alumnoRepository.delete(id);
    }

    @Override
    public boolean hasDependencies(Long id) {
        return alumnoRepository.hasDependencies(id);
    }

    @Override
    public long countAlumnos() {
        return alumnoRepository.countAlumnos();
    }

    @Override
    public AlumnoDetails getAlumnoDetails(Long id) {
        Alumno alumno = alumnoRepository.findAlumnoById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        List<Curso> cursos = cursoRepository.getCursosByAlumnoId(id);
        List<Asignatura> asignaturas = asignaturaRepository.getAsignaturasByCursoIds(
                cursos.stream().map(Curso::getId).collect(Collectors.toList())
        );
        List<Asistencia> asistencias = asistenciaRepository.getAsistenciasByAlumnoId(id);
        List<Horario> horarios = horarioRepository.getHorariosByAsignaturaIds(
                asignaturas.stream().map(Asignatura::getId).collect(Collectors.toList())
        );
        List<Examen> examenes = examenRepository.getExamenesByAsignaturaIds(
                asignaturas.stream().map(Asignatura::getId).collect(Collectors.toList())
        );
        List<Calificacion> calificaciones = calificacionRepository.getCalificacionesByAlumnoId(id);
        List<EventoEscolar> eventos = eventoRepository.getEventosByAlumnoId(id);

        // Construir el DTO
        return new AlumnoDetails(alumno, cursos, asignaturas, asistencias, horarios, examenes, calificaciones, eventos);
    }

    @Override
    public List<Alumno> getAlumnosByAnioEscolar(int anioEscolar) {
        return alumnoRepository.findAlumnosByAnioEscolar(anioEscolar);
    }

    @Override
    public List<Alumno> getAlumnosByAnioEscolarPaged(int anioEscolar, int page, int size) {
        return alumnoRepository.findAlumnosByAnioEscolarPaged(anioEscolar, page, size);
    }

    @Override
    public long countAlumnosByAnioEscolar(int anioEscolar) {
        return alumnoRepository.countAlumnosByAnioEscolar(anioEscolar);
    }

}
