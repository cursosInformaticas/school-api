package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Asistencia;
import edu.school.app.domain.repository.AsistenciaRepository;
import edu.school.app.infrastructure.entity.AlumnoEntity;
import edu.school.app.infrastructure.entity.AsistenciaEntity;
import edu.school.app.infrastructure.entity.CursoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AsistenciaRepositoryImpl implements AsistenciaRepository, PanacheRepository<AsistenciaEntity> {

    @Override
    public void save(Asistencia asistencia) {
        persist(AsistenciaEntity.fromDomain(asistencia));
    }

    @Override
    public Optional<Asistencia> findAsistenciaById(Long id) {
        return find("id", id).firstResultOptional().map(AsistenciaEntity::toDomain);
    }

    @Override
    public List<Asistencia> listAsistencias(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(AsistenciaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Asistencia asistencia) {
        AsistenciaEntity entity = findById(asistencia.getId());
        if (entity != null) {
            entity.setFecha(asistencia.getFecha());
            entity.setPresente(asistencia.isPresente());
            if (asistencia.getCurso() != null) {
                CursoEntity cursoEntity = CursoEntity.fromDomain(asistencia.getCurso());
                entity.setCurso(cursoEntity);
            } else {
                entity.setCurso(null);
            }
            if (asistencia.getAlumno() != null) {
                AlumnoEntity alumnoEntity = AlumnoEntity.fromDomain(asistencia.getAlumno());
                entity.setAlumno(alumnoEntity);
            } else {
                entity.setAlumno(null);
            }
            persist(entity);
        } else {
            throw new RuntimeException("Asistencia no encontrado con el ID: " + asistencia.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countAsistencias() {
        return count();
    }
    @Override
    public List<Asistencia> getAsistenciasByAlumnoId(Long alumnoId) {
        return find("alumno.id = ?1", alumnoId)
                .stream()
                .map(AsistenciaEntity::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public List<Asistencia> findAllAsistencias() {
        return findAll()
                .stream()
                .map(AsistenciaEntity::toDomain)
                .collect(Collectors.toList());
    }

}

