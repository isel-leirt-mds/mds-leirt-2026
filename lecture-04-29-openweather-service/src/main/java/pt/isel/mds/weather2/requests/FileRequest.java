package pt.isel.mds.weather2.requests;

import pt.isel.mds.weather2.requests.Request;
import pt.isel.mds.weather2.resources.ResourceUtils;

import java.io.Reader;

public class FileRequest implements Request {
	
	@Override
	public Reader get(String path) {
		return ResourceUtils.getFromCache(path);
	}
	
}
