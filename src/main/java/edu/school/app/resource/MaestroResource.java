package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Maestro;
import edu.school.app.domain.service.MaestroService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/maestros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class MaestroResource {

    @Inject
    MaestroService maestroService;

    @POST
    public Response createMaestro(@Valid Maestro maestro) {
        Maestro created = maestroService.createMaestro(maestro);
        ApiResponse<Maestro> response = new ApiResponse<>("success", "Maestro creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllMaestros(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<Maestro> maestroList= maestroService.getAllMaestros(page, size);
        long totalRecords = maestroService.countMaestros();
        ApiResponse<List<Maestro>> response = new ApiResponse<>("success", "Lista de maestros obtenida con éxito", maestroList,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getMaestroById(@PathParam("id") Long id) {
        Maestro maestro= maestroService.getMaestroById(id);
        ApiResponse<Maestro> response = new ApiResponse<>("success", "Maestro obtenido con éxito", maestro);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMaestro(@PathParam("id") Long id, Maestro maestro) {
        Maestro maestroUpdate= maestroService.updateMaestro(id, maestro);
        ApiResponse<Maestro> response = new ApiResponse<>("success", "Maestro actualizado con éxito", maestroUpdate);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMaestro(@PathParam("id") Long id) {
        maestroService.deleteMaestro(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Maestro eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

