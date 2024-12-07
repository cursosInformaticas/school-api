package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Curso;
import edu.school.app.domain.repository.CursoRepository;
import edu.school.app.infrastructure.entity.AlumnoEntity;
import edu.school.app.infrastructure.entity.CursoEntity;
import edu.school.app.infrastructure.entity.GradoEscolarEntity;
import edu.school.app.infrastructure.entity.MaestroEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CursoRepositoryImpl implements CursoRepository, PanacheRepository<CursoEntity> {

    @Override
    public void save(Curso curso) {
        persist(CursoEntity.fromDomain(curso));
    }

    @Override
    public Optional<Curso> findCursoById(Long id) {
        return find("id", id).firstResultOptional().map(CursoEntity::toDomain);
    }

    @Override
    public List<Curso> listCursos(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(CursoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Curso curso) {
        CursoEntity entity = findById(curso.getId());
        if (entity != null) {
            entity.setNombre(curso.getNombre());
            entity.setDescripcion(curso.getDescripcion());
            // Actualizar gradoEscolar
            if (curso.getGradoEscolar() != null) {
                entity.setGradoEscolar(GradoEscolarEntity.fromDomain(curso.getGradoEscolar()));
            } else {
                entity.setGradoEscolar(null);
            }
            // Actualizar maestro
            if (curso.getMaestro() != null) {
                entity.setMaestro(MaestroEntity.fromDomain(curso.getMaestro()));
            } else {
                entity.setMaestro(null);
            }
            if (curso.getAlumnos() != null) {
                List<AlumnoEntity> updatedAlumnos = curso.getAlumnos().stream()
                        .map(AlumnoEntity::fromDomain)
                        .collect(Collectors.toList());
                entity.setAlumnos(updatedAlumnos);
            } else {
                entity.setAlumnos(new ArrayList<>());
            }
            persist(entity);
        } else {
            throw new RuntimeException("Curso no encontrado con el ID: " + curso.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    @Override
    public long countCursos() {
        return count();
    }

    @Override
    public List<Curso> getCursosByAlumnoId(Long alumnoId) {
        return getEntityManager().createQuery(
                        "SELECT DISTINCT c FROM CursoEntity c JOIN c.alumnos a WHERE a.id = :alumnoId",
                        CursoEntity.class)
                .setParameter("alumnoId", alumnoId)
                .getResultList()
                .stream()
                .map(curso -> {
                    Curso domainCurso = curso.toDomain();
                    // Filtrar alumnos para incluir solo el alumno especificado
                    domainCurso.setAlumnos(domainCurso.getAlumnos().stream()
                            .filter(alumno -> alumno.getId().equals(alumnoId))
                            .collect(Collectors.toList()));
                    return domainCurso;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<Curso> findAllCursos() {
        return findAll()
                .stream()
                .map(CursoEntity::toDomain)
                .collect(Collectors.toList());
    }

}