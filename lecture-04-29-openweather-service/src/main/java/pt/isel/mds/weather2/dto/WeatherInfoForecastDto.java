package pt.isel.mds.weather2.dto;


import java.time.LocalDate;

import static pt.isel.mds.weather2.utils.PrintUtils.EOL;

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
