package pt.isel.mds.async;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.mds.weather_async.OpenWeatherWebApi;
import pt.isel.mds.weather_async.dto.WeatherInfoDto;
import pt.isel.mds.weather_async.requests.HttpRequest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mds.weather_async.utils.ThreadUtils.join;
import static pt.isel.mds.weather_async.utils.ThreadUtils.sleep;

public class ThreadsTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;

    private final static double PORTO_LAT  =  41.1494512;
    private final static double PORTO_LONG = -8.6107884;


    private static Logger logger =
            LoggerFactory.getLogger(ThreadsTests.class);

    @Test
    public void firstExampleWithThreads() {
        Thread t = new Thread(() -> {
            sleep(50);
            logger.info("Hello from new thread ");
        });

        t.start();

        //Thread.currentThread().join(t))
        join(t);
        logger.info("in Test thread");
    }

    private List<WeatherInfoDto> getWeatherFromLisbonAndPortoInParallel0() {
        var webApi = new OpenWeatherWebApi(new HttpRequest());
        var citiesWeather = new LinkedList<WeatherInfoDto>();
        var mutex = new ReentrantLock();

        var tLisbon = new Thread(() -> {
            var res = webApi.weatherAt(LISBON_LAT, LISBON_LONG);
            mutex.lock();
            try {
                citiesWeather.add(res);
            }
            finally {
                mutex.unlock();
            }

        });
        tLisbon.start();

        var tPorto = new Thread(() -> {
            var res = webApi.weatherAt(PORTO_LAT, PORTO_LONG);
            mutex.lock();
            try {
                citiesWeather.add(res);
            }
            finally {
                mutex.unlock();
            }

        });
        tPorto.start();

        join(tLisbon);
        join(tPorto);

        return citiesWeather;
    }

    private List<WeatherInfoDto> getWeatherFromLisbonAndPortoInParallel() {
        var webApi = new OpenWeatherWebApi(new HttpRequest());

        WeatherInfoDto[] wi = new WeatherInfoDto[2];

        var tLisbon = new Thread(() -> {
            wi[0] = webApi.weatherAt(LISBON_LAT, LISBON_LONG);
        });
        tLisbon.start();

        var tPorto = new Thread(() -> {
            wi[1] = webApi.weatherAt(PORTO_LAT, PORTO_LONG);
        });
        tPorto.start();

        join(tLisbon);
        join(tPorto);

        return Arrays.asList(wi);
    }


    @Test
    public void get_weather_at_lisbon_oporto_in_parallel_using_threads0() {
        var weather =
                getWeatherFromLisbonAndPortoInParallel0();
        assertEquals(2, weather.size());

        for(var wi : weather) {
            System.out.println(wi);
        }
    }

    @Test
    public void get_weather_at_lisbon_oporto_in_parallel_using_threads() {
        var weather =
                getWeatherFromLisbonAndPortoInParallel();
        assertEquals(2, weather.size());

        for(var wi : weather) {
            System.out.println(wi);
        }
    }


}
