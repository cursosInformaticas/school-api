package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.GradoEscolar;
import edu.school.app.domain.repository.GradoEscolarRepository;
import edu.school.app.infrastructure.entity.GradoEscolarEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class GradoEscolarRepositoryImpl implements GradoEscolarRepository, PanacheRepository<GradoEscolarEntity> {

    @Override
    public void save(GradoEscolar gradoEscolar) {
        persist(GradoEscolarEntity.fromDomain(gradoEscolar));
    }

    @Override
    public Optional<GradoEscolar> findGradoEscolarById(Long id) {
        return find("id", id).firstResultOptional().map(GradoEscolarEntity::toDomain);
    }

    @Override
    public List<GradoEscolar> listGradoEscolares(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(GradoEscolarEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(GradoEscolar gradoEscolar) {
        GradoEscolarEntity entity = findById(gradoEscolar.getId());
        if (entity != null) {
            entity.setNombre(gradoEscolar.getNombre());
        } else {
            throw new RuntimeException("GradoEscolar no encontrado con el ID: " + gradoEscolar.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countEscolares() {
        return count();
    }
}

