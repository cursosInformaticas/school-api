package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Alumno;
import edu.school.app.domain.model.Curso;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "curso")
@Getter
@Setter
public class CursoEntity extends PanacheEntity {

    public String nombre;
    public String descripcion;

    @ManyToOne
    @JoinColumn(name = "grado_escolar_id")
    private GradoEscolarEntity gradoEscolar;

    @ManyToOne
    @JoinColumn(name = "maestro_id")
    private MaestroEntity maestro;

    @ManyToMany
    @JoinTable(
            name = "curso_alumno",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<AlumnoEntity> alumnos;

    public Curso toDomain() {
        List<Alumno> alumnosDomain = this.alumnos != null
                ? this.alumnos.stream().map(AlumnoEntity::toDomain).collect(Collectors.toList())
                : new ArrayList<>();

        return new Curso(
                this.id,
                this.nombre,
                this.descripcion,
                this.gradoEscolar != null ? this.gradoEscolar.toDomain() : null,
                this.maestro != null ? this.maestro.toDomain() : null,
                alumnosDomain
        );
    }

    public static CursoEntity fromDomain(Curso curso) {
        CursoEntity entity = new CursoEntity();
        entity.id = curso.getId();
        entity.nombre = curso.getNombre();
        entity.descripcion = curso.getDescripcion();

        if (curso.getGradoEscolar() != null) {
            entity.gradoEscolar = GradoEscolarEntity.fromDomain(curso.getGradoEscolar());
        }
        if (curso.getMaestro() != null) {
            entity.maestro = MaestroEntity.fromDomain(curso.getMaestro());
        }
        if (curso.getAlumnos() != null) {
            entity.alumnos = curso.getAlumnos().stream()
                    .map(AlumnoEntity::fromDomain)
                    .collect(Collectors.toList());
        }
        return entity;
    }
}