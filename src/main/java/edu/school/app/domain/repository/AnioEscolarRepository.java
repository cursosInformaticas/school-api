package edu.school.app.domain.repository;

import edu.school.app.domain.model.AnioEscolar;

import java.util.List;
import java.util.Optional;

public interface AnioEscolarRepository {
    void save(AnioEscolar anioEscolar);
    void update(AnioEscolar anioEscolar);
    Optional<AnioEscolar> getAnioEscolarById(Long id);
    List<AnioEscolar> listAllAniosEscolares();
}
