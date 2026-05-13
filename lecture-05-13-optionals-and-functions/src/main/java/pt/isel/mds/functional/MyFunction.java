package pt.isel.mds.functional;

import java.util.function.Function;

import static pt.isel.mds.utils.Utils.TODO;

public interface MyFunction<T,R> {
    R apply(T t);

    default <V> MyFunction<V,R> compose(MyFunction<V,T> before) {
//         TODO("compose(Function<V,T> before)");
//         return null;
        return v -> apply(before.apply(v));
    }

    default <V> MyFunction<T,V> andThen(MyFunction<R,V> after) {
//        TODO("andThen(Function<R,V> after)");
//        return null;
        return t -> after.apply(apply(t));
    }
}
