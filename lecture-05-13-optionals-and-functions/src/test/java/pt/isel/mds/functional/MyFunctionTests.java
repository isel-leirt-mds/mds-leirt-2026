package pt.isel.mds.functional;

import pt.isel.mds.functional.MyFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyFunctionTests {

    private static int sinc(int i) {
        return i + 1;
    }

    public static int striple(int i) {
        return 3 * i;
    }

    @Test
    public void andThenAndComposeTest() {
        MyFunction<Integer, Integer> inc = MyFunctionTests::sinc;
        MyFunction<Integer, Integer> triple = MyFunctionTests::striple;

        MyFunction<Integer, Integer> fa =
            inc.compose(triple);

        MyFunction<Integer, Integer> fb =
            inc.andThen(triple);

        assertEquals(10 , inc.compose(triple).apply(3));
        assertEquals(12 , inc.andThen(triple).apply(3));
    }
}
