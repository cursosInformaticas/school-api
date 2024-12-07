package edu.school.app.domain.repository;

import edu.school.app.domain.model.Examen;
import java.util.List;
import java.util.Optional;

public interface ExamenRepository {
    void save(Examen examen);
    Optional<Examen> findExamenById(Long id);
    List<Examen> listExamenes(int page, int size);
    void update(Examen examen);
    void delete(Long id);
    long countExamenes();
    List<Examen> getExamenesByAsignaturaIds(List<Long> asignaturaIds);
    List<Examen> findAllExamenes();
}

