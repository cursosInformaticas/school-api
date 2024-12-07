package edu.school.app.application.utils;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.util.Base64;
import java.util.logging.Logger;

@Provider
public class JwtRequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(JwtRequestFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring(7); // Extraer el token JWT
                String[] parts = token.split("\\.");
                if (parts.length == 3) {
                    String payload = new String(Base64.getUrlDecoder().decode(parts[1])); // Decodificar el payload
                    JsonObject jwt = Json.createReader(new java.io.StringReader(payload)).readObject();
                    requestContext.setProperty("decodedJwt", jwt); // Almacenar el JWT decodificado
                }
            } catch (Exception e) {
                LOG.warning("Error al decodificar el token JWT: " + e.getMessage());
            }
        }
    }
}