package weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class FindMetricsAverageResponse extends RestResponseBase {

    private BigDecimal metricsAverage;

    public BigDecimal getMetricsAverage() {
        return metricsAverage;
    }

    public void setMetricsAverage(BigDecimal metricsAverage) {
        this.metricsAverage = metricsAverage;
    }

}
