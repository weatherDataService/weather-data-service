package weather;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    
    @Override
    public Response toResponse(Throwable ex) {
        if(ex instanceof org.jboss.resteasy.spi.Failure){
            return ((org.jboss.resteasy.spi.Failure) ex).getResponse();
        } else if(ex instanceof javax.ws.rs.WebApplicationException){
            return ((javax.ws.rs.WebApplicationException) ex).getResponse();
        }
        Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, "", ex);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}