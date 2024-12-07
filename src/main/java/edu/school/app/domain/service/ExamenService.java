package edu.school.app.domain.service;

import edu.school.app.domain.model.Examen;

import java.util.List;

public interface ExamenService {
    Examen createExamen(Examen examen);
    Examen getExamenById(Long id);
    List<Examen> getAllExamens(int page, int size);
    Examen updateExamen(Long id, Examen examen);
    void deleteExamen(Long id);
    long countExamenes();
}
