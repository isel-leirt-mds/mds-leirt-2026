package pt.isel.mds.weather2.dto;

import com.google.gson.annotations.SerializedName;
import pt.isel.mds.weather2.dto.WeatherInfoForecastDto;

import java.util.List;

public class ForecastInfoDto {

    private List<WeatherInfoForecastDto> list;

    @SerializedName("city")
    private LocalDto local;

    public List<WeatherInfoForecastDto> getForecast() { return list; }
    public LocalDto getLocal() { return local; }
}
