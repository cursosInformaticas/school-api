package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Asistencia;
import edu.school.app.domain.service.AsistenciaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/asistencias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AsistenciaResource {

    @Inject
    AsistenciaService asistenciaService;

    @POST
    public Response createAsistencia(@Valid Asistencia asistencia) {
        try {
            Asistencia created = asistenciaService.createAsistencia(asistencia);
            ApiResponse<Asistencia> response = new ApiResponse<>("success", "Asistencia creada con éxito", created);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("error", e.getMessage(), null);
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }

    @GET
    public Response getAllAsistencias(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<Asistencia> asistencias= asistenciaService.getAllAsistencias(page, size);
        long totalRecords = asistenciaService.countAsistencias();
        ApiResponse<List<Asistencia>> response = new ApiResponse<>("success", "Lista de asistencia obtenida con éxito", asistencias,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getAsistenciaById(@PathParam("id") Long id) {
        Asistencia asistencia = asistenciaService.getAsistenciaById(id);
        ApiResponse<Asistencia> response = new ApiResponse<>("success", "Asistencia obtenido con éxito", asistencia);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAsistencia(@PathParam("id") Long id, Asistencia asistencia) {
        try {
            Asistencia updatedAsistencia = asistenciaService.updateAsistencia(id, asistencia);
            ApiResponse<Asistencia> response = new ApiResponse<>("success", "Asistencia actualizada con éxito", updatedAsistencia);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("error", e.getMessage(), null);
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAsistencia(@PathParam("id") Long id) {
        asistenciaService.deleteAsistencia(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Asistencia eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

