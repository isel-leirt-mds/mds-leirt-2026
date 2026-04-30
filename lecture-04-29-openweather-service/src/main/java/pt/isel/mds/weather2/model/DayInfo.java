package pt.isel.mds.weather2.model;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;

public class DayInfo {
	private LocalDate date;
	private double maxTempC;
	private double minTempC ;
	private String description;

	private   Supplier<Iterable<WeatherInfo>> temperatures;

	public DayInfo(LocalDate date, double maxTempC,
				   double minTempC, String description,
				   Supplier<Iterable<WeatherInfo>> temperatures) {
		this.date = date;
		this.maxTempC = maxTempC;
		this.minTempC = minTempC;
		this.description = description;
		this.temperatures = temperatures;
	
	}

	// accessors
	public LocalDate getDate()      { return date; }
	public double getMaxTemp()      { return maxTempC; }
	public double getMinTemp()      { return minTempC; }
	public String getDescription()  { return description; }

	public Iterable<WeatherInfo> temperatures() {
		// TO CHANGE
		return temperatures.get();
	}
	
	@Override
	public String toString() {
		return "{"
			+ date
			+ ", "				+ description
			+ ", min="          + minTempC
			+ ", max="          + maxTempC
			+ "}";
	}
	
	@Override
	public int hashCode() {
		return getDate().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != DayInfo.class) return false;
		var other = (DayInfo) obj;
		var result = getDate().equals(other.getDate());
		return result;
	}
}
