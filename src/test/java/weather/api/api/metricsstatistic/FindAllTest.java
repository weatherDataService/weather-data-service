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
import weather.api.metricsstatistic.FindAll;
import weather.api.model.enums.Status;
import weather.api.model.request.FindAllMetricsStatisticRequest;
import weather.api.model.response.FindAllMetricsStatisticResponse;
import weather.model.MetricsStatistic;
import weather.service.WeatherDataService;

@RunWith(MockitoJUnitRunner.class)
public class FindAllTest {

    @Mock
    private WeatherDataService weatherDataService;

    @InjectMocks
    private FindAll findAll;

    @Before
    public void setUp() {
    }

    @Test
    public void testFindAllMetricStatisticSuccess() {
        FindAllMetricsStatisticRequest request = createSampleRequest();
        FindAllMetricsStatisticResponse response = createSampleSuccessResponse();

        when(weatherDataService.findAllMetricsStatistic(request)).thenReturn(response);

        Response result = findAll.findAllMetricsStatistic(request);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    @Test
    public void testFindMetricStatisticFailure() {
        FindAllMetricsStatisticRequest request = createSampleRequest();
        FindAllMetricsStatisticResponse response = createSampleFailureResponse();

        when(weatherDataService.findAllMetricsStatistic(request)).thenReturn(response);

        Response result = findAll.findAllMetricsStatistic(request);

        assertEquals(Response.Status.ACCEPTED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    private FindAllMetricsStatisticRequest createSampleRequest() {
        FindAllMetricsStatisticRequest request = new FindAllMetricsStatisticRequest();
        return request;
    }

    private FindAllMetricsStatisticResponse createSampleSuccessResponse() {
        FindAllMetricsStatisticResponse response = new FindAllMetricsStatisticResponse();
        response.setStatus(Status.SUCCESS);
        response.setMessage("Metric statistics are successfully gotten!");
        response.setMetricsStatisticList(createSampleMetricStatisticList());
        return response;
    }

    private List<MetricsStatistic> createSampleMetricStatisticList() {
        List<MetricsStatistic> metricStatisticList = new ArrayList<>();
        return metricStatisticList;
    }

    private FindAllMetricsStatisticResponse createSampleFailureResponse() {
        FindAllMetricsStatisticResponse response = new FindAllMetricsStatisticResponse();
        response.setStatus(Status.FAILURE);
        response.setMessage("Failed to get metric statistics");
        return response;
    }
}
