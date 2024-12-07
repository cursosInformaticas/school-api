package edu.school.app.domain.service;

import edu.school.app.domain.model.GradoEscolar;

import java.util.List;

public interface GradoEscolarService {
    GradoEscolar createGradoEscolar(GradoEscolar gradoEscolar);
    GradoEscolar getGradoEscolarById(Long id);
    List<GradoEscolar> getAllGradoEscolars(int page, int size);
    GradoEscolar updateGradoEscolar(Long id, GradoEscolar gradoEscolar);
    void deleteGradoEscolar(Long id);
    long countEscolares();
}
