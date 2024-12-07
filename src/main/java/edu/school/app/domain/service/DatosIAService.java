package edu.school.app.domain.service;

import java.util.List;
import java.util.Map;

public interface DatosIAService {
    List<Map<String, Object>> getAllDataForAI();
    String exportDataToJSON(List<Map<String, Object>> data);
    String exportDataToCSV(List<Map<String, Object>> data);

}
