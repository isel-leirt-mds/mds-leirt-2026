package pt.isel.mds.weather2.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
