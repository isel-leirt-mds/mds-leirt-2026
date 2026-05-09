package pt.isel.mds.sequences;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An interface to produce sequences
 * with a functional iteration (tryAdvance) similar
 * to Spliterator of Stream
 * @param <T>
 */
public interface Sequence<T> {
    boolean tryAdvance(Consumer<T> action);

    /**
     * create an empty Sequence
     * @return
     * @param <T>
     */
    static <T> Sequence<T> empty() {
       return action -> false;
    }

    /**
     * Create a Sequence from an Iterable
     * @param src
     * @return
     * @param <T>
     */
    static <T> Sequence<T> from(Iterable<T> src) {
        var srcIt = src.iterator();
        return action -> {
            if (!srcIt.hasNext()) return false;
            action.accept(srcIt.next());
            return true;
        };
    }

    /**
     * transform the sequence into a different one
     * with equal size
     * @param mapper
     * @return
     * @param <U>
     */
    default <U> Sequence<U> map(Function<T,U> mapper) {
        // TODO
        return null;
    }

    /**
     * Produce a new Sequence with the elemmnets of
     * original Sequence that pass the Predicate "pred"
     * @param pred
     * @return
     */
    default Sequence<T> filter(Predicate<T> pred) {
        return action -> {
            boolean[] done = {false};
            while(tryAdvance(t -> {
                if (pred.test(t)) {
                    action.accept(t);
                    done[0] = true;
                }
            })  && !done[0]);
            return done[0];
        };
    }

    /**
     * Create a new Sequence from the concatenation of this and other Sequences
     * @param other
     * @return
     */
    default Sequence<T> concat(Sequence<T> other) {
        return action -> tryAdvance(action) || other.tryAdvance(action);
    }

    /**
     * produce a list from Sequence this
     * @return
     */
    default List<T> toList() {
        List<T> res = new ArrayList<>();

        while(tryAdvance( t -> res.add(t)));
        return res;
    }
}
