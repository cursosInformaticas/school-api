package edu.school.app.config;


import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<Map<String, String>> errors = exception.getConstraintViolations().stream()
                .map(violation -> {
                    Map<String, String> errorDetails = new HashMap<>();
                    errorDetails.put("field", violation.getPropertyPath().toString());
                    errorDetails.put("invalidValue", violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : "null");
                    errorDetails.put("message", violation.getMessage());
                    return errorDetails;
                })
                .collect(Collectors.toList());

        // Detalles adicionales en la respuesta
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("errorType", "Validation Error");
        responseBody.put("errorMessage", "One or more validation errors occurred");
        responseBody.put("violations", errors);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(responseBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
