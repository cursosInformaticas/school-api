package edu.school.app.infrastructure.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import edu.school.app.domain.model.Asistencia;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencia")
@Getter
@Setter
public class AsistenciaEntity extends PanacheEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime fecha;

    public boolean presente;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private AlumnoEntity alumno;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoEntity curso;

    public Asistencia toDomain() {
        return new Asistencia(this.id, this.fecha, this.presente,
                this.alumno != null ? this.alumno.toDomain() : null,
                this.curso != null ? this.curso.toDomain() : null);
    }

    public static AsistenciaEntity fromDomain(Asistencia asistencia) {
        AsistenciaEntity entity = new AsistenciaEntity();
        entity.id = asistencia.getId();
        entity.fecha = asistencia.getFecha();
        entity.presente = asistencia.isPresente();
        if (asistencia.getAlumno() != null) {
            entity.alumno = AlumnoEntity.fromDomain(asistencia.getAlumno());
        }
        if (asistencia.getCurso() != null) {
            entity.curso = CursoEntity.fromDomain(asistencia.getCurso());
        }
        return entity;
    }
}
