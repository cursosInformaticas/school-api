package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.GradoEscolar;
import edu.school.app.domain.service.GradoEscolarService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/grado-escolares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class GradoEscolarResource {

    @Inject
    GradoEscolarService gradoEscolarService;

    @POST
    public Response createGradoEscolar(@Valid GradoEscolar gradoEscolar) {
        GradoEscolar created = gradoEscolarService.createGradoEscolar(gradoEscolar);
        ApiResponse<GradoEscolar> response = new ApiResponse<>("success", "GradoEscolar creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllGradoEscolars(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<GradoEscolar> gradoEscolarList = gradoEscolarService.getAllGradoEscolars(page, size);
        long totalRecords = gradoEscolarService.countEscolares();
        ApiResponse<List<GradoEscolar>> response = new ApiResponse<>("success", "Lista de grado escolar obtenida con éxito", gradoEscolarList,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getGradoEscolarById(@PathParam("id") Long id) {
        GradoEscolar gradoEscolar = gradoEscolarService.getGradoEscolarById(id);
        ApiResponse<GradoEscolar> response = new ApiResponse<>("success", "GradoEscolar obtenido con éxito", gradoEscolar);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateGradoEscolar(@PathParam("id") Long id, GradoEscolar gradoEscolar) {
        GradoEscolar updateGradoEscolar = gradoEscolarService.updateGradoEscolar(id, gradoEscolar);
        ApiResponse<GradoEscolar> response = new ApiResponse<>("success", "GradoEscolar actualizado con éxito", updateGradoEscolar);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGradoEscolar(@PathParam("id") Long id) {
        gradoEscolarService.deleteGradoEscolar(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Grado escolar eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

