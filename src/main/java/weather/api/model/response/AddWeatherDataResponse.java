package weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class AddWeatherDataResponse extends RestResponseBase {
}
