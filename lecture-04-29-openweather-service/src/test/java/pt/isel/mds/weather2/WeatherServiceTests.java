package pt.isel.mds.weather2;

import org.junit.jupiter.api.Test;
import pt.isel.mds.weather2.model.*;
import pt.isel.mds.weather2.requests.CounterRequest;
import pt.isel.mds.weather2.requests.HttpRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mds.weather2.queries.Queries.*;


public class WeatherServiceTests {

	@Test
	public void get_locations_named_lisbon() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(req)
			);
		
		var locations =
			service.search("Lisboa");
 		assertEquals(0, req.getCount());

		for(var loc : locations) {
			System.out.println(loc);
		}
		assertEquals(1, req.getCount());
		
	}

	@Test
	public void getForecastForLisbonTest() {
		CounterRequest req = new CounterRequest(new HttpRequest());
		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(req));


		var forecastWeather =
			 flatMap(
					 filter(service.search("Lisbon"),
							 l -> l.getCountry().equals("PT")
					 ),
					 l -> l.forecast()
			 );

	 	assertEquals(0, req.getCount());
		long nDays = count(forecastWeather);
		assertEquals(2, req.getCount());
		assertEquals(6, nDays);

		System.out.println("DayInfo list size: " + nDays);
		for(var fw : forecastWeather) {
			System.out.println(fw);
		}
	}

}
