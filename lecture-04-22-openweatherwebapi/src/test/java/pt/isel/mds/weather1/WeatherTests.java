package pt.isel.mds.weather1;

import org.junit.jupiter.api.Test;
import pt.isel.mds.weather1.dto.*;
import pt.isel.mds.weather1.requests.FileRequest;
import pt.isel.mds.weather1.requests.HttpRequest;
import pt.isel.mds.weather1.requests.SaverRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.IO.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pt.isel.mds.weather1.queries.Queries.*;
import static pt.isel.mds.weather1.utils.PrintUtils.EOL;

public class WeatherTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;
    

    // direct API queries
    
    @Test
    public void get_weather_at_lisbon_now() {
        var request = new SaverRequest(new HttpRequest());
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(request );
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );


    }

    @Test
    public void get_weather_at_lisbon_now_offline() {
        var request = new FileRequest();
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(request );
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );

        println(winfo);
    }

    @Test
    public void get_air_pollution_in_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        
        PollutionInfoDto pi = webApi.airPollutionAt(
            LISBON_LAT, LISBON_LONG);
        println(pi);
    }
    
    @Test
    public void get_lisbon_forecast_test() {
        
        var weatherApi = new OpenWeatherWebApi();
        
        var forecast =
            weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);
        for (var df : forecast) {
            println(df);
        }
    }
    
    @Test
    public void get_air_pollution_history_by_period() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate end = LocalDate.of(2025, 3, 8);
        List<PollutionInfoDto> pinfo =
            webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);
        println(pinfo.size());
        for(PollutionInfoDto pi : pinfo) {
            println(pi);
        }
        assertEquals(121, pinfo.size() );
    }
    
    @Test
    public void get_location_info_by_name() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        String localName = "Lisboa";
        
        List<LocationDto> locations = webApi.search(localName);
        for(var loc : locations)
            println(loc);
        assertEquals(5, locations.size());
    }
    
    // Value added queries
    
    @Test
    public void get_cloud_periods_for_lisbon_forecast_test() {
        
        var weatherApi = new OpenWeatherWebApi();
        var forecast =
                filter(
                        weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG),
                        wi -> wi.description().contains("cloud")
                );
        
        println(toList(forecast));
    }
    
    @Test
    public void get_rainy_periods_for_lisbon_forecast_test() {
        var weatherApi = new OpenWeatherWebApi();

        var forecast =
                weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG);

        
        var res = filter(forecast,
            wf -> wf.description().contains("rain"));
        
        println(res);
    }

    
    @Test
    public void get_max_temp_for_clear_periods_in_lisbon_forecast() {
        var weatherApi = new OpenWeatherWebApi();
        
        var maxTempInRainyPeriod =
            max(
                filter(
                    weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG),
                    p -> p.description().contains("clear")
                ),
                Comparator.comparingDouble(WeatherInfoForecastDto::maxTemp)
            );
           
        println(maxTempInRainyPeriod);
    }

    record DatedValue<T>  (LocalDateTime dateTime, T value) {}

    @Test
    public void get_forecast_temperature_samples_in_rainy_days_at_lisbon() {
        var weatherApi = new OpenWeatherWebApi();

        var forecastTemps =
            map(
                filter(
                    weatherApi.forecastWeatherAt(LISBON_LAT, LISBON_LONG),
                    f -> f.description().contains("rain")
                    
                ),
                f -> new DatedValue<>(f.dateTime(), f.temp())
            );
        println("result size= "+ count(forecastTemps));
        for (var t : forecastTemps) {
            println(t);
        }
        
    }

    public class LocalizedWeatherInfo {
        private LocationDto loc;
        private WeatherInfoForecastDto weatherInfo;

        public LocalizedWeatherInfo(LocationDto loc, WeatherInfoForecastDto weatherInfo) {
            this.loc = loc;
            this.weatherInfo = weatherInfo;
        }

        @Override
        public String toString() {
            return loc.toString() + EOL + weatherInfo.toString();
        }
    }
    
    @Test
    public void get_forecast_weather_for_locals_named_lisbon() {
        
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        Iterable<LocalizedWeatherInfo> forecasts =
                flatMap(
                    webApi.search("Lisboa"),
                    loc -> map(
                            webApi.forecastWeatherAt(loc.getLat(), loc.getLon()),
                            wi -> new LocalizedWeatherInfo(loc, wi)
                    )
                );
        // to complete

        for(var wif : forecasts) {
            println(wif);
        }
    }
    
}
