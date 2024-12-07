package edu.school.app.domain.service;

import edu.school.app.domain.model.Asignatura;
import java.util.List;

public interface AsignaturaService {
    Asignatura createAsignatura(Asignatura asignatura);
    Asignatura getAsignaturaById(Long id);
    List<Asignatura> getAllAsignaturas(int page, int size);
    Asignatura updateAsignatura(Long id, Asignatura asignatura);
    void deleteAsignatura(Long id);
    long countAsignaturas();
}
