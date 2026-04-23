package queries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {
    private final Iterator<T> srcIt;
    private final Predicate<T> pred;

    private Optional<T> next = Optional.empty();


    public FilterIterator(Iterable<T> src,
                       Predicate<T> pred) {
        this.srcIt = src.iterator();
        this.pred = pred;
    }

    @Override
    public boolean hasNext() {
         if (next.isPresent()) return true;
          while (srcIt.hasNext()) {
             var n = srcIt.next();
             if (pred.test(n)) {
                 next = Optional.of(n);
                 return true;
             }
          }
          return  false;
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        T curr = next.get();
        next = Optional.empty();
        return curr;
    }
}
