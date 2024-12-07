package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.AnioEscolar;
import edu.school.app.domain.repository.AnioEscolarRepository;
import edu.school.app.infrastructure.entity.AnioEscolarEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
    public class AnioEscolarRepositoryImpl implements AnioEscolarRepository, PanacheRepository<AnioEscolarEntity> {


    @Override
    public void save(AnioEscolar anioEscolar) {
        persist(AnioEscolarEntity.fromDomain(anioEscolar));
    }

    @Override
    public void update(AnioEscolar anioEscolar) {
        AnioEscolarEntity existingEntity = findById(anioEscolar.getId());
        if (existingEntity == null) {
            throw new IllegalArgumentException("AnioEscolar no encontrado con el ID: " + anioEscolar.getId());
        }
        existingEntity.setAnio(anioEscolar.getAnio());
        existingEntity.setDescripcion(anioEscolar.getDescripcion());
        persist(existingEntity);
    }

    @Override
    public Optional<AnioEscolar> getAnioEscolarById(Long id) {
        return findByIdOptional(id).map(AnioEscolarEntity::toDomain);
    }

    @Override
    public List<AnioEscolar> listAllAniosEscolares() {
        return findAll()
                .stream()
                .map(AnioEscolarEntity::toDomain)
                .collect(Collectors.toList());
    }
}
