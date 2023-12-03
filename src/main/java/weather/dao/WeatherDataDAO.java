package weather.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import weather.entity.WeatherData;
import weather.model.MetricsAverage;
import weather.model.MetricsStatistic;

@Named
@RequestScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class WeatherDataDAO {

    //These data will come from database table "WEATHER_SENSOR".
    private static final List<String> SENSOR_IDS = List.of("S01", "S02", "S03", "S04", "S05");
    //These data will come from database table "WEATHER_METRICS".
    private static final List<String> METRICS_NAMES = List.of("temp", "hum", "wp");

    @PersistenceContext(unitName = "weatherPU")
    private EntityManager em;

    @Transactional(Transactional.TxType.REQUIRED)
    public WeatherData addWeatherData(String sensorID, String metricsName, BigDecimal metricsValue, Date measureTime) {

        WeatherData weatherData = new WeatherData();
        weatherData.setSensorID(sensorID);
        weatherData.setMetricsName(metricsName);
        weatherData.setMetricsValue(metricsValue);
        weatherData.setCreatedDate(measureTime);

        em.persist(weatherData);

        return weatherData;
    }

    public List<WeatherData> findWeatherDataBySensorID(List<String> sensorIDs, Date startDate, Date endDate) {
        return em.createNamedQuery("WeatherData.findWeatherDataBySensorID", WeatherData.class)
                .setParameter("sensorIDs", sensorIDs)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<WeatherData> findAllWeatherData(Date startDate, Date endDate) {
        return em.createNamedQuery("WeatherData.findAllWeatherData", WeatherData.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<WeatherData> findLatestWeatherData(List<String> sensorIDs) {
        if (sensorIDs == null || sensorIDs.isEmpty()) {
            sensorIDs = SENSOR_IDS;
        }

        List<WeatherData> weatherDataList = new ArrayList<>();
        for (String sensorID : sensorIDs) {
            for (String metricsName : METRICS_NAMES) {
                List<WeatherData> weatherDatasList = em.createNamedQuery("WeatherData.findWeatherDataBySensorIDMetricsName", WeatherData.class)
                        .setParameter("sensorID", sensorID)
                        .setParameter("metricsName", metricsName)
                        .getResultList();
                if (weatherDatasList == null || weatherDatasList.isEmpty()) {
                    continue;
                }
                weatherDataList.add(weatherDatasList.get(0));
            }
        }

        return weatherDataList;
    }

    public List<MetricsAverage> findMetricsAverageFromLatestMetricsData(List<String> sensorIDs, List<String> metricsNames) {
        if (sensorIDs == null || sensorIDs.isEmpty()) {
            sensorIDs = SENSOR_IDS;
        }

        if (metricsNames == null || metricsNames.isEmpty()) {
            metricsNames = METRICS_NAMES;
        }

        List<MetricsAverage> metricsAverageList = new ArrayList<>();
        for (String sensorID : sensorIDs) {
            for (String metricsName : metricsNames) {
                List<WeatherData> weatherDatasList = em.createNamedQuery("WeatherData.findWeatherDataBySensorIDMetricsName", WeatherData.class)
                        .setParameter("sensorID", sensorID)
                        .setParameter("metricsName", metricsName)
                        .getResultList();
                if (weatherDatasList == null || weatherDatasList.isEmpty()) {
                    continue;
                }
                WeatherData metricsData = weatherDatasList.get(0);
                MetricsAverage metricsAverage = new MetricsAverage();
                metricsAverage.setSensorID(metricsData.getSensorID());
                metricsAverage.setMetricsName(metricsData.getMetricsName());
                metricsAverage.setAverage(metricsData.getMetricsValue());
                metricsAverageList.add(metricsAverage);
            }
        }

        return metricsAverageList;
    }

    public List<MetricsAverage> findMetricsAverageBySensorIDMetricsName(List<String> sensorIDs, List<String> metricsNames, Date startDate, Date endDate) {
        List<Object[]> queryResults = em.createNamedQuery("WeatherData.findMetricsAverageBySensorIDMetricsName")
                .setParameter("sensorIDs", sensorIDs)
                .setParameter("metricsNames", metricsNames)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();

        List<MetricsAverage> metricsAverages = new ArrayList<>();

        queryResults.stream().map(record -> {
            MetricsAverage ms = new MetricsAverage();
            ms.setSensorID((String) record[0]);
            ms.setMetricsName((String) record[1]);
            ms.setAverage(BigDecimal.valueOf((double) record[2]));
            return ms;
        }).forEachOrdered(ms -> {
            metricsAverages.add(ms);
        });

        return metricsAverages;
    }

    public List<MetricsAverage> findMetricsAverageByMetricsName(List<String> metricsNames, Date startDate, Date endDate) {
        List<Object[]> queryResults = em.createNamedQuery("WeatherData.findMetricsAverageByMetricsName")
                .setParameter("metricsNames", metricsNames)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();

        List<MetricsAverage> metricsAverages = new ArrayList<>();

        queryResults.stream().map(record -> {
            MetricsAverage ms = new MetricsAverage();
            ms.setSensorID((String) record[0]);
            ms.setMetricsName((String) record[1]);
            ms.setAverage(BigDecimal.valueOf((double) record[2]));
            return ms;
        }).forEachOrdered(ms -> {
            metricsAverages.add(ms);
        });

        return metricsAverages;
    }

    public List<MetricsAverage> findOverallMetricsAverage(Date startDate, Date endDate) {
        List<Object[]> queryResults = em.createNamedQuery("WeatherData.findOverallMetricsAverage")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();

        List<MetricsAverage> metricsAverages = new ArrayList<>();

        queryResults.stream().map(record -> {
            MetricsAverage ms = new MetricsAverage();
            ms.setSensorID((String) record[0]);
            ms.setMetricsName((String) record[1]);
            ms.setAverage(BigDecimal.valueOf((double) record[2]));
            return ms;
        }).forEachOrdered(ms -> {
            metricsAverages.add(ms);
        });

        return metricsAverages;
    }

    public List<MetricsStatistic> findMetricsStatisticBySensorIDMetricsName(List<String> sensorIDs, String metricsName, Date startDate, Date endDate) {
        List<Object[]> queryResults = em.createNamedQuery("WeatherData.findMetricsStatisticBySensorIDMetricsName")
                .setParameter("sensorIDs", sensorIDs)
                .setParameter("metricsName", metricsName)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();

        List<MetricsStatistic> metricsStatisticList = new ArrayList<>();

        queryResults.stream().map(record -> {
            MetricsStatistic ms = new MetricsStatistic();
            ms.setSensorID((String) record[0]);
            ms.setMetricsName((String) record[1]);
            ms.setMinValue((BigDecimal) record[2]);
            ms.setMaxValue((BigDecimal) record[3]);
            ms.setSum((BigDecimal) record[4]);
            ms.setAverage(BigDecimal.valueOf((double) record[5]));
            return ms;
        }).forEachOrdered(ms -> {
            metricsStatisticList.add(ms);
        });

        return metricsStatisticList;
    }

    public List<MetricsStatistic> findOverallMetricsStatisticByMetricsName(String metricsName, Date startDate, Date endDate) {
        List<Object[]> queryResults = em.createNamedQuery("WeatherData.findOverallMetricsStatisticByMetricsName")
                .setParameter("metricsName", metricsName)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();

        List<MetricsStatistic> metricsStatisticList = new ArrayList<>();

        queryResults.stream().map(record -> {
            MetricsStatistic ms = new MetricsStatistic();
            ms.setSensorID((String) record[0]);
            ms.setMetricsName((String) record[1]);
            ms.setMinValue((BigDecimal) record[2]);
            ms.setMaxValue((BigDecimal) record[3]);
            ms.setSum((BigDecimal) record[4]);
            ms.setAverage(BigDecimal.valueOf((double) record[5]));
            return ms;
        }).forEachOrdered(ms -> {
            metricsStatisticList.add(ms);
        });

        return metricsStatisticList;
    }

    public List<MetricsStatistic> findAllMetricsStatistic(Date startDate, Date endDate) {
        List<Object[]> queryResults = em.createNamedQuery("WeatherData.findAllMetricsStatistic")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();

        List<MetricsStatistic> metricsStatisticList = new ArrayList<>();

        queryResults.stream().map(record -> {
            MetricsStatistic ms = new MetricsStatistic();
            ms.setSensorID((String) record[0]);
            ms.setMetricsName((String) record[1]);
            ms.setMinValue((BigDecimal) record[2]);
            ms.setMaxValue((BigDecimal) record[3]);
            ms.setSum((BigDecimal) record[4]);
            ms.setAverage(BigDecimal.valueOf((double) record[5]));
            return ms;
        }).forEachOrdered(ms -> {
            metricsStatisticList.add(ms);
        });

        return metricsStatisticList;
    }

}
