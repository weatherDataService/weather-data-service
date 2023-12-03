package weather.api.weatherdata;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import weather.api.model.enums.Status;
import weather.api.model.request.AddWeatherDataRequest;
import weather.api.model.response.AddWeatherDataResponse;
import weather.service.WeatherDataService;

/**
 * Description of the API: A sensor can call this API endpoint with a request
 * payload containing all kinds of weather metrics values measured by this
 * sensor at a specific time, and the API will store these data in the
 * WEATHER_DATA database table for this sensor. The sensor’s request payload
 * includes sensor ID, a collection of metricsName-metricsValue pairs and the
 * time of measurement. All three attributes in the payload are required.
 */
@Path("/weatherdata/add")
@RequestScoped
public class Add {

    @Inject
    private WeatherDataService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addWeatherData(@Valid AddWeatherDataRequest request) {

        AddWeatherDataResponse response = service.addWeatherData(request);
        if (Status.SUCCESS.equals(response.getStatus())) {
            return Response.ok(response).build();
        } else {
            return Response.accepted(response).build();
        }
    }

    @OPTIONS
    protected Response getOptions() {
        return Response.ok().build();
    }

}
