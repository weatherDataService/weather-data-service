package weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import weather.model.MetricsAverage;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class FindMetricsAverageResponse extends RestResponseBase {

    private List<MetricsAverage> metricsAverageList;

    public List<MetricsAverage> getMetricsAverageList() {
        return metricsAverageList;
    }

    public void setMetricsAverageList(List<MetricsAverage> metricsAverageList) {
        this.metricsAverageList = metricsAverageList;
    }
    
}
