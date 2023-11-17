package weather.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FindMetricsStatisticRequest extends FindWeatherDataRequestBase {

    @NotEmpty(message = "Missing sensor ID")
    private String metricsName;

    public String getMetricsName() {
        return metricsName;
    }

    public void setMetricsName(String metricsName) {
        this.metricsName = metricsName;
    }
}
