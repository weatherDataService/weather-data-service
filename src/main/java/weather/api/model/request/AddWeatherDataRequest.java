package weather.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddWeatherDataRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Missing sensor ID")
    private String sensorID;

    //The key is metrics name such as temperature while the value is metrics's value
    private Map<String, BigDecimal> weatherData;
    
    @NotNull(message = "Missing measurement time")
    private Date measureTime;

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

    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }
    
}
