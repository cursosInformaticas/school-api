package edu.school.app.domain.service;

import edu.school.app.domain.model.Alumno;
import edu.school.app.domain.model.AlumnoDetails;

import java.util.List;

public interface AlumnoService {
    Alumno createAlumno(Alumno alumno);
    Alumno getAlumnoById(Long id);
    List<Alumno> getAllAlumnos(int page, int size);
    Alumno updateAlumno(Long id, Alumno alumno);
    void deleteAlumno(Long id);
    boolean hasDependencies(Long id);
    long countAlumnos();
    AlumnoDetails getAlumnoDetails(Long id);
    List<Alumno> getAlumnosByAnioEscolar(int anioEscolar);
    List<Alumno> getAlumnosByAnioEscolarPaged(int anioEscolar, int page, int size);
    long countAlumnosByAnioEscolar(int anioEscolar);

}
