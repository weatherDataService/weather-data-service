package weather.api.api.metricsaverage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
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

    @InjectMocks
    private Find find;

    @Mock
    private WeatherDataService weatherDataService;

    @Test
    public void testFindMetricsAverage() {
        FindMetricsAverageRequest request = new FindMetricsAverageRequest();

        FindMetricsAverageResponse mockedResponse = new FindMetricsAverageResponse();
        mockedResponse.setStatus(Status.SUCCESS);
        mockedResponse.setMessage("Sample message");

        when(weatherDataService.findMetricsAverage(any(FindMetricsAverageRequest.class)))
                .thenReturn(mockedResponse);
        Response actualResponse = find.findMetricsAverage(request);

        assertNotNull(actualResponse);
        assertEquals(Response.Status.OK.getStatusCode(), actualResponse.getStatus());
        verify(weatherDataService, times(1)).findMetricsAverage(request);
    }
}
