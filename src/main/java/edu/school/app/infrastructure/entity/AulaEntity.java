package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Aula;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "aula")
@Getter
@Setter
public class AulaEntity extends PanacheEntity {

    public String nombre;
    public int capacidad;

    @OneToMany(mappedBy = "aula")
    private List<HorarioEntity> horarios;


    public Aula toDomain() {
        return new Aula(this.id, this.nombre, this.capacidad);
    }

    public static AulaEntity fromDomain(Aula aula) {
        AulaEntity entity = new AulaEntity();
        entity.id = aula.getId();
        entity.nombre = aula.getNombre();
        entity.capacidad = aula.getCapacidad();
        return entity;
    }
}

