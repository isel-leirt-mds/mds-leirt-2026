package pt.isel.mds.streams;

import org.junit.jupiter.api.Test;
import pt.isel.mds.utils.PrimeUtils;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamTests {

    @Test
    public void getPrimesTill_1_000_000() {

        var primes =
                LongStream.range(2, 1_000_000)
                        .mapToObj(l -> l)
                        .filter(PrimeUtils::isPrime);

        long startTime = System.currentTimeMillis();

        var primesList =
                // the parallel operation speedup the
                // stream consume by using all cores
                // in the process
                primes
                        .parallel()
                    .toList();

        System.out.println("done in " + (System.currentTimeMillis()-startTime) + "ms!");

        System.out.println("number of primes till 10_000_000: " + primesList.size());
        for(var p : primesList) {
            System.out.println(p);
        }

    }

//    class Pair<T,U> {
//        public final T first;
//        public final U second;
//
//        public Pair(T first, U second) {
//            this.first = first;
//            this.second = second;
//        }
//
//        @Override
//        public String toString()  {
//            return String.format("{%s, %s}", first, second);
//        }
//    }

    record Pair<T,U>(T first, T second) {};
    record IntPair(int first, int second) {};

    @Test
    public void produceCombinationsPairsOfIntsBteween1And10() {
       var combs =
               IntStream.rangeClosed(1, 10)
               .mapToObj(n1 ->
                       IntStream.rangeClosed(n1+1, 10)
                       .mapToObj(n2 -> new Pair(n1, n2)))
               .flatMap(p -> p);


        var combsList = combs.toList();

        for(var p : combsList) {
            System.out.println(p);
        }
        System.out.println("combs number = " + combsList.size());
    }
}
