package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Calificacion;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "calificacion")
@Getter
@Setter
public class CalificacionEntity extends PanacheEntity {

    public double puntaje;

    @ManyToOne
    @JoinColumn(name = "examen_id")
    private ExamenEntity examen;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private AlumnoEntity alumno;

    public Calificacion toDomain() {
        return new Calificacion(
                this.id,
                this.puntaje,
                this.examen != null ? this.examen.toDomain() : null,
                this.alumno != null ? this.alumno.toDomain() : null
        );
    }

    public static CalificacionEntity fromDomain(Calificacion calificacion) {
        CalificacionEntity entity = new CalificacionEntity();
        entity.id = calificacion.getId();
        entity.puntaje = calificacion.getPuntaje();
        if (calificacion.getExamen() != null) {
            entity.examen = ExamenEntity.fromDomain(calificacion.getExamen());
        }
        if (calificacion.getAlumno() != null) {
            entity.alumno = AlumnoEntity.fromDomain(calificacion.getAlumno());
        }
        return entity;
    }
}
