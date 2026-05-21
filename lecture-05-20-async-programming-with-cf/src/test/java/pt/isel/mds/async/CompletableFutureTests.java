package pt.isel.mds.async;

import pt.isel.mds.weather_async.OpenWeatherWebApiAsync;
import pt.isel.mds.weather_async.dto.WeatherInfoDto;
import pt.isel.mds.weather_async.model.async.WeatherInfo;
import pt.isel.mds.weather_async.requests.HttpAsyncRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.lang.IO.println;
import static pt.isel.mds.weather_async.utils.ThreadUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompletableFutureTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;
    
    private final static double PORTO_LAT  =  41.1494512;
    private final static double PORTO_LONG = -8.6107884;
    
    
    private static Logger logger =
        LoggerFactory.getLogger(CompletableFutureTests.class);
    
    CompletableFuture<Integer> inc(int i) {
        return CompletableFuture.supplyAsync(() -> {
            //logger.info("inc start");
            sleep(2000);
            //logger.info("inc end");
            return i + 1;
        });
    }
    
    CompletableFuture<Integer> square(int i) {
        return CompletableFuture.supplyAsync(() -> {
            //logger.info("square start");
            sleep(3000);
            //logger.info("square end");
            return i * i;
        });
    }


    @Test
    public void fistUseOfCompletableFutures() {
        logger.info("start calculation");
        // launch a completable future...
        var f1 = CompletableFuture.supplyAsync(() -> {
            logger.info("supplier 1");
            sleep(2000);
            return 10;
        });

        // launch another completable future...
        var f2 = CompletableFuture.supplyAsync(() -> {
            logger.info("supplier 2");
            sleep(1000);
            return 20;
        });

        // and wait for both... ugh!
        var f1Res = f1.join();
        var f2Res = f2.join();
        logger.info("end calculation");

        assertEquals(10, f1Res);
        assertEquals(20, f2Res);
    }

    @Test
    public void useOfCompletableFuturesCombination() {
        // launch a completable future...
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return 10;
        });

        // launch another completable future...
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return 20;
        });

        // and combine them... ok!
        var fComb =
            f1.thenCombine(f2, (r1, r2) -> r1 + r2)
            .thenAccept(value -> logger.info("value = " + value));


        logger.info("start join!");
        // since we are in a test we must wait anyway...
        var res = fComb.join();

    }


    @Test
    public void get_weather_at_lisbon_oporto_in_parallel_using_completable_futures_combine() {
        var webApi = new OpenWeatherWebApiAsync(new HttpAsyncRequest());

        var weather =   webApi.weatherAt(LISBON_LAT, LISBON_LONG)
                        .thenCombine(webApi.weatherAt(PORTO_LAT, PORTO_LONG),
                                         (wl, wp) -> List.of(wl , wp)
                        )
                        .join();

        weather.forEach(IO::println);
    }

    @Test
    public void sequenceOfOperationsUsingFutures() {
        logger.info("start test!");
        var cf = inc(2)
                .thenApply(n -> {
                    logger.info("in  thenApply");
                    return "result= " + n;
                });

        logger.info(cf.join());

    }

    @Test
    public void sequenceOfAsyncOperationsUsingFutures() {
        logger.info("start test!");
        var cf = inc(2)
                .thenApply(n -> {
                    logger.info("in thenApply");
                    return square(n);
                })
                .thenCompose( cfRes -> cfRes);

        assertEquals(9, cf.join());
        logger.info("res = " + cf.join());
    }


    @Test
    public void get_weather_at_lisbon_oporto_in_serial_using_completable_futures() {
        var webApi = new OpenWeatherWebApiAsync(new HttpAsyncRequest());
        CompletableFuture<List<WeatherInfoDto>> result  =
                webApi.weatherAt(LISBON_LAT, LISBON_LONG)
                .thenCompose(wil ->
                        webApi.weatherAt(PORTO_LAT, PORTO_LONG)
                        .thenApply(wip -> List.of(wil, wip))
                );
    }

    private CompletableFuture<List<Integer>> squareAll(List<Integer> values) {
        List<CompletableFuture<Integer>> futList =
                values.stream()
                .map( i -> square(i))
                .toList();
        return CompletableFuture
                .allOf(futList.toArray(n -> new CompletableFuture[n]))
                .thenApply(__ ->
                         futList.stream()
                                .map(o -> o.join())
                                .toList()
                );

    }


    private  CompletableFuture<List<Integer>> squareAll2(List<Integer> values) {

        CompletableFuture<Stream<Integer>> initial =
                CompletableFuture.completedFuture(Stream.empty());
//                new CompletableFuture<Stream<Integer>>();
//        initial.complete(Stream.empty());


        var res =  values.stream()
                .map(i -> {
                    logger.info("i=" + i);
                    return square(i).thenApply(i1 -> Stream.of(i1));
                })
                .reduce(initial, (acc, cf) ->
                        acc.thenCombine(cf, (s1, s2 )-> Stream.concat(s1,s2))
                )
                .thenApply(s -> s.toList());

        return res;
    }

    @Test
    public void squareAll1() {
        var values = List.of(1, 2, 3, 4);

        var l = values.stream()
                .map( i -> square(i))
                .toList();

        l.stream().forEach(f -> System.out.println(f.join()));
    }

    @Test
    public void squareAllFinal() {
        logger.info("test start");
        var values = List.of(1,2,3,4);
        var expected = List.of(1,4,9,16);

        var futResult = squareAll2(values);
        assertEquals(expected, futResult.join());
        logger.info("test end");
    }
}
