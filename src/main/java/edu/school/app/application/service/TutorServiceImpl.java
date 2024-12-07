package edu.school.app.application.service;

import edu.school.app.config.EmailAlreadyExistsException;
import edu.school.app.domain.model.Tutor;
import edu.school.app.domain.repository.TutorRepository;
import edu.school.app.domain.service.TutorService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;

    @Override
    public Tutor createTutor(Tutor tutor) {
        if (tutorRepository.existsByEmail(tutor.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está en uso");
        }
        tutorRepository.save(tutor);
        return tutor;
    }

    @Override
    public Tutor getTutorById(Long id) {
        return tutorRepository.findTutorById(id)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));
    }

    @Override
    public List<Tutor> getAllTutors(int page, int size) {
        return tutorRepository.listTutores(page, size);
    }

    @Override
    public Tutor updateTutor(Long id, Tutor tutor) {
        Tutor existingTutor = getTutorById(id);
        if (!existingTutor.getEmail().equals(tutor.getEmail()) &&
                tutorRepository.existsByEmail(tutor.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está en uso por otro alumno");
        }
        existingTutor.setNombre(tutor.getNombre());
        existingTutor.setApellido(tutor.getApellido());
        existingTutor.setEmail(tutor.getEmail());
        existingTutor.setTelefono(tutor.getTelefono());
        tutorRepository.update(existingTutor);
        return existingTutor;
    }

    @Override
    public void deleteTutor(Long id) {
        tutorRepository.delete(id);
    }

    @Override
    public long countTutores() {
        return tutorRepository.countTutores();
    }
}
