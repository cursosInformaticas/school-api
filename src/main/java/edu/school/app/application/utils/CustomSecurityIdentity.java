package edu.school.app.application.utils;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import java.security.Permission;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


public class CustomSecurityIdentity implements SecurityIdentity {

    private final Principal principal;
    private final Set<String> roles;
    private final Map<String, Object> attributes;

    public CustomSecurityIdentity(Principal principal, Set<String> roles, Map<String, Object> attributes) {
        this.principal = principal;
        this.roles = roles;
        this.attributes = attributes;
    }
    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public <T> T getAttribute(String name) {
        return (T) attributes.get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Uni<Boolean> checkPermission(Permission permission) {
        return Uni.createFrom().item(false);
    }


    @Override
    public boolean isAnonymous() {
        return false; // Indica que esta identidad no es anónima
    }

    @Override
    public boolean hasRole(String role) {
        return roles.contains(role); // Verifica si la identidad tiene un rol específico
    }

    @Override
    public <T extends Credential> T getCredential(Class<T> aClass) {
        return null;
    }

    @Override
    public Set<Credential> getCredentials() {
        return Collections.emptySet();
    }


}
