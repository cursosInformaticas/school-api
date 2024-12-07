package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.EventoEscolar;
import edu.school.app.domain.service.EventoEscolarService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/evento-escolares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class EventoEscolarResource {

    @Inject
    EventoEscolarService eventoEscolarService;

    @POST
    public Response createEventoEscolar(@Valid EventoEscolar eventoEscolar) {
        EventoEscolar created = eventoEscolarService.createEventoEscolar(eventoEscolar);
        ApiResponse<EventoEscolar> response = new ApiResponse<>("success", "Evento escolar creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllEventoEscolars(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<EventoEscolar> eventoEscolarList= eventoEscolarService.getAllEventoEscolars(page, size);
        long totalRecords = eventoEscolarService.countEscolares();
        ApiResponse<List<EventoEscolar>> response = new ApiResponse<>("success", "Lista de eventos escolares obtenida con éxito", eventoEscolarList,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getEventoEscolarById(@PathParam("id") Long id) {
        EventoEscolar eventoEscolar= eventoEscolarService.getEventoEscolarById(id);
        ApiResponse<EventoEscolar> response = new ApiResponse<>("success", "EventoEscolar obtenido con éxito", eventoEscolar);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEventoEscolar(@PathParam("id") Long id, EventoEscolar eventoEscolar) {
        EventoEscolar updateAlumno = eventoEscolarService.updateEventoEscolar(id, eventoEscolar);
        ApiResponse<EventoEscolar> response = new ApiResponse<>("success", "EventoEscolar actualizado con éxito", updateAlumno);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEventoEscolar(@PathParam("id") Long id) {
        eventoEscolarService.deleteEventoEscolar(id);
        ApiResponse<String> response = new ApiResponse<>("success", "EventoEscolar eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

