package weather.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddWeatherDataRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Missing sensor ID")
    private String sensorID;

    //The key is metrics name such as temperature while the value is metrics's value
    @NotNull(message = "Missing weather data")
    private Map<String, BigDecimal> weatherData;

    @NotEmpty(message = "Missing measurement time")
    @Pattern(regexp = "|(^[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}$)?", message = "Invalid date format")
    private String measureTime;

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public Map<String, BigDecimal> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Map<String, BigDecimal> weatherData) {
        this.weatherData = weatherData;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

}
