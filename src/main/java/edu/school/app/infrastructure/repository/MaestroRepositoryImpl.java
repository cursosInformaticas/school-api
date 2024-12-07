package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Maestro;
import edu.school.app.domain.repository.MaestroRepository;
import edu.school.app.infrastructure.entity.MaestroEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaestroRepositoryImpl implements MaestroRepository, PanacheRepository<MaestroEntity> {

    @Override
    public void save(Maestro maestro) {
        persist(MaestroEntity.fromDomain(maestro));
    }

    @Override
    public Optional<Maestro> findMaestroById(Long id) {
        return find("id", id).firstResultOptional().map(MaestroEntity::toDomain);
    }

    @Override
    public List<Maestro> listMaestros(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(MaestroEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Maestro maestro) {
        MaestroEntity entity = findById(maestro.getId());
        if (entity != null) {
            entity.setNombre(maestro.getNombre());
            entity.setApellido(maestro.getApellido());
            entity.setEspecialidad(maestro.getEspecialidad());
        } else {
            throw new RuntimeException("Maestro no encontrado con el ID: " + maestro.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countMaestros() {
        return count();
    }
}

