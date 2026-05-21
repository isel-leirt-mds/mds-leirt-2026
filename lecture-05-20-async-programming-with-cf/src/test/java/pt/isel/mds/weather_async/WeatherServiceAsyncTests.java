package pt.isel.mds.weather_async;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mds.weather_async.utils.ThreadUtils.sleep;


public class WeatherServiceAsyncTests {

	@Test
	public void get_locations_named_lisbon() {
		
		var service =
			new OpenWeatherServiceAsync(
				new OpenWeatherWebApiAsync()
			);
		
		var locations =
			service.search("Lisboa")
			.thenApply( locs -> {
				locs.forEach(System.out::println);
				return locs;
			});

		sleep(2000);
		
	}


	@Test
	public void getForecastForLisbonTest() {
//
//		OpenWeatherService service =
//			new OpenWeatherService(
//				new OpenWeatherWebApi( ));
//
//		// TODO
//		Stream<DayInfo> forecastWeather =
//			service.search("Lisbon")
//		   	.filter(l -> l.getCountry().equals("PT"))
//			.flatMap(l -> l.forecast());
//
//		List<DayInfo>  forecastList = forecastWeather.toList();
//
//		long nDays = forecastList.size();
//		assertEquals(6, nDays);
//
//		System.out.println("DayInfo list size: " + nDays);
//		forecastList.forEach(System.out::println);


		var service = new OpenWeatherServiceAsync(
				new OpenWeatherWebApiAsync()
		);

		var forecastWeather = service.search("Lisbon")
				.thenApply(locs -> locs.filter( l ->
						l.getCountry().equals("PT"))
				)
				.thenCompose(locs ->
					locs.findFirst().get().forecast()
				)
				.join();

		forecastWeather.forEach(System.out::println);

	}

	@Test
	public void getForecastDetailForTomorrowAtLisbonTest() {
//		var service =
//			new OpenWeatherService(new OpenWeatherWebApi(new HttpRequest()));
//
//		// TODO
//		List<WeatherInfo> tomorrowTemps =
//			service.search("Lisboa")
//				   .filter(l -> l.getCountry().equals("PT"))
//				   .flatMap(l -> l.forecast())
//				   .filter(di -> di.getDate().equals(LocalDate.now().plusDays(1)))
//				   .flatMap(di -> di.temperatures())
//				   .toList();
//
//		assertEquals(8, tomorrowTemps.size());
//		tomorrowTemps.forEach(System.out::println);
		var service = new OpenWeatherServiceAsync(
				new OpenWeatherWebApiAsync()
		);

		var tomorrowTemps =
				service.search("Lisbon")
				.thenApply(locs -> locs.filter(l -> l.getCountry().equals("PT"))
									.findFirst()
									.get()
				)
				.thenCompose( loc ->
						loc.forecast()
						.thenApply(f ->
							f.filter(di ->
								di.getDate().equals(LocalDate.now().plusDays(1))
							)
							.findFirst().get()
						)
				)
				.thenCompose( di -> di.temperatures())
				.join();

		tomorrowTemps.forEach(IO::println);
	}

}
