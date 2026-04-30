package pt.isel.mds.weather2.exceptions;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String msg) {
        super(msg);
    }
}
