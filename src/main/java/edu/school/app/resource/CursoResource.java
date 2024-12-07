package edu.school.app.resource;


import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Curso;
import edu.school.app.domain.service.CursoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class CursoResource {

    @Inject
    CursoService cursoService;

    @POST
    public Response createCurso(@Valid Curso curso) {
        Curso created = cursoService.createCurso(curso);
        ApiResponse<Curso> response = new ApiResponse<>("success", "Curso creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getAllCursos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size
    ) {
        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>("error", "Los parámetros de paginación no son válidos", null, 0, page, size);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        List<Curso> cursos = cursoService.getAllCursos(page, size);
        long totalRecords = cursoService.countCursos();
        ApiResponse<List<Curso>> response = new ApiResponse<>("success", "Lista de cursos obtenida con éxito", cursos,totalRecords, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getCursoById(@PathParam("id") Long id) {
        Curso curso= cursoService.getCursoById(id);
        ApiResponse<Curso> response = new ApiResponse<>("success", "Curso obtenido con éxito", curso);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCurso(@PathParam("id") Long id, Curso curso) {
        Curso updateCurso= cursoService.updateCurso(id, curso);
        ApiResponse<Curso> response = new ApiResponse<>("success", "Curso actualizado con éxito", updateCurso);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCurso(@PathParam("id") Long id) {
        cursoService.deleteCurso(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Curso eliminado con éxito", null);
        return Response.ok(response).build();
    }
}
