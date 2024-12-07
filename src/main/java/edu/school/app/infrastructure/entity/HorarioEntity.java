package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Horario;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Entity
@Table(name = "horario")
@Getter
@Setter
public class HorarioEntity extends PanacheEntity {

    public String diaSemana;  // Ejemplo: "Lunes", "Martes"
    public LocalTime horaInicio;
    public LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "asignatura_id")
    private AsignaturaEntity asignatura;

    @ManyToOne
    @JoinColumn(name = "aula_id")
    private AulaEntity aula;

    public Horario toDomain() {
        return new Horario(
                this.id,
                this.diaSemana,
                this.horaInicio,
                this.horaFin,
                this.asignatura != null ? this.asignatura.toDomain() : null,
                this.aula != null ? this.aula.toDomain() : null
        );
    }

    public static HorarioEntity fromDomain(Horario horario) {
        HorarioEntity entity = new HorarioEntity();
        entity.id = horario.getId();
        entity.diaSemana = horario.getDiaSemana();
        entity.horaInicio = horario.getHoraInicio();
        entity.horaFin = horario.getHoraFin();

        if (horario.getAsignatura() != null) {
            entity.asignatura = AsignaturaEntity.fromDomain(horario.getAsignatura());
        }
        if (horario.getAula() != null) {
            entity.aula = AulaEntity.fromDomain(horario.getAula());
        }
        return entity;
    }
}
