package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Horario;
import edu.school.app.domain.repository.HorarioRepository;
import edu.school.app.infrastructure.entity.AsignaturaEntity;
import edu.school.app.infrastructure.entity.AulaEntity;
import edu.school.app.infrastructure.entity.HorarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class HorarioRepositoryImpl implements HorarioRepository, PanacheRepository<HorarioEntity> {

    @Override
    public void save(Horario horario) {
        persist(HorarioEntity.fromDomain(horario));
    }

    @Override
    public Optional<Horario> findHorarioById(Long id) {
        return find("id", id).firstResultOptional().map(HorarioEntity::toDomain);
    }

    @Override
    public List<Horario> listHorarios(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(HorarioEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Horario horario) {
        HorarioEntity entity = findById(horario.getId());
        if (entity != null) {
            entity.setDiaSemana(horario.getDiaSemana());
            entity.setHoraInicio(horario.getHoraInicio());
            entity.setHoraFin(horario.getHoraFin());

            if (horario.getAsignatura() != null) {
                AsignaturaEntity asignaturaEntity = AsignaturaEntity.fromDomain(horario.getAsignatura());
                entity.setAsignatura(asignaturaEntity);
            } else {
                entity.setAsignatura(null);
            }
            if (horario.getAula() != null) {
                AulaEntity aulaEntity = AulaEntity.fromDomain(horario.getAula());
                entity.setAula(aulaEntity);
            } else {
                entity.setAula(null);
            }
            persist(entity);
        } else {
            throw new RuntimeException("Horario no encontrado con el ID: " + horario.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countHorarios() {
        return count();
    }

    @Override
    public List<Horario> getHorariosByAsignaturaIds(List<Long> asignaturaIds) {
        return find("asignatura.id IN ?1", asignaturaIds)
                .stream()
                .map(HorarioEntity::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public List<Horario> findAllHorarios() {
        return findAll()
                .stream()
                .map(HorarioEntity::toDomain)
                .collect(Collectors.toList());
    }

}

