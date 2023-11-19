package weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import weather.model.MetricsAverage;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class FindMetricsAverageResponse extends RestResponseBase {

    private List<MetricsAverage> metricsAverages;

    public List<MetricsAverage> getMetricsAverages() {
        return metricsAverages;
    }

    public void setMetricsAverages(List<MetricsAverage> metricsAverages) {
        this.metricsAverages = metricsAverages;
    }
    
}
