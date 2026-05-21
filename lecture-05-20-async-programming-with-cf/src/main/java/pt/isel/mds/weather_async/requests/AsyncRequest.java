package pt.isel.mds.weather_async.requests;
import java.io.Reader;
import java.util.concurrent.CompletableFuture;

public interface AsyncRequest {
    CompletableFuture<Reader>  get(String path);
}