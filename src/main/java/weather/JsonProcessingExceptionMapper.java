package weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    @Override
    public Response toResponse(JsonProcessingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
    }
}