package edu.school.app.application.service;

import edu.school.app.domain.model.Maestro;
import edu.school.app.domain.repository.MaestroRepository;
import edu.school.app.domain.service.MaestroService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class MaestroServiceImpl implements MaestroService {

    private final MaestroRepository maestroRepository;

    @Override
    public Maestro createMaestro(Maestro maestro) {
        maestroRepository.save(maestro);
        return maestro;
    }

    @Override
    public Maestro getMaestroById(Long id) {
        return maestroRepository.findMaestroById(id)
                .orElseThrow(() -> new RuntimeException("Maestro no encontrado"));
    }

    @Override
    public List<Maestro> getAllMaestros(int page, int size) {
        return maestroRepository.listMaestros(page, size);
    }

    @Override
    public Maestro updateMaestro(Long id, Maestro maestro) {
        Maestro existingMaestro = getMaestroById(id);
        existingMaestro.setNombre(maestro.getNombre());
        existingMaestro.setApellido(maestro.getApellido());
        existingMaestro.setEspecialidad(maestro.getEspecialidad());
        maestroRepository.update(existingMaestro);
        return existingMaestro;
    }

    @Override
    public void deleteMaestro(Long id) {
        maestroRepository.delete(id);
    }

    @Override
    public long countMaestros() {
        return maestroRepository.countMaestros();
    }
}
