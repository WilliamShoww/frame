package com.xcf.util.lang;

import java.util.HashMap;
import java.util.function.Function;


public class MyMap<K, V> extends HashMap<K, V> {

    public MyMap<K, V> put(K key, Function<V, V> function) {
        V v = get(key);
        V apply = function.apply(v);
        super.put(key, apply);
        return this;
    }

}
