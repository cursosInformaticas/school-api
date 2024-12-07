package edu.school.app.domain.service;

import edu.school.app.domain.model.Aula;

import java.util.List;

public interface AulaService {
    Aula createAula(Aula aula);
    Aula getAulaById(Long id);
    List<Aula> getAllAulas(int page, int size);
    Aula updateAula(Long id, Aula aula);
    void deleteAula(Long id);
    long countAulas();
}
