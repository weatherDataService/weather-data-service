package weather.api.api.weatherdata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import weather.api.model.enums.Status;
import weather.api.model.request.FindWeatherDataRequest;
import weather.api.model.response.FindWeatherDataResponse;
import weather.api.weatherdata.Find;
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
    public void testFindWeatherDataSuccess() {
        FindWeatherDataRequest request = createSampleRequest();
        FindWeatherDataResponse response = createSampleSuccessResponse();

        when(weatherDataService.findWeatherData(request)).thenReturn(response);

        Response result = find.findWeatherData(request);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    @Test
    public void testFindWeatherDataFailure() {
        FindWeatherDataRequest request = createSampleRequest();
        FindWeatherDataResponse response = createSampleFailureResponse();

        when(weatherDataService.findWeatherData(request)).thenReturn(response);

        Response result = find.findWeatherData(request);

        assertEquals(Response.Status.ACCEPTED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    private FindWeatherDataRequest createSampleRequest() {
        FindWeatherDataRequest request = new FindWeatherDataRequest();
        return request;
    }

    private FindWeatherDataResponse createSampleSuccessResponse() {
        FindWeatherDataResponse response = new FindWeatherDataResponse();
        response.setStatus(Status.SUCCESS);
        response.setMessage("Weather data are successfully gotten!");
        return response;
    }

    private FindWeatherDataResponse createSampleFailureResponse() {
        FindWeatherDataResponse response = new FindWeatherDataResponse();
        response.setStatus(Status.FAILURE);
        response.setMessage("Failed to get weather data");
        return response;
    }
}
