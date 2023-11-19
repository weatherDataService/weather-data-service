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
A client can call this API endpoint to get all kinds of weather metrics data from one, more or all sensors 
in a specific period of time. The client’s request payload may include an array of sensor IDs, start date 
and end date. All three attributes in the request payload are optional. This API has three functions: 
1) If the request payload includes one or more sensor IDs, the API’s response will include all kinds of 
weather metrics data from all the selected sensors. 2) If the request payload doesn’t include any sensor ID, 
the API’s response will include all kinds of weather metrics data from all sensors. 3) If either start date or 
end date (or both) are not provided in the request payload, the latest weather metrics data of all kinds from one, 
more or all sensors will be provided in the response. If both start date and end date are provided in the request 
payload, the start date must be before the end date and all kinds of weather metrics data in the response will be 
ordered by sensor ID, metrics name, and measured date/time.
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
