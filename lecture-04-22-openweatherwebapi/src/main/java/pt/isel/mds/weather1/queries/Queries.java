package pt.isel.mds.weather1.queries;

import pt.isel.mds.weather1.queries.iterators.*;


import java.util.*;
import java.util.function.*;

import static java.lang.IO.println;

public class Queries {

    // factory operations

    /**
     * "iterate" produce an infinite sequence given a seed and a function
     * (UnaryOperator) that produce next value
     * @param seed
     * @param oper
     * @return
     * @param <T>
     */
    public static <T> Iterable<T>
    iterate(T seed, UnaryOperator<T> oper ) {


        // the next line returns an implementation of Iterable<T>, as desired,
        // and equivalent to the commented code below:
        //        Iterable<T> it =  new Iterable<T>() {
        //            public Iterator<T> iterator() {
        //                return new Iterator<T> {
        //                      ....
        //                };
        //            }
        //        };

        return () ->  new Iterator<T>() {
            T current = seed;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                var next = current;
                current = oper.apply(current);
                return next;
            }
        };
    }

    /**
     * "generate" produce an infinite sequence given a supplier that produce next value
     * @param supplier
     * @return
     * @param <T>
     */
    public static <T> Iterable<T>  generate(Supplier<T> supplier) {
         return  () -> new Iterator<T>() {
             @Override
             public boolean hasNext() {
                 return true;
             }

             @Override
             public T next() {
                 return supplier.get();
             }
         };
    }

    /**
     * returns an Iterable lazy with the "values" received
     * @param values
     * @return
     * @param <T>
     */
    public static <T> Iterable<T> of(T... values) {
        // to implement

        return null;
    }

    public static Iterable<Integer> range(int start, int end) {
        return  () -> new Iterator<Integer>() {
            int next = start;
            @Override
            public boolean hasNext() {
                return next <= end;
            }

            @Override
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                var curr = next;
                next++;
                return curr;

            }
        };
    }

    // intermediate operations

    /**
     * A lazy "filter" implementation
     * @param src
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred) {
        return () -> {
            return new FilterIterator<>(src, pred);
        };
    }

    /**
     * A lazy "map" implementation
     * @param src
     * @param mapper
     * @return
     * @param <T>
     * @param <U>
     */
    public static <T,U>
    Iterable<U> map(Iterable<T> src, Function<T, U> mapper) {
        return () -> {
            return new MapIterator<>(src, mapper);
        };
    }

    public static <T,U> Iterable<U>
    flatMap( Iterable<T> src, Function<T, Iterable<U>> mapper) {
        return () -> new FlatmapIterator(src, mapper);
    }

    /**
     * "limit" operation, useful to work with infinite sequences,
     * "trunc" a sequence to at most "n" elements
     * @param src
     * @param n
     * @return
     * @param <T>
     */
    public static <T> Iterable<T>
    limit(Iterable<T> src, int n) {
        return () -> new Iterator<T>() {
            int remaining = n;
            Iterator<T> srcIt = src.iterator();

            @Override
            public boolean hasNext() {
                return remaining > 0 && srcIt.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                remaining--;
                return srcIt.next();
            }
        };
    }

    /**
     * returns a new Iterable that results from removing the first "n" elements of the original sequence
     * @param src
     * @param n
     * @return
     * @param <T>
     */
    public static <T> Iterable<T>
    skip(Iterable<T> src, int n) {
        // to implement
        return null;
    }

    /**
     * returns a new Iterable that results from removing the first elements of the original sequence
     * that "pass" Predicate "pred"
     * @param src
     * @param pred
     * @return
     * @param <T>
     */
    public static <T> Iterable<T>
    skipWhile(Iterable<T> src, Predicate<T> pred) {
        // to implement
        return null;
    }



    public static <T> Iterable<T> cache(Iterable<T> src) {
        //to implement
        return null;
    }

    // terminal operations

    /**
     * "toList" returns a list os the elements of the received Iterable
     * @param src
     * @return
     * @param <T>
     */
    public static <T> List<T> toList(Iterable<T> src) {
        var list = new ArrayList<T>();
        for (var t : src) {
            list.add(t);
        }
        return list;
    }

    /**
     * reduz a sequência a um único elemento obtido através da aplicação
     * da operação "accum" a todos os elementos da sequência "src"
     * No caso da sequência ser vazia é retornado um optional vazio
     * @param src
     * @return
     * @param <T>
     */
    public static <T> Optional<T>
    reduce(Iterable<T> src, BinaryOperator<T> accum) {
        //to implement
        return null;
    }


    /**
     * retorna um Optional com o maior elemento da sequência "src".
     * No caso da sequência ser vazia é retornado um optional vazio
     * @param src
     * @return
     * @param <T>
     */
    public static <T extends Comparable<T>> Optional<T> max(Iterable<T> src) {
        //to implement
        return null;
    }

    public static <T> Optional<T> max(Iterable<T> src, Comparator<T> cmp) {
        T result = null;
        for(var e : src) {
            if (result == null || cmp.compare(e, result) > 0) {
                result = e;
            }
        }
        return Optional.ofNullable(result);
    }

    /**
     * retorna um Optional com o último elemento da sequência "src".
     * No caso da sequência ser vazia é retornado um optional vazio
     * @param src
     * @return
     * @param <T>
     */
    public static <T extends Comparable<T>> Optional<T> last(Iterable<T> src) {
        //to implement
        return null;
    }

    public static <T> long count(Iterable<T> src) {
        long result = 0;
        for(var e : src) {
            result++;
        }
        return  result;
    }

}
