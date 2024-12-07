package edu.school.app.config;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EmailExceptionMapper implements ExceptionMapper<EmailAlreadyExistsException> {
    @Override
    public Response toResponse(EmailAlreadyExistsException exception) {
        ApiResponse<String> response = new ApiResponse<>("error", exception.getMessage(), null);
        return Response.status(Response.Status.CONFLICT).entity(response).build();
    }
}
