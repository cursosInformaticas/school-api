package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.EventoEscolar;
import edu.school.app.domain.repository.EventoEscolarRepository;
import edu.school.app.infrastructure.entity.AlumnoEntity;
import edu.school.app.infrastructure.entity.EventoEscolarEntity;
import edu.school.app.infrastructure.entity.MaestroEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventoEscolarRepositoryImpl implements EventoEscolarRepository, PanacheRepository<EventoEscolarEntity> {

    @Override
    public void save(EventoEscolar eventoEscolar) {
        persist(EventoEscolarEntity.fromDomain(eventoEscolar));
    }

    @Override
    public Optional<EventoEscolar> findEventoEscolarById(Long id) {
        return find("id", id).firstResultOptional().map(EventoEscolarEntity::toDomain);
    }

    @Override
    public List<EventoEscolar> listEventoEscolares(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(EventoEscolarEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(EventoEscolar eventoEscolar) {
        EventoEscolarEntity entity = findById(eventoEscolar.getId());
        if (entity != null) {
            entity.setNombre(eventoEscolar.getNombre());
            entity.setFecha(eventoEscolar.getFecha());
            entity.setDescripcion(eventoEscolar.getDescripcion());
            if (eventoEscolar.getAlumnos() != null) {
                List<AlumnoEntity> updatedAlumnos = eventoEscolar.getAlumnos().stream()
                        .map(AlumnoEntity::fromDomain)
                        .collect(Collectors.toList());
                entity.setAlumnos(updatedAlumnos);
            } else {
                entity.setAlumnos(new ArrayList<>());
            }

            if (eventoEscolar.getMaestros() != null) {
                List<MaestroEntity> updatedMaestros = eventoEscolar.getMaestros().stream()
                        .map(MaestroEntity::fromDomain)
                        .collect(Collectors.toList());
                entity.setMaestros(updatedMaestros);
            } else {
                entity.setMaestros(new ArrayList<>());
            }
            persist(entity);

        } else {
            throw new RuntimeException("EventoEscolar no encontrado con el ID: " + eventoEscolar.getId());
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

    @Override
    public List<EventoEscolar> getEventosByAlumnoId(Long alumnoId) {
        return getEntityManager().createQuery(
                        "SELECT DISTINCT e FROM EventoEscolarEntity e JOIN e.alumnos a WHERE a.id = :alumnoId",
                        EventoEscolarEntity.class)
                .setParameter("alumnoId", alumnoId)
                .getResultList()
                .stream()
                .map(evento -> {
                    EventoEscolar domainEvento = evento.toDomain();
                    // Filtrar alumnos para incluir solo el alumno especificado
                    domainEvento.setAlumnos(domainEvento.getAlumnos().stream()
                            .filter(alumno -> alumno.getId().equals(alumnoId))
                            .collect(Collectors.toList()));
                    return domainEvento;
                })
                .collect(Collectors.toList());
    }
}

