package edu.school.app.domain.repository;

import edu.school.app.domain.model.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoRepository {
    void save(Curso curso);
    Optional<Curso> findCursoById(Long id);
    List<Curso> listCursos(int page, int size);
    void update(Curso curso);
    void delete(Long id);
    long countCursos();
    List<Curso> getCursosByAlumnoId(Long alumnoId);
    List<Curso> findAllCursos();
}
