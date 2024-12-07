package edu.school.app.domain.service;

import edu.school.app.domain.model.Tutor;

import java.util.List;

public interface TutorService {
    Tutor createTutor(Tutor tutor);
    Tutor getTutorById(Long id);
    List<Tutor> getAllTutors(int page, int size);
    Tutor updateTutor(Long id, Tutor tutor);
    void deleteTutor(Long id);
    long countTutores();
}
