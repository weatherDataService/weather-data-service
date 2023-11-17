package weather.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FindMetricsAverageRequest extends FindWeatherDataRequestBase {

    @NotEmpty(message = "Missing metrics name")
    private String metricsName;

    public String getMetricsName() {
        return metricsName;
    }

    public void setMetricsName(String metricsName) {
        this.metricsName = metricsName;
    }

}
