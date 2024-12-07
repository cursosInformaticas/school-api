package edu.school.app.domain.service;

import edu.school.app.domain.model.Asistencia;
import java.util.List;

public interface AsistenciaService {
    Asistencia createAsistencia(Asistencia asistencia);
    Asistencia getAsistenciaById(Long id);
    List<Asistencia> getAllAsistencias(int page, int size);
    Asistencia updateAsistencia(Long id, Asistencia asistencia);
    void deleteAsistencia(Long id);
    long countAsistencias();
}
