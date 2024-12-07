package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Calificacion;
import edu.school.app.domain.repository.CalificacionRepository;
import edu.school.app.infrastructure.entity.AlumnoEntity;
import edu.school.app.infrastructure.entity.CalificacionEntity;
import edu.school.app.infrastructure.entity.ExamenEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CalificacionRepositoryImpl implements CalificacionRepository, PanacheRepository<CalificacionEntity> {

    @Override
    public void save(Calificacion calificacion) {
        persist(CalificacionEntity.fromDomain(calificacion));
    }

    @Override
    public Optional<Calificacion> findCalificacionById(Long id) {
        return find("id", id).firstResultOptional().map(CalificacionEntity::toDomain);
    }

    @Override
    public List<Calificacion> listCalificacions(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(CalificacionEntity::toDomain)
                .collect(Collectors.toList());

    }

    @Override
    public void update(Calificacion calificacion) {
        CalificacionEntity entity = findById(calificacion.getId());
        if (entity != null) {
            entity.setPuntaje(calificacion.getPuntaje());
            if (calificacion.getExamen() != null) {
                ExamenEntity examenEntity = ExamenEntity.fromDomain(calificacion.getExamen());
                entity.setExamen(examenEntity);
            } else {
                entity.setExamen(null);
            }
            if (calificacion.getAlumno() != null) {
                AlumnoEntity alumnoEntity = AlumnoEntity.fromDomain(calificacion.getAlumno());
                entity.setAlumno(alumnoEntity);
            } else {
                entity.setAlumno(null);
            }
            persist(entity);
        } else {
            throw new RuntimeException("Calificacion no encontrado con el ID: " + calificacion.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countCalificacions() {
        return count();
    }

    @Override
    public List<Calificacion> getCalificacionesByAlumnoId(Long alumnoId) {
        return find("alumno.id = ?1", alumnoId)
                .stream()
                .map(CalificacionEntity::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public List<Calificacion> findAllCalificaciones() {
        return findAll()
                .stream()
                .map(CalificacionEntity::toDomain)
                .collect(Collectors.toList());
    }

}

