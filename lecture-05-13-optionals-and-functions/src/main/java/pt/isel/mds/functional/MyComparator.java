package pt.isel.mds.functional;

import java.util.function.Function;

import static pt.isel.mds.utils.Utils.TODO;

@FunctionalInterface
public interface MyComparator<T> {
    int compare(T t1, T t2);
    
    static <T,U extends Comparable<U>> MyComparator<T> comparing(Function<T,U> key) {
//        TODO("MyComparator.comparing(Function key)");
//        return null;
        return (t1, t2) ->
                    key.apply(t1).compareTo(key.apply(t2));

    }
    
    static <T, U> MyComparator<T> comparing(
        Function<T, U> key,
        MyComparator<U> keyComparator)
    {
        TODO("MyComparator.comparing(Function key, MyComparator keyComparator)");
        return null;
    }


    /**
     * return a comparator that comapares using this, and if the values are equals,
     * compare the comparable values produced by key function
     * @param key
     * @return
     */
    default <U extends Comparable<U>> MyComparator<T> thenComparing(Function<T,U> key) {
        TODO("MyComparator.thenComparing(Function key)");
        return null;
    }
   
    
    /**
     * Returns a comparator that imposes the reverse ordering of this comparator.
     */
    default MyComparator<T> reversed() {
        TODO("MyComparator.reversed()");
        return null;
    }

    /**
     * return a comparator that comapares using this, and if the values are equals,
     * compare them with "other" comparator
     * @param other
     * @return
     */
    default MyComparator<T> thenComparing(MyComparator<T> other) {
        TODO("thenComparing(MyComparator<T> other))");
        return  null;
    }

    /**
     * returns a comparator where nulls are lower than everything else
     * @param comparator
     * @return
     * @param <T>
     */
    static <T> MyComparator<T> nullsFirst(MyComparator<T> comparator) {
        TODO("nullsFirst(MyComparator<T> other))");
        return null;
    }
}
