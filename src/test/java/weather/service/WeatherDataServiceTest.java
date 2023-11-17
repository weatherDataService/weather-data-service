package weather.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import weather.api.model.enums.Status;
import weather.api.model.request.AddWeatherDataRequest;
import weather.api.model.request.FindAllMetricsStatisticRequest;
import weather.api.model.request.FindMetricsAverageRequest;
import weather.api.model.request.FindMetricsStatisticRequest;
import weather.api.model.request.FindWeatherDataRequest;
import weather.api.model.response.AddWeatherDataResponse;
import weather.api.model.response.FindAllMetricsStatisticResponse;
import weather.api.model.response.FindMetricsAverageResponse;
import weather.api.model.response.FindMetricsStatisticResponse;
import weather.api.model.response.FindWeatherDataResponse;
import weather.dao.WeatherDataDAO;
import weather.entity.WeatherData;
import weather.model.MetricsStatistic;

@RunWith(MockitoJUnitRunner.class)
public class WeatherDataServiceTest {

    @InjectMocks
    private WeatherDataService weatherDataService;

    @Mock
    private WeatherDataDAO weatherDataDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddWeatherData() {
        AddWeatherDataRequest request = createAddWeatherDataRequest();
        Map<String, BigDecimal> weatherDataMap = createWeatherDataMap();
        request.setWeatherData(weatherDataMap);

        AddWeatherDataResponse response = weatherDataService.addWeatherData(request);

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Weather data are successfully added!", response.getMessage());
        verify(weatherDataDao).addWeatherData(any(String.class), any(String.class), any(BigDecimal.class), any(Date.class));
    }

    @Test
    public void testAddWeatherDataNoWeatherData() {
        AddWeatherDataRequest request = createAddWeatherDataRequest();
        request.setWeatherData(Collections.emptyMap());

        AddWeatherDataResponse response = weatherDataService.addWeatherData(request);

        assertEquals(Status.FAILURE, response.getStatus());
        assertEquals("No weather data in the request", response.getMessage());
    }

    @Test
    public void testAddWeatherDataException() {
        AddWeatherDataRequest request = createAddWeatherDataRequest();
        Map<String, BigDecimal> weatherDataMap = createWeatherDataMap();
        request.setWeatherData(weatherDataMap);

        doThrow(new NullPointerException("Mocked DAO Exception")).when(weatherDataDao)
                .addWeatherData(any(String.class), any(String.class), any(BigDecimal.class), any(Date.class));

        AddWeatherDataResponse response = weatherDataService.addWeatherData(request);

        assertEquals(Status.ERROR, response.getStatus());
        assertEquals("System error in adding weather data", response.getMessage());
    }
    
    @Test
    public void testFindWeatherDataAllSensors() {
        FindWeatherDataRequest request = createFindWeatherDataRequest();
        request.setSensorIDs(null);
        List<WeatherData> mockWeatherDataList = Arrays.asList(
                new WeatherData("sensor1", "temperature", new BigDecimal(25.00), new Date()),
                new WeatherData("sensor2", "temperature", new BigDecimal(26.00), new Date())
        );
        when(weatherDataDao.findAllWeatherData(any(Date.class), any(Date.class))).thenReturn(mockWeatherDataList);

        FindWeatherDataResponse response = weatherDataService.findWeatherData(request);

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Weather data are successfully gotten!", response.getMessage());
        assertEquals(mockWeatherDataList, response.getWeatherDataList());
    }

    @Test
    public void testFindWeatherDataBySensor() {
        FindWeatherDataRequest request = createFindWeatherDataRequest();
        List<WeatherData> mockWeatherDataList = Collections.singletonList(
                new WeatherData("sensor1", "temperature", new BigDecimal(25.00), new Date())
        );
        when(weatherDataDao.findWeatherDataBySensorID(any(List.class), any(Date.class), any(Date.class)))
                .thenReturn(mockWeatherDataList);

        FindWeatherDataResponse response = weatherDataService.findWeatherData(request);

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Weather data are successfully gotten!", response.getMessage());
        assertEquals(mockWeatherDataList, response.getWeatherDataList());
    }
    
    @Test
    public void testFindMetricAverageOverall() {
        when(weatherDataDao.findOverallMetricsAverage(anyString(), any(), any()))
                .thenReturn(new BigDecimal("25.5"));

        FindMetricsAverageRequest request = new FindMetricsAverageRequest();
        request.setMetricsName("temperature");
        FindMetricsAverageResponse response = weatherDataService.findMetricsAverage(request);

        verify(weatherDataDao, times(1)).findOverallMetricsAverage(anyString(), any(), any());

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Metrics average is successfully gotten!", response.getMessage());
        assertEquals(new BigDecimal("25.5"), response.getMetricsAverage());
    }

    @Test
    public void testFindMetricAverageBySensor() {
        when(weatherDataDao.findMetricsAverageBySensorID(anyList(), anyString(), any(), any()))
                .thenReturn(new BigDecimal("18.9"));

        FindMetricsAverageRequest request = new FindMetricsAverageRequest();
        List<String> sensorIDs = new ArrayList<>();
        sensorIDs.add("Sensor01");
        request.setSensorIDs(sensorIDs);
        request.setMetricsName("temperature");
        FindMetricsAverageResponse response = weatherDataService.findMetricsAverage(request);

        verify(weatherDataDao, times(1))
                .findMetricsAverageBySensorID(anyList(), anyString(), any(), any());

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Metrics average is successfully gotten!", response.getMessage());
        assertEquals(new BigDecimal("18.9"), response.getMetricsAverage());
    }

    @Test
    public void testFindMetricStatisticByAllSensorsForOneMetrics() {
        FindMetricsStatisticRequest request = createFindMetricStatisticRequest();
        request.setSensorIDs(null);
        List<MetricsStatistic> mockMetricStatisticList = Arrays.asList(new MetricsStatistic("sensor1", "temperature", new BigDecimal(20.00), new BigDecimal(25.00), new BigDecimal(150.00), new BigDecimal(22.50)),
                new MetricsStatistic("sensor2", "temperature", new BigDecimal(21.00), new BigDecimal(26.00), new BigDecimal(147.00), new BigDecimal(23.50))
        );
        when(weatherDataDao.findOverallMetricsStatisticByMetricsName(anyString(),any(Date.class), any(Date.class)))
                .thenReturn(mockMetricStatisticList);

        FindMetricsStatisticResponse response = weatherDataService.findMetricsStatistic(request);

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Metrics statistics are successfully gotten!", response.getMessage());
        assertEquals(mockMetricStatisticList, response.getMetricsStatisticList());
    }

    @Test
    public void testFindMetricStatisticBySensorsForOneMetrics() {
        FindMetricsStatisticRequest request = createFindMetricStatisticRequest();
        List<MetricsStatistic> mockMetricStatisticList = Collections.singletonList(new MetricsStatistic("sensor1", "temperature", new BigDecimal(20.00), new BigDecimal(25.00), new BigDecimal(150.00), new BigDecimal(22.50))
        );
        when(weatherDataDao.findMetricsStatisticBySensorIDMetricsName(any(List.class), any(String.class), any(Date.class), any(Date.class)))
                .thenReturn(mockMetricStatisticList);

        FindMetricsStatisticResponse response = weatherDataService.findMetricsStatistic(request);

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Metrics statistics are successfully gotten!", response.getMessage());
        assertEquals(mockMetricStatisticList, response.getMetricsStatisticList());
    }
    
    @Test
    public void testListOverallMetricsStatistic() {
        FindAllMetricsStatisticRequest request = new FindAllMetricsStatisticRequest();
        request.setStartDate("2023-11-01");
        request.setEndDate("2023-11-16");
        List<MetricsStatistic> mockMetricStatisticList = Arrays.asList(new MetricsStatistic("sensor1", "temperature", new BigDecimal(20.00), new BigDecimal(25.00), new BigDecimal(150.00), new BigDecimal(22.50)),
                new MetricsStatistic("sensor2", "temperature", new BigDecimal(21.00), new BigDecimal(26.00), new BigDecimal(147.00), new BigDecimal(23.50))
        );
        when(weatherDataDao.findAllMetricsStatistic(any(Date.class), any(Date.class)))
                .thenReturn(mockMetricStatisticList);

        FindAllMetricsStatisticResponse response = weatherDataService.findAllMetricsStatistic(request);

        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("Metrics statistics are successfully gotten!", response.getMessage());
        assertEquals(mockMetricStatisticList, response.getMetricsStatisticList());
    }
    
    private AddWeatherDataRequest createAddWeatherDataRequest() {
        AddWeatherDataRequest request = new AddWeatherDataRequest();
        request.setSensorID("sensor123");
        request.setMeasureTime(new Date());
        return request;
    }

    private Map<String, BigDecimal> createWeatherDataMap() {
        return Collections.singletonMap("temperature", BigDecimal.valueOf(25.0));
    }
    
    private FindWeatherDataRequest createFindWeatherDataRequest() {
        FindWeatherDataRequest request = new FindWeatherDataRequest();
        request.setSensorIDs(Arrays.asList("sensor1", "sensor2"));
        request.setStartDate("2023-11-01");
        request.setEndDate("2023-11-16");
        return request;
    }
        
    private FindMetricsStatisticRequest createFindMetricStatisticRequest() {
        FindMetricsStatisticRequest request = new FindMetricsStatisticRequest();
        request.setSensorIDs(Arrays.asList("sensor1", "sensor2"));
        request.setMetricsName("temperature");
        request.setStartDate("2023-11-01");
        request.setEndDate("2023-11-16");
        return request;
    }
}
