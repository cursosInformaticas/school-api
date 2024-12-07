package edu.school.app.application.service;

import edu.school.app.domain.model.Examen;
import edu.school.app.domain.repository.ExamenRepository;
import edu.school.app.domain.service.ExamenService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class ExamenServiceImpl implements ExamenService {

    private final ExamenRepository examenRepository;

    @Override
    public Examen createExamen(Examen examen) {
        examenRepository.save(examen);
        return examen;
    }

    @Override
    public Examen getExamenById(Long id) {
        return examenRepository.findExamenById(id)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado"));
    }

    @Override
    public List<Examen> getAllExamens(int page, int size) {
        return examenRepository.listExamenes(page, size);
    }

    @Override
    public Examen updateExamen(Long id, Examen examen) {
        Examen existingExamen = getExamenById(id);
        existingExamen.setNombre(examen.getNombre());
        existingExamen.setFecha(examen.getFecha());
        existingExamen.setAsignatura(examen.getAsignatura());
        examenRepository.update(existingExamen);
        return existingExamen;
    }

    @Override
    public void deleteExamen(Long id) {
        examenRepository.delete(id);
    }

    @Override
    public long countExamenes() {
        return examenRepository.countExamenes();
    }
}
