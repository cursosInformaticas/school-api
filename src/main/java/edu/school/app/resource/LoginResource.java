package edu.school.app.resource;

import edu.school.app.application.utils.KeycloakRestClient;
import edu.school.app.domain.model.LoginRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    @RestClient
    KeycloakRestClient keycloakRestClient;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        try {
            Form form = new Form()
                    .param("grant_type", "password")
                    .param("client_id", clientId)
                    .param("username", request.getUsername())
                    .param("password", request.getPassword())
                    .param("client_secret", clientSecret);
            String response = keycloakRestClient.login(form);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciales inválidas o error en el servidor: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/refresh")
    public Response refreshToken(@QueryParam("refresh_token") String refreshToken) {
        try {
            if (refreshToken == null || refreshToken.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El token de actualización (refresh_token) es requerido.")
                        .build();
            }

            Form form = new Form()
                    .param("grant_type", "refresh_token")
                    .param("client_id", clientId)
                    .param("client_secret", clientSecret)
                    .param("refresh_token", refreshToken);

            String response = keycloakRestClient.login(form);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("No se pudo renovar el token: " + e.getMessage())
                    .build();
        }
    }
}
