package weather.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FindWeatherDataRequestBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> sensorIDs;

    //Date pattern is "yyyy-MM-dd"
    @Pattern(regexp = "|(^[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}$)?", message = "Invalid start date pattern")
    private String startDate;

    //Date pattern is "yyyy-MM-dd"
    @Pattern(regexp = "|(^[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}$)?", message = "Invalid end date pattern")
    private String endDate;

    public List<String> getSensorIDs() {
        return sensorIDs;
    }

    public void setSensorIDs(List<String> sensorIDs) {
        this.sensorIDs = sensorIDs;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
