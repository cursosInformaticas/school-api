package edu.school.app.domain.repository;

import edu.school.app.domain.model.Calificacion;
import java.util.List;
import java.util.Optional;

public interface CalificacionRepository {
    void save(Calificacion calificacion);
    Optional<Calificacion> findCalificacionById(Long id);
    List<Calificacion> listCalificacions(int page, int size);
    void update(Calificacion calificacion);
    void delete(Long id);
    long countCalificacions();
    List<Calificacion> getCalificacionesByAlumnoId(Long alumnoId);
    List<Calificacion> findAllCalificaciones();
}
