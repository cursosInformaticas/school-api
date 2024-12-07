package edu.school.app.application.utils;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.Principal;
import java.util.*;

@ApplicationScoped
public class JwtAuthenticationConverter implements SecurityIdentityAugmentor {

    @ConfigProperty(name = "jwt.auth.converter.principal-attribute", defaultValue = "preferred_username")
    String principalAttribute;

    @ConfigProperty(name = "jwt.auth.converter.resource-id")
    String resourceId;

    @Override
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        // Intentar obtener el token JWT del atributo "accessToken"
        String token = identity.getAttribute("realms");
        if (token == null) {
            return Uni.createFrom().item(identity); // Si no hay token, retorna la identidad original
        }

        // Decodificar el JWT
        JsonObject jwt = decodeJwt(token);
        if (jwt == null) {
            return Uni.createFrom().item(identity); // Si el token es inválido, retorna la identidad original
        }

        // Extraer roles y atributos personalizados
        Set<String> roles = extractRoles(jwt);
        Principal principal = () -> jwt.getString(principalAttribute); // Utilizar principal-attribute

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", jwt.getString("email"));
        attributes.put("name", jwt.getString("name"));

        // Crear una identidad personalizada
        CustomSecurityIdentity customIdentity = new CustomSecurityIdentity(principal, roles, attributes);

        // Combinar la identidad original con la personalizada
        SecurityIdentity mergedIdentity = mergeIdentity(identity, customIdentity);

        return Uni.createFrom().item(mergedIdentity);
    }

    private JsonObject decodeJwt(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("JWT no válido");
            }
            String payload = new String(Base64.getUrlDecoder().decode(parts[1])); // Decodificar el payload
            return Json.createReader(new java.io.StringReader(payload)).readObject();
        } catch (Exception e) {
            return null; // Retorna null si hay un error en la decodificación
        }
    }

    private Set<String> extractRoles(JsonObject jwt) {
        Set<String> roles = new HashSet<>();

        // Extraer roles de realm
        JsonObject realmAccess = jwt.getJsonObject("realm_access");
        if (realmAccess != null) {
            realmAccess.getJsonArray("roles").forEach(role -> roles.add(role.toString()));
        }

        // Extraer roles de recurso
        JsonObject resourceAccess = jwt.getJsonObject("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey(resourceId)) {
            JsonObject resource = resourceAccess.getJsonObject(resourceId);
            resource.getJsonArray("roles").forEach(role -> roles.add(role.toString()));
        }

        return roles;
    }

    private SecurityIdentity mergeIdentity(SecurityIdentity original, CustomSecurityIdentity additional) {
        Principal principal = additional.getPrincipal() != null ? additional.getPrincipal() : original.getPrincipal();
        Set<String> mergedRoles = new HashSet<>(original.getRoles());
        mergedRoles.addAll(additional.getRoles());

        Map<String, Object> mergedAttributes = new HashMap<>(original.getAttributes());
        mergedAttributes.putAll(additional.getAttributes());

        return new CustomSecurityIdentity(principal, mergedRoles, mergedAttributes);
    }
}
