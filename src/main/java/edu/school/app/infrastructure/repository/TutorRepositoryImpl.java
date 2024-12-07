package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Tutor;
import edu.school.app.domain.repository.TutorRepository;
import edu.school.app.infrastructure.entity.TutorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TutorRepositoryImpl implements TutorRepository, PanacheRepository<TutorEntity> {

    @Override
    public void save(Tutor tutor) {
        persist(TutorEntity.fromDomain(tutor));
    }

    @Override
    public Optional<Tutor> findTutorById(Long id) {
        return find("id", id).firstResultOptional().map(TutorEntity::toDomain);
    }

    @Override
    public List<Tutor> listTutores(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(TutorEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Tutor tutor) {
        TutorEntity entity = findById(tutor.getId());
        if (entity != null) {
            entity.setNombre(tutor.getNombre());
            entity.setApellido(tutor.getApellido());
            entity.setEmail(tutor.getEmail());
            entity.setTelefono(tutor.getTelefono());
        } else {
            throw new RuntimeException("Tutor no encontrado con el ID: " + tutor.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countTutores() {
        return count();
    }

    public boolean existsByEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }
}

