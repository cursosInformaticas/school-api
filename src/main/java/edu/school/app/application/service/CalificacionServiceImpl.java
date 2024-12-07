package edu.school.app.application.service;

import edu.school.app.domain.model.Calificacion;
import edu.school.app.domain.repository.CalificacionRepository;
import edu.school.app.domain.service.CalificacionService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;

    @Override
    public Calificacion createCalificacion(Calificacion calificacion) {
        calificacionRepository.save(calificacion);
        return calificacion;
    }

    @Override
    public Calificacion getCalificacionById(Long id) {
        return calificacionRepository.findCalificacionById(id)
                .orElseThrow(() -> new RuntimeException("Calificacion no encontrado"));
    }

    @Override
    public List<Calificacion> getAllCalificacions(int page, int size) {
        return calificacionRepository.listCalificacions(page, size);
    }

    @Override
    public Calificacion updateCalificacion(Long id, Calificacion calificacion) {
        Calificacion existingCalificacion = getCalificacionById(id);
        existingCalificacion.setPuntaje(calificacion.getPuntaje());
        existingCalificacion.setAlumno(calificacion.getAlumno());
        existingCalificacion.setExamen(calificacion.getExamen());
        calificacionRepository.update(existingCalificacion);
        return existingCalificacion;
    }

    @Override
    public void deleteCalificacion(Long id) {
        calificacionRepository.delete(id);
    }

    @Override
    public long countCalificacions() {
        return calificacionRepository.countCalificacions();
    }
}
