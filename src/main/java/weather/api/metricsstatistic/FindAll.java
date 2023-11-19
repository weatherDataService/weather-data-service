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
import weather.api.model.request.FindAllMetricsStatisticRequest;
import weather.api.model.response.FindAllMetricsStatisticResponse;
import weather.service.WeatherDataService;

/** Description of the API:
A client can call this API endpoint to get all the statistic values (min, max, sum and average) of all weather metrics 
for all sensors grouped by sensor ID and metrics name in a specific period of time. The client’s request payload may 
include start date and end date. Both attributes in the payload are required and the start date must be before the end date.
*/ 

@Path("/metricsstatistic/findall")
@RequestScoped
public class FindAll {

    @Inject
    private WeatherDataService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findAllMetricsStatistic(@Valid FindAllMetricsStatisticRequest request) {

        FindAllMetricsStatisticResponse response = service.findAllMetricsStatistic(request);
        if (Status.SUCCESS.equals(response.getStatus())) {
            return Response.ok(response).build();
        } else {
            return Response.accepted(response).build();
        }
    }

}
