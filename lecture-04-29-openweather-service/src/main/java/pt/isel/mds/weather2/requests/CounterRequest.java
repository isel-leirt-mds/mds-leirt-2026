package pt.isel.mds.weather2.requests;

import java.io.Reader;

import static pt.isel.mds.weather2.resources.BuildUtils.TODO;

public class CounterRequest implements Request {

    public CounterRequest(Request origReq) {
        TODO("CounterRequest");
    }

    @Override
    public Reader get(String path) {
        TODO("CounterRequest get");
        return null;
    }

    public int getCount() {
        TODO("CounterRequest getCount");
        return 0;
    }
}
