package weather.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FindAllMetricsStatisticRequest extends FindWeatherDataRequestBase {
    
}
