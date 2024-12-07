package edu.school.app.application.service;

import edu.school.app.domain.model.Asignatura;
import edu.school.app.domain.repository.AsignaturaRepository;
import edu.school.app.domain.service.AsignaturaService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class AsignaturaServiceImpl implements AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;

    @Override
    public Asignatura createAsignatura(Asignatura asignatura) {
        asignaturaRepository.save(asignatura);
        return asignatura;
    }

    @Override
    public Asignatura getAsignaturaById(Long id) {
        return asignaturaRepository.findAsignaturaById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrado"));
    }

    @Override
    public List<Asignatura> getAllAsignaturas(int page, int size) {
        return asignaturaRepository.listAsignaturas(page, size);
    }

    @Override
    public Asignatura updateAsignatura(Long id, Asignatura asignatura) {
        Asignatura existingAsignatura = getAsignaturaById(id);
        existingAsignatura.setNombre(asignatura.getNombre());
        existingAsignatura.setDescripcion(asignatura.getDescripcion());
        existingAsignatura.setCurso(asignatura.getCurso());
        asignaturaRepository.update(existingAsignatura);
        return existingAsignatura;
    }

    @Override
    public void deleteAsignatura(Long id) {
        asignaturaRepository.delete(id);
    }

    @Override
    public long countAsignaturas() {
        return asignaturaRepository.countAsignaturas();
    }
}
