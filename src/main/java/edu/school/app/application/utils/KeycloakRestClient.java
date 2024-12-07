package edu.school.app.application.utils;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9090/realms/curso-api/protocol/openid-connect")
@Path("/token")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface KeycloakRestClient {

    @POST
    String login(Form form);
}
