package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Asignatura;
import edu.school.app.domain.repository.AsignaturaRepository;
import edu.school.app.infrastructure.entity.AsignaturaEntity;
import edu.school.app.infrastructure.entity.CursoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AsignaturaRepositoryImpl implements AsignaturaRepository, PanacheRepository<AsignaturaEntity> {

    @Override
    public void save(Asignatura asignatura) {
        persist(AsignaturaEntity.fromDomain(asignatura));
    }

    @Override
    public Optional<Asignatura> findAsignaturaById(Long id) {
        return find("id", id).firstResultOptional().map(AsignaturaEntity::toDomain);
    }

    @Override
    public List<Asignatura> listAsignaturas(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(AsignaturaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Asignatura asignatura) {
        AsignaturaEntity entity = findById(asignatura.getId());
        if (entity != null) {
            entity.setNombre(asignatura.getNombre());
            entity.setDescripcion(asignatura.getDescripcion());
            if (asignatura.getCurso() != null) {
                CursoEntity cursoEntity = CursoEntity.fromDomain(asignatura.getCurso());
                entity.setCurso(cursoEntity);
            } else {
                entity.setCurso(null);
            }
            persist(entity);
        } else {
            throw new RuntimeException("Asignatura no encontrado con el ID: " + asignatura.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countAsignaturas() {
        return count();
    }
    @Override
    public List<Asignatura> getAsignaturasByCursoIds(List<Long> cursoIds) {
        return find("curso.id IN ?1", cursoIds)
                .stream()
                .map(AsignaturaEntity::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public List<Asignatura> findAllAsignaturas() {
        return findAll()
                .stream()
                .map(AsignaturaEntity::toDomain)
                .collect(Collectors.toList());
    }

}

