package edu.school.app.application.service;

import edu.school.app.domain.model.GradoEscolar;
import edu.school.app.domain.repository.GradoEscolarRepository;
import edu.school.app.domain.service.GradoEscolarService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class GradoEscolarServiceImpl implements GradoEscolarService {

    private final GradoEscolarRepository gradoEscolarRepository;

    @Override
    public GradoEscolar createGradoEscolar(GradoEscolar gradoEscolar) {
        gradoEscolarRepository.save(gradoEscolar);
        return gradoEscolar;
    }

    @Override
    public GradoEscolar getGradoEscolarById(Long id) {
        return gradoEscolarRepository.findGradoEscolarById(id)
                .orElseThrow(() -> new RuntimeException("GradoEscolar no encontrado"));
    }

    @Override
    public List<GradoEscolar> getAllGradoEscolars(int page, int size) {
        return gradoEscolarRepository.listGradoEscolares(page, size);
    }

    @Override
    public GradoEscolar updateGradoEscolar(Long id, GradoEscolar gradoEscolar) {
        GradoEscolar existingGradoEscolar = getGradoEscolarById(id);
        existingGradoEscolar.setNombre(gradoEscolar.getNombre());
        gradoEscolarRepository.update(existingGradoEscolar);
        return existingGradoEscolar;
    }

    @Override
    public void deleteGradoEscolar(Long id) {
        gradoEscolarRepository.delete(id);
    }

    @Override
    public long countEscolares() {
        return gradoEscolarRepository.countEscolares();
    }
}
