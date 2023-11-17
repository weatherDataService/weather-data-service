package weather.api.weatherdata;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import weather.api.model.enums.Status;
import weather.api.model.request.FindWeatherDataRequest;
import weather.api.model.response.FindWeatherDataResponse;
import weather.service.WeatherDataService;

/** Description of the API:
A client can call this API endpoint to get all weather metrics data of one, 
more or all sensors in a specific period of time. The client’s request 
payload may include an array of sensor IDs, start date and end date. 
All three attributes in the payload are optional. This API has three functions: 
1) If the request payload includes one or more sensor IDs, the API’s response 
will include all the weather metrics data of all the selected sensor IDs. 
2) If the payload doesn’t include any sensor ID, the API’s response will include 
all the weather metrics data of all sensor IDs. 3) If the start date and end date 
are not provided in the payload, all the weather metrics data for one, 
more or all sensors in the last one day will be provided in the response. 
All the weather metrics data in the response will be ordered by sensor ID, 
metrics name, and created/measured date.
*/

@Path("/weatherdata/find")
@RequestScoped
public class Find {

    @Inject
    private WeatherDataService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findWeatherData(@Valid FindWeatherDataRequest request) {

        FindWeatherDataResponse response = service.findWeatherData(request);
        if (Status.SUCCESS.equals(response.getStatus())) {
            return Response.ok(response).build();
        } else {
            return Response.accepted(response).build();
        }
    }

}
