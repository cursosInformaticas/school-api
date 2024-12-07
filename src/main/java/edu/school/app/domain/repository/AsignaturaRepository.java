package edu.school.app.domain.repository;

import edu.school.app.domain.model.Asignatura;
import java.util.List;
import java.util.Optional;

public interface AsignaturaRepository {
    void save(Asignatura asignatura);
    Optional<Asignatura> findAsignaturaById(Long id);
    List<Asignatura> listAsignaturas(int page, int size);
    void update(Asignatura asignatura);
    void delete(Long id);
    long countAsignaturas();
    List<Asignatura> getAsignaturasByCursoIds(List<Long> cursoIds);
    List<Asignatura> findAllAsignaturas();
}

