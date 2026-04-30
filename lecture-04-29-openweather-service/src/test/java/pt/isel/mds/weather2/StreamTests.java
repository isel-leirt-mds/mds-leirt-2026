package pt.isel.mds.weather2;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Stream.*;

import static java.lang.IO.println;
import static pt.isel.mds.weather2.queries.PipeIterable.of;

public class StreamTests {

    @Test
    public void checkStreamMapAndFilterTest() {
        var names = Stream.of("Carlos", "Ana", "Mourinho");

        var res = names
                .filter(n -> n.length() > 6)
                .map(n -> n.length());

        res.forEach(v -> println(v));

    }

}
