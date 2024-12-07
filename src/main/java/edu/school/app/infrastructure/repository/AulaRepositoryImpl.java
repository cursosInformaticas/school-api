package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Aula;
import edu.school.app.domain.repository.AulaRepository;
import edu.school.app.infrastructure.entity.AulaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AulaRepositoryImpl implements AulaRepository, PanacheRepository<AulaEntity> {

    @Override
    public void save(Aula aula) {
        persist(AulaEntity.fromDomain(aula));
    }

    @Override
    public Optional<Aula> findAulaById(Long id) {
        return find("id", id).firstResultOptional().map(AulaEntity::toDomain);
    }

    @Override
    public List<Aula> listAulas(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(AulaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Aula aula) {
        AulaEntity entity = findById(aula.getId());
        if (entity != null) {
            entity.setNombre(aula.getNombre());
            entity.setCapacidad(aula.getCapacidad());
        } else {
            throw new RuntimeException("Aula no encontrado con el ID: " + aula.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countAulas() {
        return count();
    }
}

