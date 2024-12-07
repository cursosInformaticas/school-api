package edu.school.app.infrastructure.repository;

import edu.school.app.domain.model.Alumno;
import edu.school.app.domain.repository.AlumnoRepository;
import edu.school.app.infrastructure.entity.AlumnoEntity;
import edu.school.app.infrastructure.entity.AnioEscolarEntity;
import edu.school.app.infrastructure.entity.TutorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlumnoRepositoryImpl implements AlumnoRepository, PanacheRepository<AlumnoEntity> {

    @Override
    public void save(Alumno alumno) {
        persist(AlumnoEntity.fromDomain(alumno));
    }

    @Override
    public Optional<Alumno> findAlumnoById(Long id) {
        return find("id", id).firstResultOptional().map(AlumnoEntity::toDomain);
    }

    @Override
    public List<Alumno> listAlumnos(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .stream()
                .map(AlumnoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Alumno alumno) {
        AlumnoEntity entity = findById(alumno.getId());
        if (entity != null) {
            entity.setNombre(alumno.getNombre());
            entity.setApellido(alumno.getApellido());
            entity.setEmail(alumno.getEmail());

            if (alumno.getTutor() != null) {
                TutorEntity tutorEntity = TutorEntity.fromDomain(alumno.getTutor());
                entity.setTutor(tutorEntity);
            } else {
                entity.setTutor(null);
            }

            if (alumno.getAnioEscolar() != null) {
                AnioEscolarEntity tutorEntity = AnioEscolarEntity.fromDomain(alumno.getAnioEscolar());
                entity.setAnioEscolar(tutorEntity);
            } else {
                entity.setAnioEscolar(null);
            }
            persist(entity);
        } else {
            throw new RuntimeException("Alumno no encontrado con el ID: " + alumno.getId());
        }
    }

    @Override
    public void delete(Long id) {
        delete("id", id);
    }

    public boolean existsByEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }

    public boolean hasDependencies(Long alumnoId) {
        long count = getEntityManager().createQuery(
                        "SELECT COUNT(c) FROM CursoEntity c JOIN c.alumnos a WHERE a.id = :alumnoId", Long.class)
                .setParameter("alumnoId", alumnoId)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public long countAlumnos() {
        return count();
    }

    @Override
    public List<Alumno> findAllAlumnos() {
        return findAll()
                .stream()
                .map(AlumnoEntity::toDomain)
                .collect(Collectors.toList());
    }

    public List<Alumno> findAlumnosByAnioEscolar(int anioEscolar) {
        return find("anioEscolar.anio", anioEscolar)
                .stream()
                .map(AlumnoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alumno> findAlumnosByAnioEscolarPaged(int anioEscolar, int page, int size) {
        return find("anioEscolar.anio", anioEscolar)
                .page(Page.of(page, size))
                .stream()
                .map(AlumnoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countAlumnosByAnioEscolar(int anioEscolar) {
        return count("anioEscolar.anio", anioEscolar);
    }

}

