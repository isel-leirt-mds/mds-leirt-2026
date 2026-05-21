package pt.isel.mds.weather_async.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
