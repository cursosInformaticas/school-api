package edu.school.app.infrastructure.entity;

import edu.school.app.domain.model.Tutor;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "tutor")
@Getter
@Setter
public class TutorEntity extends PanacheEntity {

    public String nombre;
    public String apellido;
    public String email;
    public String telefono;

    @OneToMany(mappedBy = "tutor")
    private List<AlumnoEntity> alumnos;

    public Tutor toDomain() {
        return new Tutor(this.id, this.nombre, this.apellido, this.email, this.telefono);
    }

    public static TutorEntity fromDomain(Tutor tutor) {
        TutorEntity entity = new TutorEntity();
        entity.id = tutor.getId();
        entity.nombre = tutor.getNombre();
        entity.apellido = tutor.getApellido();
        entity.email = tutor.getEmail();
        entity.telefono = tutor.getTelefono();
        return entity;
    }
}
