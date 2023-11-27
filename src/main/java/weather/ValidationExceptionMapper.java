package weather;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.api.validation.ResteasyViolationException;
import weather.api.model.ValidationExceptionEntry;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<javax.validation.ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {

        if (e instanceof ResteasyViolationException) {

            ResteasyViolationException ex = (ResteasyViolationException) e;
            List<ValidationExceptionEntry> errors = new ArrayList<>();

            for (ConstraintViolation c : ex.getConstraintViolations()) {
                ValidationExceptionEntry vee = new ValidationExceptionEntry();
                vee.setMessage(c.getMessage());
                vee.setMessageTemplate(c.getMessage());
                vee.setPath(c.getPropertyPath().toString());
                vee.setInvalidValue("");
                errors.add(vee);
            }

            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        return Response.serverError().build();
    }
}
