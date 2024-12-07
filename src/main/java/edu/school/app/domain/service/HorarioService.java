package edu.school.app.domain.service;

import edu.school.app.domain.model.Horario;

import java.util.List;

public interface HorarioService {
    Horario createHorario(Horario horario);
    Horario getHorarioById(Long id);
    List<Horario> getAllHorarios(int page, int size);
    Horario updateHorario(Long id, Horario horario);
    void deleteHorario(Long id);
    long countHorarios();
}
