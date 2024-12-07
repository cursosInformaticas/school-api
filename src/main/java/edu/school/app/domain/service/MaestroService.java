package edu.school.app.domain.service;

import edu.school.app.domain.model.Maestro;

import java.util.List;

public interface MaestroService {
    Maestro createMaestro(Maestro maestro);
    Maestro getMaestroById(Long id);
    List<Maestro> getAllMaestros(int page, int size);
    Maestro updateMaestro(Long id, Maestro maestro);
    void deleteMaestro(Long id);
    long countMaestros();
}
