package edu.school.app.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school.app.config.ApiResponse;
import edu.school.app.domain.service.DatosIAService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

@Path("/api/v1/ia-data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class DatosIAResource {

    @Inject
    DatosIAService datosIAService;

    @GET
    public Response getAllDataForAI() {
        List<Map<String, Object>> data = datosIAService.getAllDataForAI();
        return Response.ok(new ApiResponse<>("success", "Datos para IA obtenidos con éxito", data)).build();
    }
    @GET
    @Path("/json")
    public Response exportDataAsJSON() {
        try {
            // Obtener los datos procesados
            List<Map<String, Object>> data = datosIAService.getAllDataForAI();

            // Crear el ObjectMapper y registrar el módulo para manejar tipos Java 8
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            // Convertir los datos a formato JSON
            String jsonData = objectMapper.writeValueAsString(data);

            // Crear respuesta
            return Response.ok(jsonData).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of(
                            "status", "error",
                            "message", "Error al exportar datos para IA",
                            "error", e.getMessage()
                    )).build();
        }
    }

    @GET
    @Path("/csv")
    @Produces("text/csv")
    public Response exportDataAsCSV() {
        try {
            // Obtener los datos procesados
            List<Map<String, Object>> data = datosIAService.getAllDataForAI();

            // Generar el contenido CSV
            String csvContent = datosIAService.exportDataToCSV(data);

            // Crear la respuesta con el contenido CSV
            return Response.ok(csvContent)
                    .header("Content-Disposition", "attachment; filename=\"datos_exportados.csv\"")
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of(
                            "status", "error",
                            "message", "Error al exportar datos para IA en formato CSV",
                            "error", e.getMessage()
                    )).build();
        }
    }

}
