package pt.isel.mds.weather1.queries;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import static java.lang.IO.println;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pt.isel.mds.weather1.queries.Queries.*;


public class QueryTests {

    @Test
    public void simpleOfTest() {
        var names = toList(
                of("joseph", "anthony", "sara")
        );
        var expected = List.of("joseph", "anthony", "sara");

        assertEquals(expected, names);
    }
    @Test
    public void simpleLimitTests() {
        var nums = List.of(2, 4, 5);
        var expected1 = nums;

        var limited = toList (
                limit(nums, 4)
        );

        assertEquals(expected1, limited);

        var expected2 = List.of(2, 4);
        var limited2 = toList (
                limit(nums, 2)
        );

        assertEquals(expected2, limited2);
    }

    @Test
    public void simpleIterate0Test() {
        var nums = iterate(1, v -> v +2);
        for(var n : nums) {
            println(n);
        }
    }

    @Test
    public void simpleIterateTest() {
        var expected = List.of(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);

        var firstTenOddNumbers =
            toList(
                limit(
                    iterate(1, v -> v +2),
                    10
                )
            );

        assertEquals(expected, firstTenOddNumbers);
    }

    @Test
    public void generateTest() {
        int[] next = {2};

        Supplier<Integer> si = () -> {
            int curr = next[0];
            next[0] += 2;
            return curr;
        };
        var nrs =
                limit(
                    generate(si), 10);

        var expected = List.of(2, 4, 6, 8, 10, 12, 14, 16, 18, 20);
        assertEquals(expected, toList(nrs));
    }

    @Test
    public void filterAllWordWithSizeGreaterThan4() {
        var words = List.of("hello", "how" , "do", "you", "feel", "today");
        var expected = List.of("hello", "today");

        var longWords = toList(
                filter(words, n -> n.length() > 4)
        );

        assertEquals(expected, longWords);
    }

    @Test
    public void mapLazyTest() {
        var nums = List.of(1,2,3,4);

        var squares = map(nums, n -> n*n);
        var it = squares.iterator();
    }

    @Test
    public void mapConvertStringToSizeTest() {
        var names = List.of("Joana", "Johnny", "Rute");
        var expected = List.of(5, 6, 4);

        var result = toList (
                map(names, n -> n.length())
        );
        assertEquals(expected, result);
    }

    @Test
    public void simpleSkipTest() {
        var primes = List.of(2, 3, 5, 7, 9, 11, 13);
        var skippedPrimes = toList(
                skip(primes, 3)
        );

        var expected = List.of(7, 9, 11, 13);
        assertEquals(expected, skippedPrimes);
    }

    @Test
    public void simpleSkipWhileTest() {
        var primes = List.of(2, 3, 5, 7, 9, 11, 13);
        var skippedPrimes = toList(
                skipWhile(primes, n -> n <= 7)
        );

        var expected = List.of(9, 11, 13);
        assertEquals(expected, skippedPrimes);
    }

    @Test
    public void testCache() {
        Random r = new Random();
        var nrs = generate(() -> r.nextInt(100));
        nrs = cache(nrs);

        var expected = toList(
                limit(nrs, 10)
        );
        var actual = toList(
                limit(nrs, 10)
        );
        assertEquals(expected, actual);
    }

    private static class IntPair {
        public final int i1, i2;

        public IntPair(int i1, int i2) {
            this.i1 = i1; this.i2 = i2;
        }

        public String toString() {
            return String.format("(%d,%d)", i1, i2);
        }
    }


    private List<Character> chars(String s) {
        var l = new ArrayList<Character>();
        for(int i=0; i< s.length(); ++i)
            l.add(s.charAt(i));
        return l;
    }
/*
    private int countOccurrences(
            List<List<String>> words,
            char letter
    ) {
        return
                count(
                        filter(
                                flatMap(
                                        flatMap(words, l -> l),
                                        s -> chars(s)
                                ),
                                c -> c == letter
                        )
                );
    }


    @Test
    public void getAllCombinationsOf2IntsBetween1And10() {
        var res = flatMap(
                range(1, 10),
                i1 ->
                    map(
                            range(i1+1, 10),
                            i2 -> new IntPair(i1, i2)
                    )
        );

        for(var c : res)
            System.out.println(c);
    }

    @Test
    public void countOcurrencesTest() {
        List<List<String>> data = List.of(
                List.of( "abc" , "bd"),
                List.of(),
                List.of("123a", "bda")
        );

        int expected = 3;

        int res = countOccurrences(data, 'b');

        assertEquals(expected, res);
    }


    @Test
    public void testCache2() {

        Iterable<Integer> nrs = limit(iterate(1, n -> n +1), 10);
        nrs = cache(nrs);

        var itA = nrs.iterator();
        var itB = nrs.iterator();

        int ia1 = itA.next();
        int ib1 = itB.next();
        int ib2 = itB.next();
        int ia2 = itA.next();

        assertEquals(ia1, ib1);
        assertEquals(ia2, ib2);
        assertEquals(1, ia1);
        assertEquals(2, ia2);
    }

    @Test
    public void testReduce() {
        var nums = List.of(1, 2, 5, 8);
        var expected = 16 ; // sum of all lements

        var res = reduce(nums, (a,n) -> a + n);

        assertTrue(res.isPresent());
        assertEquals(expected, res.get());
    }

    @Test
    public void testMax() {
        Random r = new Random();
        Iterable<Integer> nrs =
                limit(
                     generate(() -> r.nextInt(100)),
                     10
                );

        var lst = toList(nrs);
        Optional<Integer> m = max(lst);
        lst.sort(Integer::compare);


        m.ifPresent(out::println);
        assertTrue(m.isPresent());
        assertEquals(lst.get(lst.size() - 1), m.get());
    }

    @Test
    public void testLast() {
        var names = List.of("mary", "john", "artur");

        var expected = "artur";
        var res = last(names);
        assertTrue(res.isPresent());
        assertEquals(expected, res.get());
    }

 */
}
