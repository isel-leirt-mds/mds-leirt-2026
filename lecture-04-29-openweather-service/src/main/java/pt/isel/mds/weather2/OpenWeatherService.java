package pt.isel.mds.weather2;

import pt.isel.mds.weather2.dto.*;
import pt.isel.mds.weather2.model.*;


import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Supplier;

import static pt.isel.mds.weather2.queries.Queries.*;


public class OpenWeatherService {
	private  OpenWeatherWebApi api;

	public OpenWeatherService(OpenWeatherWebApi api) {
		this.api = api;
	}

	/**
	 *
	 * @param placeName
	 * @return
	 */
	public Iterable<Location> search(String placeName) {
		// CHANGED TO TURN LAZY

		return
				() -> map(
						api.search(placeName) ,
						dto -> dtoToLocation(dto)
						).iterator();
	}


	public Iterable<DayInfo> forecastAt(Location loc) {
		// CHANGE TO TURN LAZY
		return
			distinct(
				map(
					api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()),
					dto -> dtoToDayInfo(dto, loc)
				),
				Comparator.comparing(DayInfo::getDate)
			);
	}
	
	private Iterable<WeatherInfo> weatherDetail(Location loc, LocalDate date) {
		// CHANGE TO TURN LAZY
		return
			map(
				filter(api.forecastWeatherAt(loc.getLatitude(), loc.getLongitude()),
						dto -> dto.dateTime().toLocalDate().equals(date)
				),
				this::dtoToWeatherInfo
			);
	}

	private Iterable<WeatherInfo> weatherDetail(Location loc, DayInfo di) {
	 	return weatherDetail(loc, di.getDate());
	}
	
	private  Location dtoToLocation(LocationDto dto) {
		return  new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			loc -> forecastAt(loc)
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
			// changed to a supplier in order to avoid
			// calling web api on DayInfo creation
			() -> weatherDetail(loc, dto.dateTime().toLocalDate())
		);
	}
}
