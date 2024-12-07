package edu.school.app.resource;

import edu.school.app.config.ApiResponse;
import edu.school.app.domain.model.Alumno;
import edu.school.app.domain.model.AlumnoDetails;
import edu.school.app.domain.service.AlumnoService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/api/v1/alumnos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AlumnoResource {


    @Inject
    AlumnoService alumnoService;

    @Inject
    SecurityIdentity identity;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @RolesAllowed("ADMIN")
    @Path("/roles")
    public Response getUserRoles() {
        return Response.ok(identity.getRoles()).build();
    }

    @GET
    @RolesAllowed({"ADMIN","USER"})
    public Response getAllAlumnos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("5") int size,
            @QueryParam("anioEscolar") Integer anioEscolar
    ) {

        String username = securityIdentity.getPrincipal().getName();
        Set<String> roles = securityIdentity.getRoles();
        System.out.println("Usuario: " + username + ", Roles: " + roles);
     //   return Response.ok().build();

        if (page < 0 || size <= 0) {
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    "error", "Los parámetros de paginación no son válidos", null, 0, page, size
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }

        List<Alumno> alumnos;
        long totalRecords;

        if (anioEscolar != null) {
            alumnos = alumnoService.getAlumnosByAnioEscolarPaged(anioEscolar, page, size);
            totalRecords = alumnoService.countAlumnosByAnioEscolar(anioEscolar);
        } else {
            alumnos = alumnoService.getAllAlumnos(page, size);
            totalRecords = alumnoService.countAlumnos();
        }

        ApiResponse<List<Alumno>> response = new ApiResponse<>(
                "success", "Lista de alumnos obtenida con éxito", alumnos, totalRecords, page, size
        );
        return Response.ok(response).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response createAlumno(@Valid Alumno alumno) {
        Alumno created = alumnoService.createAlumno(alumno);

        ApiResponse<Alumno> response = new ApiResponse<>("success", "Alumno creado con éxito", created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }


    @GET
    @Path("/{id}")
    public Response getAlumnoById(@PathParam("id") Long id) {
        Alumno alumno =  alumnoService.getAlumnoById(id);
        ApiResponse<Alumno> response = new ApiResponse<>("success", "Alumno obtenido con éxito", alumno);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response  updateAlumno(@PathParam("id") Long id,@Valid Alumno alumno) {
        Alumno updatedAlumno = alumnoService.updateAlumno(id, alumno);
        ApiResponse<Alumno> response = new ApiResponse<>("success", "Alumno actualizado con éxito", updatedAlumno);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAlumno(@PathParam("id") Long id) {
        alumnoService.deleteAlumno(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Alumno eliminado con éxito", null);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}/has-dependencies")
    public Response checkDependencies(@PathParam("id") Long id) {
        boolean hasDependencies = alumnoService.hasDependencies(id);
        ApiResponse<Boolean> response = new ApiResponse<>("success", "Verificación de dependencias realizada con éxito", hasDependencies);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}/details")
    public Response getAlumnoDetails(@PathParam("id") Long id) {
        AlumnoDetails alumnoDetails = alumnoService.getAlumnoDetails(id);
        ApiResponse<AlumnoDetails> response = new ApiResponse<>(
                "success",
                "Detalles del alumno obtenidos con éxito",
                alumnoDetails
        );
        return Response.ok(response).build();
    }

    @GET
    @Path("/anio-escolar/{anioEscolar}")
    public Response getAlumnosByAnioEscolar(@PathParam("anioEscolar") int anioEscolar) {
        List<Alumno> alumnos = alumnoService.getAlumnosByAnioEscolar(anioEscolar);
        ApiResponse<List<Alumno>> response = new ApiResponse<>(
                "success",
                "Lista de alumnos por año escolar obtenida con éxito",
                alumnos
        );
        return Response.ok(response).build();
    }

}

