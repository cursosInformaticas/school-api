package edu.school.app.application.service;

import edu.school.app.domain.model.Curso;
import edu.school.app.domain.repository.CursoRepository;
import edu.school.app.domain.service.CursoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    public Curso createCurso(Curso curso) {
        cursoRepository.save(curso);
        return curso;
    }

    @Override
    public Curso getCursoById(Long id) {
        return cursoRepository.findCursoById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    @Override
    public List<Curso> getAllCursos(int page, int size) {
        return cursoRepository.listCursos(page, size);
    }

    @Override
    @Transactional
    public Curso updateCurso(Long id, Curso curso) {
        Curso existingCurso = getCursoById(id);
        existingCurso.setNombre(curso.getNombre());
        existingCurso.setDescripcion(curso.getDescripcion());
        existingCurso.setGradoEscolar(curso.getGradoEscolar());
        existingCurso.setMaestro(curso.getMaestro());
        existingCurso.setAlumnos(curso.getAlumnos());
        cursoRepository.update(existingCurso);
        return existingCurso;
    }


    @Override
    public void deleteCurso(Long id) {
        cursoRepository.delete(id);
    }

    @Override
    public long countCursos() {
        return cursoRepository.countCursos();
    }
}
