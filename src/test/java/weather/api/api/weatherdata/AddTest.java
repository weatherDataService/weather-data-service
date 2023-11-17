package weather.api.api.weatherdata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import weather.api.model.enums.Status;
import weather.api.model.request.AddWeatherDataRequest;
import weather.api.model.response.AddWeatherDataResponse;
import weather.api.weatherdata.Add;
import weather.service.WeatherDataService;

@RunWith(MockitoJUnitRunner.class)
public class AddTest {

    @Mock
    private WeatherDataService weatherDataService;

    @InjectMocks
    private Add add;

    @Before
    public void setUp() {
    }

    @Test
    public void testAddWeatherDataSuccess() {
        AddWeatherDataRequest request = createSampleRequest();
        AddWeatherDataResponse response = createSampleSuccessResponse();

        when(weatherDataService.addWeatherData(request)).thenReturn(response);

        Response result = add.addWeatherData(request);

        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    @Test
    public void testAddWeatherDataFailure() {
        AddWeatherDataRequest request = createSampleRequest();
        AddWeatherDataResponse response = createSampleFailureResponse();

        when(weatherDataService.addWeatherData(request)).thenReturn(response);

        Response result = add.addWeatherData(request);

        assertEquals(Response.Status.ACCEPTED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
    }

    private AddWeatherDataRequest createSampleRequest() {
        AddWeatherDataRequest request = new AddWeatherDataRequest();
        Map<String, BigDecimal> weatherData = new HashMap<>();
        weatherData.put("temperature", BigDecimal.valueOf(25.5));
        request.setWeatherData(weatherData);
        return request;
    }

    private AddWeatherDataResponse createSampleSuccessResponse() {
        AddWeatherDataResponse response = new AddWeatherDataResponse();
        response.setStatus(Status.SUCCESS);
        response.setMessage("Weather data are successfully added!");
        return response;
    }

    private AddWeatherDataResponse createSampleFailureResponse() {
        AddWeatherDataResponse response = new AddWeatherDataResponse();
        response.setStatus(Status.FAILURE);
        response.setMessage("Failed to add weather data");
        return response;
    }
}
