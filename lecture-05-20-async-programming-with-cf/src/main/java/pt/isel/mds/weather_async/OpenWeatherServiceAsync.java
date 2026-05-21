package pt.isel.mds.weather_async;


import pt.isel.mds.weather_async.dto.LocationDto;
import pt.isel.mds.weather_async.dto.WeatherInfoForecastDto;
import pt.isel.mds.weather_async.model.async.*;


import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


public class OpenWeatherServiceAsync {
	private  OpenWeatherWebApiAsync api;

	public OpenWeatherServiceAsync(OpenWeatherWebApiAsync api) {
		this.api = api;
	}


	/**
	 *
	 * @param placeName
	 * @return
	 */
	public CompletableFuture<Stream<Location>> search(String placeName) {
//		return api.search(placeName).stream()
//				.map(this::dtoToLocation);

		return api.search(placeName)
				.thenApply(list ->
					list.stream().map(this::dtoToLocation)
				);

}

	
	private CompletableFuture<Stream<DayInfo>> forecastAt(Location loc) {
//		return 	api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()).stream()
//				.map(dto -> dtoToDayInfo(dto, loc))
//				.distinct();

		return api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude())
				.thenApply(dtoList ->
							dtoList.stream()
						   	.map(dto -> dtoToDayInfo(dto, loc))
							.distinct()
				);
	}

	private CompletableFuture<Stream<WeatherInfo>> weatherDetail(Double lat,
											  Double lon,
											  LocalDate day) {
//		 return api.forecastWeatherAt(lon, lat).stream()
//				.filter(dto -> dto.dateTime().toLocalDate().equals(day))
//				.map(this::dtoToWeatherInfo);

		return api.forecastWeatherAt(lon, lat)
				.thenApply(dtoList ->
					dtoList.stream()
					.filter(dto -> dto.dateTime().toLocalDate().equals(day))
					.map(this::dtoToWeatherInfo)
				);
			 
	}

	private CompletableFuture<Stream<WeatherInfo>> weatherDetail(Location loc, DayInfo di) {
		return weatherDetail(loc.getLatitude(), loc.getLongitude(), di.getDate());
	}
	
	private  Location dtoToLocation(LocationDto dto) {
		return  new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			(Location l) -> forecastAt(l)
		);
	}

	private  WeatherInfo dtoToWeatherInfo(WeatherInfoForecastDto dto) {
		return new WeatherInfo(
			dto.dateTime(),
			dto.temp(),
			dto.description(),
			dto.humidity(),
			dto.feelsLike());
	}


	public DayInfo dtoToDayInfo(WeatherInfoForecastDto dto, Location loc) {
		return new DayInfo(
			dto.dateTime().toLocalDate(),
			dto.maxTemp(),
			dto.minTemp(),
			dto.description(),
			di -> weatherDetail(loc, di)
		);
	}
}
