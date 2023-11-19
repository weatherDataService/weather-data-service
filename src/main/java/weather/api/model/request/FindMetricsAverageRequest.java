package weather.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FindMetricsAverageRequest extends FindWeatherDataRequestBase {
    
    private List<String> metricsNames;

    public List<String> getMetricsNames() {
        return metricsNames;
    }

    public void setMetricsNames(List<String> metricsNames) {
        this.metricsNames = metricsNames;
    }
    
}
