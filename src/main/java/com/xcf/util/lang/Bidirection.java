package com.xcf.util.lang;


import com.xcf.util.concurrent.AutoLock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 双向映射容器
 *
 * @param <K>
 * @param <V>
 */
public class Bidirection<K, V> {
    private final Map<K, V> kvMap;
    private final Map<V, K> vkMap;
    private final ReadWriteLock readWriteLock;

    public Bidirection() {
        this.kvMap = new HashMap<>();
        this.vkMap = new HashMap<>();
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    public List<K> getKeys() {
        try (AutoLock autoLock = new AutoLock(readWriteLock.readLock())) {
            return new ArrayList<>(kvMap.keySet());
        }
    }

    public List<V> getValues() {
        try (AutoLock autoLock = new AutoLock(readWriteLock.readLock())) {
            return new ArrayList<>(vkMap.keySet());
        }
    }

    public K getKey(V v) {
        try (AutoLock autoLock = new AutoLock(readWriteLock.readLock())) {
            return vkMap.get(v);
        }
    }

    public V getValue(K k) {
        try (AutoLock autoLock = new AutoLock(readWriteLock.readLock())) {
            return kvMap.get(k);
        }
    }


    public void put(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("Key | Value can't null");
        }
        try (AutoLock autoLock = new AutoLock(readWriteLock.writeLock())) {
            kvMap.put(k, v);
            vkMap.put(v, k);
        }
    }

    public Node<K, V> removeByValue(V v) {
        try (AutoLock autoLock = new AutoLock(readWriteLock.writeLock())) {
            K k = vkMap.remove(v);
            if (null != k) {
                kvMap.remove(k);
                return new Node<>(k, v);
            }
        }
        return null;
    }

    public Node<K, V> removeByKey(K k) {
        try (AutoLock autoLock = new AutoLock(readWriteLock.writeLock())) {
            V v = kvMap.remove(k);
            if (null != v) {
                vkMap.remove(v);
                return new Node<>(k, v);
            }
        }
        return null;
    }

    static class Node<KEY, VALUE> implements Map.Entry<KEY, VALUE> {

        final KEY key;
        VALUE value;

        public Node(KEY key, VALUE value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public KEY getKey() {
            return this.key;
        }

        @Override
        public VALUE getValue() {
            return this.value;
        }

        @Override
        public VALUE setValue(VALUE newValue) {
            VALUE oldValue = value;
            value = newValue;
            return oldValue;
        }
    }
}
