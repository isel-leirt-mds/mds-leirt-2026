package pt.isel.mds.weather1.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static pt.isel.mds.weather1.utils.PrintUtils.EOL;

public class WeatherInfoForecastDto extends WeatherInfoBaseDto {

    public LocalDate getDate() {
        var dt = dateTime();
        return LocalDate.of(dt.getYear(), dt.getMonth(), dt.getDayOfMonth());
    }

    @Override
    public String toString() {
        return "{" + EOL
            + "\tdateTime = " + dateTime() + EOL
            + "\tdescription = " + description() + EOL
            + "\tweather = " + weather() + EOL
            + "}";
    }
}
