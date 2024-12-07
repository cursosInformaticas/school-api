package edu.school.app.domain.service;

import edu.school.app.domain.model.EventoEscolar;

import java.util.List;

public interface EventoEscolarService {
    EventoEscolar createEventoEscolar(EventoEscolar eventoEscolar);
    EventoEscolar getEventoEscolarById(Long id);
    List<EventoEscolar> getAllEventoEscolars(int page, int size);
    EventoEscolar updateEventoEscolar(Long id, EventoEscolar eventoEscolar);
    void deleteEventoEscolar(Long id);
    long countEscolares();
}
