package pt.isel.mds.weather1.queries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class FlatmapIterator<T,U> implements Iterator<U>    {

    private final Function<T, Iterable<U>> mapper;
    private final Iterator<T> srcIt;
    private Iterator<U> currIt;
    private Optional<U> currValue = Optional.empty();
    private boolean done = false;

    public FlatmapIterator(Iterable<T> src, Function<T,Iterable<U>> mapper) {
        this.mapper = mapper;
        this.srcIt = src.iterator();
    }

    @Override
    public boolean hasNext() {
        if (done) return false;
        if (currValue.isPresent()) return true;
        while (currIt == null || !currIt.hasNext()) {
            if (!srcIt.hasNext()) {
                done = true;
                return false;
            }
            currIt = mapper.apply(srcIt.next()).iterator();
        }
        currValue = Optional.of(currIt.next());
        return true;

    }

    @Override
    public U next() {
        if (!hasNext()) throw new NoSuchElementException();
        var curr = currValue.get();
        currValue = Optional.empty();
        return curr;
    }
}
