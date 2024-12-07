package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.AnioEscolar;
import edu.school.app.domain.service.AnioEscolarService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/anios-escolares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AnioEscolarResource {

    @Inject
    AnioEscolarService anioEscolarService;

    @POST
    public Response createAnioEscolar(@Valid AnioEscolar anioEscolar) {
        AnioEscolar created = anioEscolarService.createAnioEscolar(anioEscolar);
        ApiResponse<AnioEscolar> response = new ApiResponse<>("success", "Alumno creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAnioEscolar(@PathParam("id") Long id, @Valid AnioEscolar anioEscolar) {
        AnioEscolar updated = anioEscolarService.updateAnioEscolar(id, anioEscolar);
        ApiResponse<AnioEscolar> response = new ApiResponse<>("success", "AnioEscolar actualizado con éxito", updated);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getAnioEscolarById(@PathParam("id") Long id) {
        AnioEscolar anioEscolar = anioEscolarService.getAnioEscolarById(id);
        ApiResponse<AnioEscolar> response = new ApiResponse<>(
                "success",
                "Año escolar obtenido con éxito",
                anioEscolar
        );
        return Response.ok(response).build();
    }

    @GET
    public Response listAllAniosEscolares(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        List<AnioEscolar> aniosEscolares = anioEscolarService.listAllAniosEscolares();
        long totalRecords = aniosEscolares.size();
        ApiResponse<List<AnioEscolar>> response = new ApiResponse<>(
                "success",
                "Lista de años escolares obtenida con éxito",
                aniosEscolares,
                totalRecords, page, size
        );
        return Response.ok(response).build();
    }
}
