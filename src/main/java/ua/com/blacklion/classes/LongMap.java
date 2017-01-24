package ua.com.blacklion.classes;

import org.apache.log4j.Logger;
import ua.com.blacklion.interfaces.TestMap;

import java.lang.reflect.Array;

public class LongMap<V> implements TestMap<V> {
    private Logger logger = Logger.getLogger(this.getClass());
    private Class<?> kind;
    private InnerPair [] table;
    private int maxKeyValue = 1000000;
    private int tableInitSize = 10;

    public LongMap() {
        table = (InnerPair[]) Array.newInstance(InnerPair.class, tableInitSize);
        logger.debug("Constructor has created. Table size = " + table.length);
    }

    @Override
    public V put(long key, V value) {
        logger.debug("Incoming data: key: " + key + " value: " + value);
        long k = keyCheck(key);
        int index = (int) k;
        tableSizeCheck(k);

        try {
            if (table[index].isDeleted()) {
                table[index] = new InnerPair(k, value);
            }
        } catch (NullPointerException e) {
            table[index] = new InnerPair(k, value);
            logger.debug("Set to basket in catch value " + table[index]);
        }

        return table[index].getValue();
    }

    @Override
    public V get(long key) {
        int index = presentsCheck(key);

        logger.debug("Returned value is " + table[index].getValue());
        return table[index].getValue();
    }

    @Override
    public V remove(long key) {
        int index = presentsCheck(key);

        table[index].deletePair();

        logger.debug("Deleted value is " + table[index].getValue());
        return table[index].getValue();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < table.length - 1; i++){
            if (table[i] != null){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean containsKey(long key) {
        if (key > table.length){
            return false;
        }

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
        return 0L;
    }

    @Override
    public void clear() {

    }

    private class InnerPair {
        private Long key;
        private V value;
        private boolean deleted;

        public InnerPair(Long key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }

        public Long getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean isDeleted(){
            return deleted;
        }

        public boolean deletePair(){
            if (!this.deleted){
                this.deleted = true;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Key = " + key + "; value = " + value;
        }
    }

    private long keyCheck(long key){
        Long newKey = key;

        if (newKey < 0){
            newKey = Math.abs(newKey);
            logger.debug("Key is positive with value " + newKey);
        }

        while (newKey > maxKeyValue){
            newKey /= 2;
            logger.debug("New key is " + newKey);
        }

        return newKey;
    }

    private void tableSizeCheck(long key){
        if (key > table.length){
            int k = (int) key * 2;
            table = (InnerPair[]) Array.newInstance(InnerPair.class, k);
            logger.debug("New table length is " + table.length);
        }
    }

    private int presentsCheck(long key){
        logger.debug("Entered key: " + key);

        long k = keyCheck(key);
        int index = (int) k;

        if (table[index] == null || table[index].isDeleted()){
            logger.debug("Returned value is empty or deleted");
            throw new NullPointerException();
        }

        logger.debug("The index " + index + " is being");

        return index;
    }
}
