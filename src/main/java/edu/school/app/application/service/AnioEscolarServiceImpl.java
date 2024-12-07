package edu.school.app.application.service;

import edu.school.app.domain.model.AnioEscolar;
import edu.school.app.domain.repository.AnioEscolarRepository;
import edu.school.app.domain.service.AnioEscolarService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class AnioEscolarServiceImpl implements AnioEscolarService {

    private final AnioEscolarRepository anioEscolarRepository;

    @Override
    public AnioEscolar createAnioEscolar(AnioEscolar anioEscolar) {
        Optional<AnioEscolar> existingAnio = anioEscolarRepository.listAllAniosEscolares().stream()
                .filter(a -> a.getAnio().equals(anioEscolar.getAnio()))
                .findFirst();

        if (existingAnio.isPresent()) {
            throw new IllegalArgumentException("El año escolar ya existe");
        }

        // Guardar el año escolar
        anioEscolarRepository.save(anioEscolar);
        return anioEscolar;
    }

    @Override
    public AnioEscolar updateAnioEscolar(Long id, AnioEscolar anioEscolar) {
        AnioEscolar existingAnio = getAnioEscolarById(id);

        // Actualizar los campos
        existingAnio.setAnio(anioEscolar.getAnio());
        existingAnio.setDescripcion(anioEscolar.getDescripcion());

        // Persistir los cambios
        anioEscolarRepository.update(existingAnio);
        return existingAnio;
    }

    @Override
    public AnioEscolar getAnioEscolarById(Long id) {
        return anioEscolarRepository.getAnioEscolarById(id)
                .orElseThrow(() -> new RuntimeException("AnioEscolar no encontrado"));
    }

    @Override
    public List<AnioEscolar> listAllAniosEscolares() {
        return anioEscolarRepository.listAllAniosEscolares();
    }
}
