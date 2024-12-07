package edu.school.app.domain.repository;

import edu.school.app.domain.model.EventoEscolar;
import java.util.List;
import java.util.Optional;

public interface EventoEscolarRepository {
    void save(EventoEscolar eventoEscolar);
    Optional<EventoEscolar> findEventoEscolarById(Long id);
    List<EventoEscolar> listEventoEscolares(int page, int size);
    void update(EventoEscolar eventoEscolar);
    void delete(Long id);
    long countEscolares();
    List<EventoEscolar> getEventosByAlumnoId(Long alumnoId);
}

