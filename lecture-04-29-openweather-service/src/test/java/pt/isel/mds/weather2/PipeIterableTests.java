package pt.isel.mds.weather2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.IO.println;
import static pt.isel.mds.weather2.queries.PipeIterable.of;

public class PipeIterableTests {

    @Test
    public void checkPipeMapAndFilterTest() {
        var names = List.of("CArlos", "Ana", "Mourinho");

        var res = of(names)
                .filter(n -> n.length() > 6)
                .map(n -> n.length());

        for (var v : res) {
            println(v);
        }
    }
}
