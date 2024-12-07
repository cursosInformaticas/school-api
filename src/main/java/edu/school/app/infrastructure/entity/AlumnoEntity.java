package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Alumno;
import edu.school.app.infrastructure.repository.AlumnoRepositoryImpl;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "alumno")
@Getter
@Setter
public class AlumnoEntity extends PanacheEntity {

    public String nombre;
    public String apellido;
    public String email;

    @ManyToMany(mappedBy = "alumnos")
    private List<CursoEntity> cursos;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private TutorEntity tutor;

    @ManyToOne
    @JoinColumn(name = "anio_escolar_id")
    private AnioEscolarEntity anioEscolar;


    public Alumno toDomain() {
        boolean dependencies = new AlumnoRepositoryImpl().hasDependencies(this.id);
        return new Alumno(
                this.id, this.nombre,
                this.apellido, this.email,
                this.tutor != null ? this.tutor.toDomain() : null,
                dependencies,
                this.anioEscolar != null ? this.anioEscolar.toDomain() : null);
    }

    public static AlumnoEntity fromDomain(Alumno alumno) {
        AlumnoEntity entity = new AlumnoEntity();
        entity.id = alumno.getId();
        entity.nombre = alumno.getNombre();
        entity.apellido = alumno.getApellido();
        entity.email = alumno.getEmail();
        if (alumno.getTutor() != null) {
            entity.tutor = TutorEntity.fromDomain(alumno.getTutor());
        }
        if (alumno.getAnioEscolar() != null && alumno.getAnioEscolar().getId() != null) {
            entity.anioEscolar = AnioEscolarEntity.findById(alumno.getAnioEscolar().getId());
            if (entity.anioEscolar == null) {
                throw new IllegalArgumentException("AnioEscolar no encontrado con el ID: " + alumno.getAnioEscolar().getId());
            }
        }
        return entity;
    }
}
