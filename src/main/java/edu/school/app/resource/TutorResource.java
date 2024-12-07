package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Tutor;
import edu.school.app.domain.service.TutorService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/tutores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class TutorResource {

    @Inject
    TutorService tutorService;

    @POST
    public Response createTutor(@Valid Tutor tutor) {
        Tutor created = tutorService.createTutor(tutor);
        ApiResponse<Tutor> response = new ApiResponse<>("success", "Tutor creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public  Response getAllTutors(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }

        List<Tutor> tutorList= tutorService.getAllTutors(page, size);
        long totalRecords = tutorService.countTutores();
        ApiResponse<List<Tutor>> response = new ApiResponse<>("success", "Lista de tutor obtenida con éxito", tutorList,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getTutorById(@PathParam("id") Long id) {
        Tutor tutor= tutorService.getTutorById(id);
        ApiResponse<Tutor> response = new ApiResponse<>("success", "Tutor obtenido con éxito", tutor);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTutor(@PathParam("id") Long id, Tutor tutor) {
        Tutor tutorUpdate= tutorService.updateTutor(id, tutor);
        ApiResponse<Tutor> response = new ApiResponse<>("success", "Tutor actualizado con éxito", tutorUpdate);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTutor(@PathParam("id") Long id) {
        tutorService.deleteTutor(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Tutor eliminado con éxito", null);
        return Response.ok(response).build();
    }
}

