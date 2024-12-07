package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Maestro;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "maestro")
@Getter
@Setter
public class MaestroEntity extends PanacheEntity {

    public String nombre;
    public String apellido;
    public String especialidad;

    @OneToMany(mappedBy = "maestro")
    private List<CursoEntity> cursos;

    public Maestro toDomain() {
        return new Maestro(this.id, this.nombre, this.apellido, this.especialidad);
    }

    public static MaestroEntity fromDomain(Maestro maestro) {
        MaestroEntity entity = new MaestroEntity();
        entity.id = maestro.getId();
        entity.nombre = maestro.getNombre();
        entity.apellido = maestro.getApellido();
        entity.especialidad = maestro.getEspecialidad();
        return entity;
    }
}
