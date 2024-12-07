package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Asignatura;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "asignatura")
@Getter
@Setter
public class AsignaturaEntity extends PanacheEntity {

    public String nombre;
    public String descripcion;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoEntity curso;


    public Asignatura toDomain() {
        return new Asignatura(this.id, this.nombre, this.descripcion,
                this.curso != null ? this.curso.toDomain() : null );
    }

    public static AsignaturaEntity fromDomain(Asignatura asignatura) {
        AsignaturaEntity entity = new AsignaturaEntity();
        entity.id = asignatura.getId();
        entity.nombre = asignatura.getNombre();
        entity.descripcion = asignatura.getDescripcion();
        if (asignatura.getCurso() != null) {
            entity.curso = CursoEntity.fromDomain(asignatura.getCurso());
        }
        return entity;
    }

}
