package weather.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.collections4.CollectionUtils;
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
import weather.util.Utility;
import weather.util.WeatherDataServiceException;

@Named
@RequestScoped
public class WeatherDataService {

    private static final Logger LOGGER = Logger.getLogger(WeatherDataService.class.getName());

    @Inject
    protected WeatherDataDAO weatherDataDao;

    public AddWeatherDataResponse addWeatherData(AddWeatherDataRequest request) {

        AddWeatherDataResponse response = new AddWeatherDataResponse();

        try {

            Map<String, BigDecimal> weatherDataMap = request.getWeatherData();

            if (weatherDataMap == null || weatherDataMap.isEmpty()) {
                throw new WeatherDataServiceException("No weather data in the request");
            }

            for (Entry<String, BigDecimal> entry : weatherDataMap.entrySet()) {
                weatherDataDao.addWeatherData(request.getSensorID(), entry.getKey(), entry.getValue(), request.getMeasureTime());
            }

            response.setStatus(Status.SUCCESS);
            response.setMessage("Weather data are successfully added!");

        } catch (WeatherDataServiceException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
            response.setStatus(Status.FAILURE);
            response.setMessage(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            response.setStatus(Status.ERROR);
            response.setMessage("System error in adding weather data");
        }

        return response;
    }

    public FindWeatherDataResponse findWeatherData(FindWeatherDataRequest request) {

        FindWeatherDataResponse response = new FindWeatherDataResponse();
        List<WeatherData> weatherDataList;

        try {
            List<String> sensorIDs = request.getSensorIDs();
            if (CollectionUtils.isEmpty(sensorIDs)) {
                weatherDataList = weatherDataDao.findAllWeatherData(Utility.getStartDate(request.getStartDate()),
                        Utility.getEndDate(request.getEndDate()));
            } else {
                weatherDataList = weatherDataDao.findWeatherDataBySensorID(sensorIDs,
                        Utility.getStartDate(request.getStartDate()),
                        Utility.getEndDate(request.getEndDate()));
            }

            response.setStatus(Status.SUCCESS);
            response.setMessage("Weather data are successfully gotten!");
            response.setWeatherDataList(weatherDataList);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            response.setStatus(Status.ERROR);
            response.setMessage("System error in getting weather data");
        }

        return response;
    }

    public FindMetricsAverageResponse findMetricsAverage(FindMetricsAverageRequest request) {
        FindMetricsAverageResponse response = new FindMetricsAverageResponse();
        BigDecimal metricsAverage;
        try {
            List<String> sensorIDs = request.getSensorIDs();
            if (CollectionUtils.isEmpty(sensorIDs)) {
                metricsAverage = weatherDataDao.findOverallMetricsAverage(request.getMetricsName(), Utility.getStartDate(request.getStartDate()),
                        Utility.getEndDate(request.getEndDate()));
            } else {
                metricsAverage = weatherDataDao.findMetricsAverageBySensorID(sensorIDs, request.getMetricsName(),
                        Utility.getStartDate(request.getStartDate()),
                        Utility.getEndDate(request.getEndDate()));
            }

            response.setStatus(Status.SUCCESS);
            response.setMessage("Metrics average is successfully gotten!");
            response.setMetricsAverage(metricsAverage);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            response.setStatus(Status.ERROR);
            response.setMessage("System error in getting metrics average");
        }

        return response;
    }

    public FindMetricsStatisticResponse findMetricsStatistic(FindMetricsStatisticRequest request) {

        FindMetricsStatisticResponse response = new FindMetricsStatisticResponse();
        List<MetricsStatistic> metricsStatisticList;

        try {
            List<String> sensorIDs = request.getSensorIDs();
            if (CollectionUtils.isEmpty(sensorIDs)) {
                metricsStatisticList = weatherDataDao.findOverallMetricsStatisticByMetricsName(request.getMetricsName(),Utility.getStartDate(request.getStartDate()),
                        Utility.getEndDate(request.getEndDate()));
            } else {
                metricsStatisticList = weatherDataDao.findMetricsStatisticBySensorIDMetricsName(sensorIDs,
                        request.getMetricsName(),
                        Utility.getStartDate(request.getStartDate()),
                        Utility.getEndDate(request.getEndDate()));
            }

            response.setStatus(Status.SUCCESS);
            response.setMessage("Metrics statistics are successfully gotten!");
            response.setMetricsStatisticList(metricsStatisticList);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            response.setStatus(Status.ERROR);
            response.setMessage("System error in getting metrics statistics");
        }

        return response;
    }
    
    public FindAllMetricsStatisticResponse findAllMetricsStatistic(FindAllMetricsStatisticRequest request) {

        FindAllMetricsStatisticResponse response = new FindAllMetricsStatisticResponse();
        List<MetricsStatistic> metricsStatisticList;

        try {

            metricsStatisticList = weatherDataDao.findAllMetricsStatistic(
                    Utility.getStartDate(request.getStartDate()),
                    Utility.getEndDate(request.getEndDate()));

            response.setStatus(Status.SUCCESS);
            response.setMessage("Metrics statistics are successfully gotten!");
            response.setMetricsStatisticList(metricsStatisticList);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            response.setStatus(Status.ERROR);
            response.setMessage("System error in getting metrics statistics");
        }

        return response;
    }


}
