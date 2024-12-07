package edu.school.app.domain.repository;

import edu.school.app.domain.model.GradoEscolar;
import java.util.List;
import java.util.Optional;

public interface GradoEscolarRepository {
    void save(GradoEscolar gradoEscolar);
    Optional<GradoEscolar> findGradoEscolarById(Long id);
    List<GradoEscolar> listGradoEscolares(int page, int size);
    void update(GradoEscolar gradoEscolar);
    void delete(Long id);
    long countEscolares();
}

