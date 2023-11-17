package weather.api.metricsstatistic;

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
import weather.api.model.request.FindMetricsStatisticRequest;
import weather.api.model.response.FindMetricsStatisticResponse;
import weather.service.WeatherDataService;

/** Description of the API:
A client can call this API endpoint to get the statistic values 
(min, max, sum and average) of a single weather metrics for one, 
more or all sensors grouped by sensor ID and metrics name in a specific 
period of time. The client’s request payload may include an array of 
sensor IDs, a metrics name, start date and end date. Only metrics name 
attribute in the payload is required. This API has three functions: 
1) If the request payload includes one or more sensor IDs, the API’s 
response will include the statistic values (min, max, sum and average) 
of the selected single weather metrics for all the selected sensor IDs. 
2) If the payload does not include any sensor ID, the API’s response will 
include the statistic values (min, max, sum and average) of the selected 
single weather metrics for all sensor IDs. 3) If the start date and end 
date are not provided in the payload, the statistic values (min, max, 
sum and average) of the selected single weather metrics for one, more or 
all sensors in the last one day will be provided in the response.
*/ 

@Path("/metricsstatistic/find")
@RequestScoped
public class Find {

    @Inject
    private WeatherDataService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findMetricsStatistic(@Valid FindMetricsStatisticRequest request) {

        FindMetricsStatisticResponse response = service.findMetricsStatistic(request);
        if (Status.SUCCESS.equals(response.getStatus())) {
            return Response.ok(response).build();
        } else {
            return Response.accepted(response).build();
        }
    }

}
