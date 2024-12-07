package edu.school.app.domain.repository;

import edu.school.app.domain.model.Horario;
import java.util.List;
import java.util.Optional;

public interface HorarioRepository {
    void save(Horario horario);
    Optional<Horario> findHorarioById(Long id);
    List<Horario> listHorarios(int page, int size);
    void update(Horario horario);
    void delete(Long id);
    long countHorarios();
    List<Horario> getHorariosByAsignaturaIds(List<Long> asignaturaIds);
    List<Horario> findAllHorarios();
}
