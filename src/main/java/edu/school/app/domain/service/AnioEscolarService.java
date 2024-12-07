package edu.school.app.domain.service;

import edu.school.app.domain.model.AnioEscolar;

import java.util.List;

public interface AnioEscolarService {

    AnioEscolar createAnioEscolar(AnioEscolar anioEscolar);
    AnioEscolar updateAnioEscolar(Long id, AnioEscolar anioEscolar);
    AnioEscolar getAnioEscolarById(Long id);
    List<AnioEscolar> listAllAniosEscolares();
}
