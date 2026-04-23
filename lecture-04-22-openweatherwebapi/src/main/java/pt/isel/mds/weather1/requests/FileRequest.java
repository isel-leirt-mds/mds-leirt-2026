package pt.isel.mds.weather1.requests;

import pt.isel.mds.weather1.resources.ResourceUtils;

import java.io.Reader;

public class FileRequest implements Request{
	
	@Override
	public Reader get(String path) {
		return ResourceUtils.getFromCache(path);
	}
	
}
