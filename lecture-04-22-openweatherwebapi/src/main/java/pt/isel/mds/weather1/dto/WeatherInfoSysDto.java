package pt.isel.mds.weather1.dto;

import pt.isel.mds.weather1.utils.TimeUtils;

import java.time.LocalDateTime;

public class WeatherInfoSysDto {
    private String country;
    private long sunrise;
    private long sunset;

    public String getCountry() {
        return this.country;
    }

    public LocalDateTime getSunrise() {
        return TimeUtils.fromUnixTime(sunrise);
    }

    public LocalDateTime getSunset() {
        return TimeUtils.fromUnixTime(sunset);
    }

}
