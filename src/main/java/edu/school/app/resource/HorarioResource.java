package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Horario;
import edu.school.app.domain.service.HorarioService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/horarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class HorarioResource {

    @Inject
    HorarioService horarioService;

    @POST
    public Response createHorario(@Valid Horario horario) {
        Horario created = horarioService.createHorario(horario);
        ApiResponse<Horario> response = new ApiResponse<>("success", "Horario creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllHorarios(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<Horario> horarios= horarioService.getAllHorarios(page, size);
        long totalRecords = horarioService.countHorarios();
        ApiResponse<List<Horario>> response = new ApiResponse<>("success", "Lista de horarios obtenida con éxito", horarios,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getHorarioById(@PathParam("id") Long id) {
        Horario horario= horarioService.getHorarioById(id);
        ApiResponse<Horario> response = new ApiResponse<>("success", "Horario obtenido con éxito", horario);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateHorario(@PathParam("id") Long id, Horario horario) {
        Horario updateHorario = horarioService.updateHorario(id, horario);
        ApiResponse<Horario> response = new ApiResponse<>("success", "Horario actualizado con éxito", updateHorario);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHorario(@PathParam("id") Long id) {
        horarioService.deleteHorario(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Horario eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

