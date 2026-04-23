package pt.isel.mds.weather1.dto;

import static pt.isel.mds.weather1.utils.PrintUtils.EOL;

public class WeatherInfoDto extends WeatherInfoBaseDto {
    private WeatherInfoSysDto sys;
    private String name;
    private  int timezone;

    public String local() {
        return name;
    }

    @Override
    public String toString() {
        return "{" + EOL
                + "\tdateTime = " + dateTime() + EOL
                + "\tdescription = " + description() + EOL
                + "\tweather = " + weather() + EOL
                + "\tname = " + local() + EOL
                + "\tcountry = " + sys.getCountry() + EOL
                + "\ttimezone = " + timezone + EOL
                + "\tsunrise = " + sys.getSunrise() + EOL
                + "\tsunset = " + sys.getSunset() + EOL
                + "}";
    }
}
