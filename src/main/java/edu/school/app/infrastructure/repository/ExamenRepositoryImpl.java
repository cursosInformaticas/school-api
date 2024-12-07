package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Examen;
import edu.school.app.domain.repository.ExamenRepository;
import edu.school.app.infrastructure.entity.AsignaturaEntity;
import edu.school.app.infrastructure.entity.ExamenEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExamenRepositoryImpl implements ExamenRepository, PanacheRepository<ExamenEntity> {

    @Override
    public void save(Examen examen) {
        persist(ExamenEntity.fromDomain(examen));
    }

    @Override
    public Optional<Examen> findExamenById(Long id) {
        return find("id", id).firstResultOptional().map(ExamenEntity::toDomain);
    }

    @Override
    public List<Examen> listExamenes(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(ExamenEntity::toDomain)
                .collect(Collectors.toList());

    }

    @Override
    public void update(Examen examen) {
        ExamenEntity entity = findById(examen.getId());
        if (entity != null) {
            entity.setNombre(examen.getNombre());
            entity.setFecha(examen.getFecha());
            if (examen.getAsignatura() != null) {
                AsignaturaEntity asignaturaEntity = AsignaturaEntity.fromDomain(examen.getAsignatura());
                entity.setAsignatura(asignaturaEntity);
            } else {
                entity.setAsignatura(null);
            }
            persist(entity);
        } else {
            throw new RuntimeException("Examen no encontrado con el ID: " + examen.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countExamenes() {
        return count();
    }

    @Override
    public List<Examen> getExamenesByAsignaturaIds(List<Long> asignaturaIds) {
        return find("asignatura.id IN ?1", asignaturaIds)
                .stream()
                .map(ExamenEntity::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public List<Examen> findAllExamenes() {
        return findAll()
                .stream()
                .map(ExamenEntity::toDomain)
                .collect(Collectors.toList());
    }

}

