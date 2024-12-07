package edu.school.app.domain.repository;

import edu.school.app.domain.model.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository {
    void save(Alumno alumno);
    Optional<Alumno> findAlumnoById(Long id);
    List<Alumno> listAlumnos(int page, int size);
    void update(Alumno alumno);
    void delete(Long id);
    boolean existsByEmail(String email);
    boolean hasDependencies(Long id);
    long countAlumnos();
    List<Alumno> findAllAlumnos();
    List<Alumno> findAlumnosByAnioEscolar(int anioEscolar);
    List<Alumno> findAlumnosByAnioEscolarPaged(int anioEscolar, int page, int size);
    long countAlumnosByAnioEscolar(int anioEscolar);
}

