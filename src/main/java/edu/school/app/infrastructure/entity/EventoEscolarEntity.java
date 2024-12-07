package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Alumno;
import edu.school.app.domain.model.EventoEscolar;
import edu.school.app.domain.model.Maestro;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "evento_escolar")
@Getter
@Setter
public class EventoEscolarEntity extends PanacheEntity {

    public String nombre;
    public LocalDate fecha;
    public String descripcion;

    @ManyToMany
    @JoinTable(
            name = "evento_alumno",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<AlumnoEntity> alumnos;

    @ManyToMany
    @JoinTable(
            name = "evento_maestro",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "maestro_id")
    )
    private List<MaestroEntity> maestros;


    public EventoEscolar toDomain() {
        List<Alumno> alumnosDomain = this.alumnos != null
                ? this.alumnos.stream().map(AlumnoEntity::toDomain).collect(Collectors.toList())
                : new ArrayList<>();

        List<Maestro> maestrosDomain = this.maestros != null
                ? this.maestros.stream().map(MaestroEntity::toDomain).collect(Collectors.toList())
                : new ArrayList<>();

        return new EventoEscolar(
                this.id,
                this.nombre,
                this.fecha,
                this.descripcion,
                alumnosDomain,
                maestrosDomain
        );
    }

    public static EventoEscolarEntity fromDomain(EventoEscolar eventoEscolar) {
        EventoEscolarEntity entity = new EventoEscolarEntity();
        entity.id = eventoEscolar.getId();
        entity.nombre = eventoEscolar.getNombre();
        entity.fecha = eventoEscolar.getFecha();
        entity.descripcion = eventoEscolar.getDescripcion();

        if (eventoEscolar.getAlumnos() != null) {
            entity.alumnos = eventoEscolar.getAlumnos().stream()
                    .map(AlumnoEntity::fromDomain)
                    .collect(Collectors.toList());
        }
        if (eventoEscolar.getMaestros() != null) {
            entity.maestros = eventoEscolar.getMaestros().stream()
                    .map(MaestroEntity::fromDomain)
                    .collect(Collectors.toList());
        }
        return entity;
    }
}
