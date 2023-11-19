package weather.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import weather.api.model.request.FindWeatherDataRequestBase;

public class Utility {

    private static final DateTimeFormatter DATE_FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Utility() {
    }

    public static Date getDateFromString(String dateString) {
        if (dateString == null) {
            return null;
        }

        try {
            LocalDate eventDate = LocalDate.parse(dateString, DATE_FORMATTER_YYYYMMDD);
            return Date.from(eventDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception ex) {
        }
        return null;
    }

    public static Date getStartDate(String startDateString) {
        if (StringUtils.isBlank(startDateString)) {
            return Timestamp.valueOf(LocalDateTime.now().minusHours(1));
        } else {
            return getDateFromString(startDateString);
        }
    }

    public static Date getEndDate(String endDateString) {
        if (StringUtils.isBlank(endDateString)) {
            return Timestamp.valueOf(LocalDateTime.now());
        } else {
            return getDateFromString(endDateString);
        }
    }

    public static void validateStartEndDates(FindWeatherDataRequestBase request) throws WeatherDataServiceException {
        if (StringUtils.isBlank(request.getStartDate()) && StringUtils.isBlank(request.getEndDate())) {
            if (getDateFromString(request.getStartDate()).after(getDateFromString(request.getEndDate()))) {
                throw new WeatherDataServiceException("Start date is after end date");
            }
        }
    }

    public static void validateStartEndDatesForFindStatistic(FindWeatherDataRequestBase request) throws WeatherDataServiceException {
        if (StringUtils.isBlank(request.getStartDate())) {
            throw new WeatherDataServiceException("Start date missing");
        } else if (StringUtils.isBlank(request.getEndDate())) {
            throw new WeatherDataServiceException("End date missing");
        } else if (getDateFromString(request.getStartDate()).after(getDateFromString(request.getEndDate()))) {
            throw new WeatherDataServiceException("Start date is after end date");
        }
    }

}
