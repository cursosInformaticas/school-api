package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Examen;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "examen")
@Getter
@Setter
public class ExamenEntity extends PanacheEntity {

    public String nombre;
    public LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "asignatura_id")
    private AsignaturaEntity asignatura;


    public Examen toDomain() {
        return new Examen(
                this.id,
                this.nombre,
                this.fecha,
                this.asignatura != null ? this.asignatura.toDomain() : null
        );
    }

    public static ExamenEntity fromDomain(Examen examen) {
        ExamenEntity entity = new ExamenEntity();
        entity.id = examen.getId();
        entity.nombre = examen.getNombre();
        entity.fecha = examen.getFecha();
        if (examen.getAsignatura() != null) {
            entity.asignatura = AsignaturaEntity.fromDomain(examen.getAsignatura());
        }
        return entity;
    }
}

