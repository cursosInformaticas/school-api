package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.GradoEscolar;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "grado_escolar")
@Getter
@Setter
public class GradoEscolarEntity extends PanacheEntity {

    public String nombre;

    @OneToMany(mappedBy = "gradoEscolar")
    private List<CursoEntity> cursos;

    public GradoEscolar toDomain() {
        return new GradoEscolar(this.id, this.nombre);
    }

    public static GradoEscolarEntity fromDomain(GradoEscolar gradoEscolar) {
        GradoEscolarEntity entity = new GradoEscolarEntity();
        entity.id = gradoEscolar.getId();
        entity.nombre = gradoEscolar.getNombre();
        return entity;
    }
}
