package edu.school.app.domain.repository;

import edu.school.app.domain.model.Maestro;
import java.util.List;
import java.util.Optional;

public interface MaestroRepository {
    void save(Maestro maestro);
    Optional<Maestro> findMaestroById(Long id);
    List<Maestro> listMaestros(int page, int size);
    void update(Maestro maestro);
    void delete(Long id);
    long countMaestros();
}
