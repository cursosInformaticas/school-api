package edu.school.app.application.service;

import edu.school.app.domain.model.Aula;
import edu.school.app.domain.repository.AulaRepository;
import edu.school.app.domain.service.AulaService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;

    @Override
    public Aula createAula(Aula aula) {
        aulaRepository.save(aula);
        return aula;
    }

    @Override
    public Aula getAulaById(Long id) {
        return aulaRepository.findAulaById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrado"));
    }

    @Override
    public List<Aula> getAllAulas(int page, int size) {
        return aulaRepository.listAulas(page, size);
    }

    @Override
    public Aula updateAula(Long id, Aula aula) {
        Aula existingAula = getAulaById(id);
        existingAula.setNombre(aula.getNombre());
        existingAula.setCapacidad(aula.getCapacidad());
        aulaRepository.update(existingAula);
        return existingAula;
    }

    @Override
    public void deleteAula(Long id) {
        aulaRepository.delete(id);
    }

    @Override
    public long countAulas() {
        return aulaRepository.countAulas();
    }
}
