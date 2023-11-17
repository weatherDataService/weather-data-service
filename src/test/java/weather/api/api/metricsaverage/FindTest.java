package weather.api.api.metricsaverage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import weather.api.metricsaverage.Find;
import weather.api.model.enums.Status;
import weather.api.model.request.FindMetricsAverageRequest;
import weather.api.model.response.FindMetricsAverageResponse;
import weather.service.WeatherDataService;

@RunWith(MockitoJUnitRunner.class)
public class FindTest {

    @Mock
    private WeatherDataService weatherDataService;

    @InjectMocks
    private Find find;

    @Before
    public void setUp() {
    }

    @Test
    public void testFindMetricAverageSuccess() {
        FindMetricsAverageRequest request = createSampleRequest();
        FindMetricsAverageResponse response = createSampleSuccessResponse();

        when(weatherDataService.findMetricsAverage(request)).thenReturn(response);

        Response result = find.findMetricsAverage(request);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    @Test
    public void testFindMetricAverageFailure() {
        FindMetricsAverageRequest request = createSampleRequest();
        FindMetricsAverageResponse response = createSampleFailureResponse();

        when(weatherDataService.findMetricsAverage(request)).thenReturn(response);

        Response result = find.findMetricsAverage(request);

        assertEquals(Response.Status.ACCEPTED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    private FindMetricsAverageRequest createSampleRequest() {
        FindMetricsAverageRequest request = new FindMetricsAverageRequest();
        return request;
    }

    private FindMetricsAverageResponse createSampleSuccessResponse() {
        FindMetricsAverageResponse response = new FindMetricsAverageResponse();
        response.setStatus(Status.SUCCESS);
        response.setMessage("Metric average is successfully gotten!");
        response.setMetricsAverage(new BigDecimal("25.5"));
        return response;
    }

    private FindMetricsAverageResponse createSampleFailureResponse() {
        FindMetricsAverageResponse response = new FindMetricsAverageResponse();
        response.setStatus(Status.FAILURE);
        response.setMessage("Failed to get metric average");
        return response;
    }
}
