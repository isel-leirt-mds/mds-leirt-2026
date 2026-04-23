package pt.isel.mds.weather1.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
