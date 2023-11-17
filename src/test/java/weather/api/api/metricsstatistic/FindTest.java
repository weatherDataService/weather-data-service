package weather.api.api.metricsstatistic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import weather.api.metricsstatistic.Find;
import weather.api.model.enums.Status;
import weather.api.model.request.FindMetricsStatisticRequest;
import weather.api.model.response.FindMetricsStatisticResponse;
import weather.model.MetricsStatistic;
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
    public void testFindMetricStatisticSuccess() {
        FindMetricsStatisticRequest request = createSampleRequest();
        FindMetricsStatisticResponse response = createSampleSuccessResponse();

        when(weatherDataService.findMetricsStatistic(request)).thenReturn(response);

        Response result = find.findMetricsStatistic(request);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    @Test
    public void testFindMetricStatisticFailure() {
        FindMetricsStatisticRequest request = createSampleRequest();
        FindMetricsStatisticResponse response = createSampleFailureResponse();

        when(weatherDataService.findMetricsStatistic(request)).thenReturn(response);

        Response result = find.findMetricsStatistic(request);

        assertEquals(Response.Status.ACCEPTED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    private FindMetricsStatisticRequest createSampleRequest() {
        FindMetricsStatisticRequest request = new FindMetricsStatisticRequest();
        return request;
    }

    private FindMetricsStatisticResponse createSampleSuccessResponse() {
        FindMetricsStatisticResponse response = new FindMetricsStatisticResponse();
        response.setStatus(Status.SUCCESS);
        response.setMessage("Metric statistics are successfully gotten!");
        response.setMetricsStatisticList(createSampleMetricStatisticList());
        return response;
    }

    private List<MetricsStatistic> createSampleMetricStatisticList() {
        List<MetricsStatistic> metricStatisticList = new ArrayList<>();
        return metricStatisticList;
    }

    private FindMetricsStatisticResponse createSampleFailureResponse() {
        FindMetricsStatisticResponse response = new FindMetricsStatisticResponse();
        response.setStatus(Status.FAILURE);
        response.setMessage("Failed to get metric statistics");
        return response;
    }
}
