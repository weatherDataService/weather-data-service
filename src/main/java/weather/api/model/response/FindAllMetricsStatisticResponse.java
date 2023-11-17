package weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import weather.model.MetricsStatistic;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class FindAllMetricsStatisticResponse extends RestResponseBase {

    private List<MetricsStatistic> metricsStatisticList;

    public List<MetricsStatistic> getMetricsStatisticList() {
        return metricsStatisticList;
    }

    public void setMetricsStatisticList(List<MetricsStatistic> metricsStatisticList) {
        this.metricsStatisticList = metricsStatisticList;
    }
}
