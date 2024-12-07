package edu.school.app.domain.service;

import edu.school.app.domain.model.Curso;
import java.util.List;

public interface CursoService {
    Curso createCurso(Curso curso);
    Curso getCursoById(Long id);
    List<Curso> getAllCursos(int page, int size);
    Curso updateCurso(Long id, Curso curso);
    void deleteCurso(Long id);
    long countCursos();
}
