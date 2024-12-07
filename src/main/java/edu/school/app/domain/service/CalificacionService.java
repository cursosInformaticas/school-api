package edu.school.app.domain.service;

import edu.school.app.domain.model.Calificacion;

import java.util.List;

public interface CalificacionService {
    Calificacion createCalificacion(Calificacion calificacion);
    Calificacion getCalificacionById(Long id);
    List<Calificacion> getAllCalificacions(int page, int size);
    Calificacion updateCalificacion(Long id, Calificacion calificacion);
    void deleteCalificacion(Long id);
    long countCalificacions();
}
