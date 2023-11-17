package weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import weather.entity.WeatherData;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class FindWeatherDataResponse extends RestResponseBase {

    private List<WeatherData> weatherDataList;

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

}
