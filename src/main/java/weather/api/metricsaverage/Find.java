package weather.api.metricsaverage;

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
import weather.api.model.request.FindMetricsAverageRequest;
import weather.api.model.response.FindMetricsAverageResponse;
import weather.service.WeatherDataService;

/** Description of the API:
A client can call this API endpoint to get the average values of one, more or all kinds of weather metrics for one, 
more or all sensors grouped by sensor ID and metrics name in a specific period of time. The client’s request payload 
may include an array of sensor IDs, an array of metrics names, start date and end date. All the four attributes 
in the request payload are optional. This API has four functions: 1) If the request payload includes one or more sensor IDs 
and one or more metrics names, the API’s response will include the average values of all the selected weather metrics 
for all the selected sensors. 2) If the request payload doesn’t include any sensor ID but includes one or more metrics names, 
the API’s response will include the average values of all the selected weather metrics for all sensors. 3) If the request 
payload doesn’t include both sensor ID and metrics name, the API’s response will include the average values of all the 
weather metrics for all sensors. 4) If either start date or end date (or both) are not provided in the request payload, 
the latest values of all the selected (or all) weather metrics for all the selected (or all) sensors will be provided 
in the response depending on whether an array of sensor IDs or an array of metrics names or both are provided in the request 
payload. If both start date and end date are provided in the request payload, the start date must be before the end date and 
all weather metrics average data in the response will be grouped by sensor ID and metrics name.
*/

@Path("/metricsaverage/find")
@RequestScoped
public class Find {

    @Inject
    private WeatherDataService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findMetricsAverage(@Valid FindMetricsAverageRequest request) {

        FindMetricsAverageResponse response = service.findMetricsAverage(request);
        if (Status.SUCCESS.equals(response.getStatus())) {
            return Response.ok(response).build();
        } else {
            return Response.accepted(response).build();
        }
    }

}
