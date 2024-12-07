package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.AnioEscolar;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "anio_escolar")
@Getter
@Setter
public class AnioEscolarEntity extends PanacheEntity {

    public int anio;
    public String descripcion;

    public AnioEscolar toDomain() {
        return new AnioEscolar(this.id, this.anio, this.descripcion);
    }

    public static AnioEscolarEntity fromDomain(AnioEscolar anioEscolar) {
        AnioEscolarEntity entity = new AnioEscolarEntity();
        entity.id = anioEscolar.getId();
        entity.anio = anioEscolar.getAnio();
        entity.descripcion = anioEscolar.getDescripcion();
        return entity;
    }

}