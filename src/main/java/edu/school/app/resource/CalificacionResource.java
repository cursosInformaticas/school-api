package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Calificacion;
import edu.school.app.domain.service.CalificacionService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/calificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class CalificacionResource {

    @Inject
    CalificacionService calificacionService;

    @POST
    public Response createCalificacion(@Valid Calificacion calificacion) {
        Calificacion created = calificacionService.createCalificacion(calificacion);
        ApiResponse<Calificacion> response = new ApiResponse<>("success", "Calificacion creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllCalificacions(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<Calificacion> calificacions= calificacionService.getAllCalificacions(page, size);
        long totalRecords = calificacionService.countCalificacions();
        ApiResponse<List<Calificacion>> response = new ApiResponse<>("success", "Lista de calificacions obtenida con éxito", calificacions,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getCalificacionById(@PathParam("id") Long id) {
        Calificacion calificacion= calificacionService.getCalificacionById(id);
        ApiResponse<Calificacion> response = new ApiResponse<>("success", "Calificacion obtenido con éxito", calificacion);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCalificacion(@PathParam("id") Long id, Calificacion calificacion) {
        Calificacion updateCalificacion= calificacionService.updateCalificacion(id, calificacion);
        ApiResponse<Calificacion> response = new ApiResponse<>("success", "Calificacion actualizado con éxito", updateCalificacion);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCalificacion(@PathParam("id") Long id) {
        calificacionService.deleteCalificacion(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Calificacion eliminado con éxito", null);
        return Response.ok(response).build();

    }
}

