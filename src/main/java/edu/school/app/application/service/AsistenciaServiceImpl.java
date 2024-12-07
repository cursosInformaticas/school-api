package edu.school.app.application.service;

import edu.school.app.domain.model.Asistencia;
import edu.school.app.domain.repository.AsistenciaRepository;
import edu.school.app.domain.service.AsistenciaService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    @Override
    public Asistencia createAsistencia(Asistencia asistencia) {
        asistenciaRepository.save(asistencia);
        return asistencia;
    }

    @Override
    public Asistencia getAsistenciaById(Long id) {
        return asistenciaRepository.findAsistenciaById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrado"));
    }

    @Override
    public List<Asistencia> getAllAsistencias(int page, int size) {
        return asistenciaRepository.listAsistencias(page, size);
    }

    @Override
    public Asistencia updateAsistencia(Long id, Asistencia asistencia) {
        Asistencia existingAsistencia = getAsistenciaById(id);
       /* LocalDate today = LocalDate.now();
        if (!asistencia.getFecha().equals(today)) {
            throw new IllegalArgumentException("La asistencia solo puede ser actualizada para el día actual.");
        }
        DayOfWeek dayOfWeek = asistencia.getFecha().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("La asistencia solo puede ser actualizada de lunes a viernes.");
        }*/
        existingAsistencia.setFecha(asistencia.getFecha());
        existingAsistencia.setPresente(asistencia.isPresente());
        existingAsistencia.setCurso(asistencia.getCurso());
        existingAsistencia.setAlumno(asistencia.getAlumno());
        asistenciaRepository.update(existingAsistencia);
        return existingAsistencia;
    }

    @Override
    public void deleteAsistencia(Long id) {
        asistenciaRepository.delete(id);
    }

    @Override
    public long countAsistencias() {
        return asistenciaRepository.countAsistencias();
    }
}
