package edu.school.app.domain.repository;

import edu.school.app.domain.model.Asistencia;

import java.util.List;
import java.util.Optional;

public interface AsistenciaRepository {
    void save(Asistencia asistencia);
    Optional<Asistencia> findAsistenciaById(Long id);
    List<Asistencia> listAsistencias(int page, int size);
    void update(Asistencia asistencia);
    void delete(Long id);
    long countAsistencias();
    List<Asistencia> getAsistenciasByAlumnoId(Long alumnoId);
    List<Asistencia> findAllAsistencias();
}

