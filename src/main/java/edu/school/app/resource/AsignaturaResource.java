package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Asignatura;
import edu.school.app.domain.service.AsignaturaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/asignaturas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AsignaturaResource {

    @Inject
    AsignaturaService asignaturaService;

    @POST
    public Response createAsignatura(@Valid Asignatura asignatura) {
        Asignatura created = asignaturaService.createAsignatura(asignatura);
        ApiResponse<Asignatura> response = new ApiResponse<>("success", "Asignatura creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllAsignaturas(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<Asignatura> asignaturas = asignaturaService.getAllAsignaturas(page, size);
        long totalRecords = asignaturaService.countAsignaturas();
        ApiResponse<List<Asignatura>> response = new ApiResponse<>("success", "Lista de asignaturas obtenida con éxito", asignaturas,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getAsignaturaById(@PathParam("id") Long id) {
        Asignatura asignatura = asignaturaService.getAsignaturaById(id);
        ApiResponse<Asignatura> response = new ApiResponse<>("success", "Asignatura obtenido con éxito", asignatura);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response  updateAsignatura(@PathParam("id") Long id,@Valid Asignatura asignatura) {
        Asignatura updateAsignatura = asignaturaService.updateAsignatura(id, asignatura);
        ApiResponse<Asignatura> response = new ApiResponse<>("success", "Asignatura actualizado con éxito", updateAsignatura);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAsignatura(@PathParam("id") Long id) {
        asignaturaService.deleteAsignatura(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Asignatura eliminado con éxito", null);
        return Response.ok(response).build();
    }

}

