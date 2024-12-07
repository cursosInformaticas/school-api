package edu.school.app.application.service;

import edu.school.app.domain.model.Horario;
import edu.school.app.domain.repository.HorarioRepository;
import edu.school.app.domain.service.HorarioService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;

    @Override
    public Horario createHorario(Horario horario) {
        horarioRepository.save(horario);
        return horario;
    }

    @Override
    public Horario getHorarioById(Long id) {
        return horarioRepository.findHorarioById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
    }

    @Override
    public List<Horario> getAllHorarios(int page, int size) {
        return horarioRepository.listHorarios(page, size);
    }

    @Override
    public Horario updateHorario(Long id, Horario horario) {
        Horario existingHorario = getHorarioById(id);
        existingHorario.setDiaSemana(horario.getDiaSemana());
        existingHorario.setHoraInicio(horario.getHoraInicio());
        existingHorario.setHoraFin(horario.getHoraFin());
        existingHorario.setAsignatura(horario.getAsignatura());
        existingHorario.setAula(horario.getAula());
        horarioRepository.update(existingHorario);
        return existingHorario;
    }

    @Override
    public void deleteHorario(Long id) {
        horarioRepository.delete(id);
    }

    @Override
    public long countHorarios() {
        return horarioRepository.countHorarios();
    }
}
