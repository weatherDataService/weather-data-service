package weather.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import weather.entity.WeatherData;
import weather.model.MetricsAverage;
import weather.model.MetricsStatistic;

@RunWith(MockitoJUnitRunner.class)
public class WeatherDataDAOTest {

    @InjectMocks
    private WeatherDataDAO weatherDataDAO;

    @Mock
    private EntityManager entityManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddWeatherData() {
        WeatherData weatherData = new WeatherData();
        weatherData.setSensorID("sensor1");
        weatherData.setMetricsName("temperature");
        weatherData.setMetricsValue(BigDecimal.valueOf(25.5));
        weatherData.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));

        WeatherData result = weatherDataDAO.addWeatherData("sensor1", "temperature", BigDecimal.valueOf(25.5), new Date());

        verify(entityManager, times(1)).persist(any(WeatherData.class));
    }

    @Test
    public void testFindWeatherDataBySensorID() {
        TypedQuery<WeatherData> query = mock(TypedQuery.class);
        List<WeatherData> expectedData = Collections.singletonList(new WeatherData());

        when(entityManager.createNamedQuery(eq("WeatherData.findWeatherDataBySensorID"), eq(WeatherData.class)))
                .thenReturn(query);
        when(query.setParameter(eq("sensorIDs"), anyList())).thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedData);

        List<WeatherData> result = weatherDataDAO.findWeatherDataBySensorID(Collections.singletonList("sensor1"), new Date(), new Date());

        assertEquals(expectedData, result);
    }

    @Test
    public void testFindAllWeatherData() {
        TypedQuery<WeatherData> query = mock(TypedQuery.class);
        List<WeatherData> expectedData = Arrays.asList(new WeatherData(), new WeatherData());

        when(entityManager.createNamedQuery(eq("WeatherData.findAllWeatherData"), eq(WeatherData.class)))
                .thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedData);

        List<WeatherData> result = weatherDataDAO.findAllWeatherData(new Date(), new Date());

        assertEquals(expectedData, result);
    }

    @Test
    public void testFindLatestWeatherData() {

        List<WeatherData> mockWeatherDatasList = new ArrayList<>();
        WeatherData weatherData = new WeatherData();
        weatherData.setSensorID("sensorNo1");
        weatherData.setMetricsName("temperature");
        mockWeatherDatasList.add(weatherData);
        TypedQuery<WeatherData> mockQuery = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findWeatherDataBySensorIDMetricsName"), eq(WeatherData.class)))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("sensorID"), any(String.class)))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("metricsName"), any(String.class)))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList())
                .thenReturn(mockWeatherDatasList);

        List<WeatherData> result = weatherDataDAO.findLatestWeatherData(List.of("sensorNo1"));

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(entityManager, times(3)).createNamedQuery(eq("WeatherData.findWeatherDataBySensorIDMetricsName"), eq(WeatherData.class));
    }

    @Test
    public void testFindMetricsAverageFromLatestMetricsData() {
        List<WeatherData> mockWeatherDatasList = new ArrayList<>();
        WeatherData weatherData = new WeatherData();
        weatherData.setSensorID("sensorNo1");
        weatherData.setMetricsName("temperature");
        mockWeatherDatasList.add(weatherData);
        TypedQuery<WeatherData> mockQuery = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findWeatherDataBySensorIDMetricsName"), eq(WeatherData.class)))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("sensorID"), any(String.class)))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("metricsName"), any(String.class)))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList())
                .thenReturn(mockWeatherDatasList);

        List<MetricsAverage> result = weatherDataDAO.findMetricsAverageFromLatestMetricsData(List.of("sensorNo1"), List.of("temperature"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindMetricsAverageBySensorIDMetricsName() {
        Query query = mock(Query.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findMetricsAverageBySensorIDMetricsName"))).thenReturn(query);
        when(query.setParameter(eq("sensorIDs"), anyList())).thenReturn(query);
        when(query.setParameter(eq("metricsNames"), anyList())).thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);

        List<Object[]> mockQueryResults = Arrays.asList(
                new Object[]{"sensor1", "temperature", 25.5},
                new Object[]{"sensor2", "humidity", 60.0}
        );

        when(query.getResultList()).thenReturn(mockQueryResults);

        List<String> sensorIDs = Arrays.asList("sensor1", "sensor2");
        List<String> metricsNames = Arrays.asList("temperature", "humidity");
        Date startDate = new Date();
        Date endDate = new Date();

        List<MetricsAverage> result = weatherDataDAO.findMetricsAverageBySensorIDMetricsName(sensorIDs, metricsNames, startDate, endDate);

        assertEquals(2, result.size());

        assertEquals("sensor1", result.get(0).getSensorID());
        assertEquals("temperature", result.get(0).getMetricsName());
        assertEquals(BigDecimal.valueOf(25.5), result.get(0).getAverage());

        assertEquals("sensor2", result.get(1).getSensorID());
        assertEquals("humidity", result.get(1).getMetricsName());
        assertEquals(BigDecimal.valueOf(60.0), result.get(1).getAverage());
    }

    @Test
    public void testFindMetricsAverageByMetricsName() {
        Query query = mock(Query.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findMetricsAverageByMetricsName"))).thenReturn(query);
        when(query.setParameter(eq("metricsNames"), anyList())).thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);

        List<Object[]> mockQueryResults = new ArrayList<>();
        Object[] resultRow = {"sensorId1", "temperature", 25.5};
        mockQueryResults.add(resultRow);

        when(query.getResultList()).thenReturn(mockQueryResults);

        List<MetricsAverage> result = weatherDataDAO.findMetricsAverageByMetricsName(
                List.of("temperature"),
                new Date(), new Date());

        assertEquals(BigDecimal.valueOf(25.5), result.get(0).getAverage());
    }

    @Test
    public void testFindOverallMetricsAverage() {
        Query query = mock(Query.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findOverallMetricsAverage"))).thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);

        List<Object[]> mockQueryResults = new ArrayList<>();
        Object[] resultRow = {"sensorId1", "temperature", 25.5};
        mockQueryResults.add(resultRow);
        when(query.getResultList()).thenReturn(mockQueryResults);

        List<MetricsAverage> result = weatherDataDAO.findOverallMetricsAverage(new Date(), new Date());

        assertEquals("sensorId1", result.get(0).getSensorID());
        assertEquals("temperature", result.get(0).getMetricsName());
        assertEquals(BigDecimal.valueOf(25.5), result.get(0).getAverage());
    }

    @Test
    public void testFindMetricsStatisticBySensorIDMetricsName() {
        Query query = mock(Query.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findMetricsStatisticBySensorIDMetricsName")))
                .thenReturn(query);
        when(query.setParameter(eq("sensorIDs"), anyList())).thenReturn(query);
        when(query.setParameter(eq("metricsName"), anyString())).thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);

        List<Object[]> queryResults = Arrays.asList(
                new Object[]{"sensor1", "temperature", 10.0, 20.0, 30.0, 15.0},
                new Object[]{"sensor2", "temperature", 15.0, 25.0, 35.0, 20.0}
        );

        when(query.getResultList()).thenReturn(queryResults);

        List<MetricsStatistic> expectedResult = Arrays.asList(
                createMetricStatistic("sensor1", "temperature", 10.0, 20.0, 30.0, 15.0),
                createMetricStatistic("sensor2", "temperature", 15.0, 25.0, 35.0, 20.0)
        );

        List<MetricsStatistic> result = weatherDataDAO.findMetricsStatisticBySensorIDMetricsName(
                Arrays.asList("sensor1", "sensor2"), "temperature", new Date(), new Date());

        assertNotNull(result);
    }

    @Test
    public void testFindOverallMetricsStatisticByMetricsName() {
        Query query = mock(Query.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findOverallMetricsStatisticByMetricsName")))
                .thenReturn(query);
        when(query.setParameter(eq("metricsName"), anyString())).thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);

        List<Object[]> queryResults = Arrays.asList(
                new Object[]{"sensor1", "temperature", 10.0, 20.0, 30.0, 15.0},
                new Object[]{"sensor2", "temperature", 15.0, 25.0, 35.0, 20.0}
        );

        when(query.getResultList()).thenReturn(queryResults);

        List<MetricsStatistic> expectedResult = Arrays.asList(
                createMetricStatistic("sensor1", "temperature", 10.0, 20.0, 30.0, 15.0),
                createMetricStatistic("sensor2", "temperature", 15.0, 25.0, 35.0, 20.0)
        );

        List<MetricsStatistic> result = weatherDataDAO.findOverallMetricsStatisticByMetricsName("temperature", new Date(), new Date());

        assertNotNull(result);
    }

    @Test
    public void testFindAllMetricsStatistic() {
        Query query = mock(Query.class);
        when(entityManager.createNamedQuery(eq("WeatherData.findAllMetricsStatistic")))
                .thenReturn(query);
        when(query.setParameter(eq("startDate"), any(Date.class))).thenReturn(query);
        when(query.setParameter(eq("endDate"), any(Date.class))).thenReturn(query);

        List<Object[]> queryResults = Arrays.asList(
                new Object[]{"sensor1", "temperature", 10.0, 20.0, 30.0, 15.0},
                new Object[]{"sensor2", "temperature", 15.0, 25.0, 35.0, 20.0}
        );

        when(query.getResultList()).thenReturn(queryResults);

        List<MetricsStatistic> expectedResult = Arrays.asList(
                createMetricStatistic("sensor1", "temperature", 10.0, 20.0, 30.0, 15.0),
                createMetricStatistic("sensor2", "temperature", 15.0, 25.0, 35.0, 20.0)
        );

        List<MetricsStatistic> result = weatherDataDAO.findAllMetricsStatistic(new Date(), new Date());

        assertNotNull(result);
    }

    private MetricsStatistic createMetricStatistic(String sensorID, String metricName,
            double minValue, double maxValue,
            double sum, double average) {
        MetricsStatistic ms = new MetricsStatistic();
        ms.setSensorID(sensorID);
        ms.setMetricsName(metricName);
        ms.setMinValue(BigDecimal.valueOf(minValue));
        ms.setMaxValue(BigDecimal.valueOf(maxValue));
        ms.setSum(BigDecimal.valueOf(sum));
        ms.setAverage(BigDecimal.valueOf(average));
        return ms;
    }

}
