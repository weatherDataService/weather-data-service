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
import weather.entity.WeatherData;
import weather.model.MetricsStatistic;

@Named
@RequestScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class WeatherDataDAO {

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

    public BigDecimal findMetricsAverageBySensorID(List<String> sensorIDs, String metricsName, Date startDate, Date endDate) {
        Number average = (Number) em.createNamedQuery("WeatherData.findMetricsAverageBySensorID")
                .setParameter("sensorIDs", sensorIDs)
                .setParameter("metricsName", metricsName)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getSingleResult();
        BigDecimal averageBigDecimal = new BigDecimal(average.toString());

        return averageBigDecimal;
    }

    public BigDecimal findOverallMetricsAverage(String metricsName, Date startDate, Date endDate) {
        Number average = (Number) em.createNamedQuery("WeatherData.findOverallMetricsAverage")
                .setParameter("metricsName", metricsName)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getSingleResult();
        BigDecimal averageBigDecimal = new BigDecimal(average.toString());

        return averageBigDecimal;
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
            ms.setMinValue(BigDecimal.valueOf((double) record[2]));
            ms.setMaxValue(BigDecimal.valueOf((double) record[3]));
            ms.setSum(BigDecimal.valueOf((double) record[4]));
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
            ms.setMinValue(BigDecimal.valueOf((double) record[2]));
            ms.setMaxValue(BigDecimal.valueOf((double) record[3]));
            ms.setSum(BigDecimal.valueOf((double) record[4]));
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
            ms.setMinValue(BigDecimal.valueOf((double) record[2]));
            ms.setMaxValue(BigDecimal.valueOf((double) record[3]));
            ms.setSum(BigDecimal.valueOf((double) record[4]));
            ms.setAverage(BigDecimal.valueOf((double) record[5]));
            return ms;
        }).forEachOrdered(ms -> {
            metricsStatisticList.add(ms);
        });

        return metricsStatisticList;
    }

}
