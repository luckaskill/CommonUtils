package com.las.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T, C extends Collection<T>> Collection<T> fromIterable(Iterable<T> iterable, Supplier<C> collectionCreator) {
        C collection = collectionCreator.get();
        for (T t : iterable) {
            collection.add(t);
        }
        return collection;
    }

    public static <T, C extends Collection<T>> Collection<T> fromIterable(Iterable<T> iterable) {
        return fromIterable(iterable, ArrayList::new);
    }
}
