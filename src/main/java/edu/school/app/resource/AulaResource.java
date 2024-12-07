package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Aula;
import edu.school.app.domain.service.AulaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/aulas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AulaResource {

    @Inject
    AulaService aulaService;

    @POST
    public Response createAula(@Valid Aula aula) {
        Aula created = aulaService.createAula(aula);
        ApiResponse<Aula> response = new ApiResponse<>("success", "Aula creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllAulas(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<Aula> aulas= aulaService.getAllAulas(page, size);
        long totalRecords = aulaService.countAulas();
        ApiResponse<List<Aula>> response = new ApiResponse<>("success", "Lista de aulas obtenida con éxito", aulas,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getAulaById(@PathParam("id") Long id) {
        Aula aula= aulaService.getAulaById(id);
        ApiResponse<Aula> response = new ApiResponse<>("success", "Aula obtenido con éxito", aula);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAula(@PathParam("id") Long id, Aula aula) {
        Aula updateAula= aulaService.updateAula(id, aula);
        ApiResponse<Aula> response = new ApiResponse<>("success", "Aula actualizado con éxito", updateAula);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAula(@PathParam("id") Long id) {
        aulaService.deleteAula(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Aula eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

