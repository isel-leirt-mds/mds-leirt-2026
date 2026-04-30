package pt.isel.mds.weather2;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.Stream.*;

import static java.lang.IO.println;
import static pt.isel.mds.weather2.queries.PipeIterable.of;

public class StreamTests {

    @Test
    public void checkStreamMapAndFilterTest() {
        var names = List.of("Carlos", "Ana", "Mourinho").stream();
        var namesArray = new String[] {"Carlos", "Ana", "Mourinho" };

        var res1 = Arrays.stream(namesArray);

        var res = names
                .filter(n -> n.length() > 6)
                .map(n -> n.length())
                .toArray(n -> new String[n]);

        for (var n : res) {
            println(n);
        }
        //res.forEach(v -> println(v));

        println("count= " + res.length);

    }



}
