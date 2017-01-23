package ua.com.blacklion.classes;

import org.apache.log4j.Logger;
import ua.com.blacklion.interfaces.TestMap;

import java.lang.reflect.Array;

public class LongMap<V> implements TestMap<V> {
    private Logger logger = Logger.getLogger(this.getClass());
    private Class<?> kind;

    @Override
    public V put(long key, V value) {
        return null;
    }

    @Override
    public V get(long key) {
        return null;
    }

    @Override
    public V remove(long key) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(long key) {
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        return false;
    }

    @Override
    public long[] keys() {
        return new long[0];
    }

    @Override
    public V[] values() {
        return (V[]) Array.newInstance(kind);
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    private class InnerPair<V>{
        private Long key;
        private V value;
    }
}
