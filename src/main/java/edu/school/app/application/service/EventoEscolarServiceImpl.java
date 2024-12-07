package edu.school.app.application.service;

import edu.school.app.domain.model.EventoEscolar;
import edu.school.app.domain.repository.EventoEscolarRepository;
import edu.school.app.domain.service.EventoEscolarService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class EventoEscolarServiceImpl implements EventoEscolarService {

    private final EventoEscolarRepository eventoEscolarRepository;

    @Override
    public EventoEscolar createEventoEscolar(EventoEscolar eventoEscolar) {
        eventoEscolarRepository.save(eventoEscolar);
        return eventoEscolar;
    }

    @Override
    public EventoEscolar getEventoEscolarById(Long id) {
        return eventoEscolarRepository.findEventoEscolarById(id)
                .orElseThrow(() -> new RuntimeException("EventoEscolar no encontrado"));
    }

    @Override
    public List<EventoEscolar> getAllEventoEscolars(int page, int size) {
        return eventoEscolarRepository.listEventoEscolares(page, size);
    }

    @Override
    public EventoEscolar updateEventoEscolar(Long id, EventoEscolar eventoEscolar) {
        EventoEscolar existingEventoEscolar = getEventoEscolarById(id);
        existingEventoEscolar.setNombre(eventoEscolar.getNombre());
        existingEventoEscolar.setFecha(eventoEscolar.getFecha());
        existingEventoEscolar.setDescripcion(eventoEscolar.getDescripcion());
        existingEventoEscolar.setMaestros(eventoEscolar.getMaestros());
        existingEventoEscolar.setAlumnos(eventoEscolar.getAlumnos());
        eventoEscolarRepository.update(existingEventoEscolar);
        return existingEventoEscolar;
    }

    @Override
    public void deleteEventoEscolar(Long id) {
        eventoEscolarRepository.delete(id);
    }

    @Override
    public long countEscolares() {
        return eventoEscolarRepository.countEscolares();
    }
}
