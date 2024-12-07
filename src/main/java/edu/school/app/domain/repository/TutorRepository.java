package edu.school.app.domain.repository;

import edu.school.app.domain.model.Tutor;
import java.util.List;
import java.util.Optional;

public interface TutorRepository {
    void save(Tutor tutor);
    Optional<Tutor> findTutorById(Long id);
    List<Tutor> listTutores(int page, int size);
    void update(Tutor tutor);
    void delete(Long id);
    long countTutores();
    boolean existsByEmail(String email);
}
