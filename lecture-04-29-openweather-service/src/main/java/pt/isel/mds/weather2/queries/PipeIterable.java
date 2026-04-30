package pt.isel.mds.weather2.queries;

import pt.isel.mds.weather2.queries.iterators.FilterIterator;
import pt.isel.mds.weather2.queries.iterators.MapIterator;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An alternative to class Queries that support a "pipeline" of
 * operations, simplifying the sintax
 * Ex: src
 *     .filter(...)
 *     .map(...)
 *
 * versus
 *     map (
 *        filter (
 *          src,
 *          ...
 *        ),
 *        ....
 *     )
 * @param <T>
 */
public interface PipeIterable<T> extends Iterable<T> {

    /**
     * A factory to produce a PipeIterable from an Iterable
     * @param src
     * @return
     * @param <T>
     */
    static <T> PipeIterable<T> of(Iterable<T> src) {
        return () -> src.iterator();
    }

    default <U> PipeIterable<U> map(Function<T, U> mapper) {
        return () ->  new MapIterator<>(this, mapper);
    }

    default PipeIterable<T> filter(Predicate<T> pred) {
        return () ->  new FilterIterator<>(this, pred);
    }
}
