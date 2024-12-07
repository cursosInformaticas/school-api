package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Examen;
import edu.school.app.domain.service.ExamenService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/examenes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class ExamenResource {

    @Inject
    ExamenService examenService;

    @POST
    public Response createExamen(@Valid Examen examen) {
        Examen created = examenService.createExamen(examen);
        ApiResponse<Examen> response = new ApiResponse<>("success", "Examen creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllExamens(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
         List<Examen> examenList= examenService.getAllExamens(page, size);
        long totalRecords = examenService.countExamenes();
        ApiResponse<List<Examen>> response = new ApiResponse<>("success", "Lista de examenes obtenida con éxito", examenList,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getExamenById(@PathParam("id") Long id) {
        Examen examen= examenService.getExamenById(id);
        ApiResponse<Examen> response = new ApiResponse<>("success", "Examen obtenido con éxito", examen);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateExamen(@PathParam("id") Long id, Examen examen) {
        Examen updateExamen = examenService.updateExamen(id, examen);
        ApiResponse<Examen> response = new ApiResponse<>("success", "Examen actualizado con éxito", updateExamen);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteExamen(@PathParam("id") Long id) {
        examenService.deleteExamen(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Examen eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

