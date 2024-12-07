package edu.school.app.domain.repository;

import edu.school.app.domain.model.Aula;
import java.util.List;
import java.util.Optional;

public interface AulaRepository {
    void save(Aula aula);
    Optional<Aula> findAulaById(Long id);
    List<Aula> listAulas(int page, int size);
    void update(Aula aula);
    void delete(Long id);
    long countAulas();
}

