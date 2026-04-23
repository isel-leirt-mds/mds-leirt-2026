package pt.isel.mds.weather1.exceptions;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String msg) {
        super(msg);
    }
}
