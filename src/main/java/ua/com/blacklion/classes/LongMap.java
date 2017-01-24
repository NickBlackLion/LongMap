package ua.com.blacklion.classes;

import org.apache.log4j.Logger;
import ua.com.blacklion.interfaces.TestMap;

import java.lang.reflect.Array;
import java.util.Arrays;

/**Class that implements part of the logic from HashMap class*/
public class LongMap<V> implements TestMap<V> {
    private Logger logger = Logger.getLogger(this.getClass());
    private InnerPair [] table;
    private int tableInitSize = 10;

    public LongMap() {
        table = (InnerPair[]) Array.newInstance(InnerPair.class, tableInitSize);
        logger.debug("Constructor has created. Table size = " + table.length);
    }

    @Override
    public V put(long key, V value) {
        logger.debug("Incoming data: key: " + key + " value: " + value);
        long k = makeAbsoluteKey(key);
        int index = (int) k;
        tableSizeCheck(k);

        try {
            if (table[index].isDeleted()) {
                table[index] = new InnerPair(k, value);
                logger.debug("Created value in basket " + table[index]);
            }
        } catch (NullPointerException e) {
            table[index] = new InnerPair(k, value);
            logger.debug("Caught NullPointerException. Created value in basket " + table[index]);
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
        for (InnerPair ip: table){
            if (ip != null && !ip.isDeleted()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsKey(long key) {
        if (key > table.length){
            logger.debug("in key > table.length return false");
            return false;
        }

        int low = 0;
        int height = table.length;

        try {
            while (low < height) {
                int middle = low + (height - low) / 2;

                if (key == middle && makeAbsoluteKey(key) == presentsCheck(key)) {
                    logger.debug("in key == middle && makeNewKey(key) == presentsCheck(key) return true");
                    return true;
                }

                if (key < middle){
                    logger.debug("in key < middle: middle is " + middle + "; height is " + height + "; low is " + low);
                    height = middle - 1;
                }

                if (middle < key){
                    logger.debug("in middle < key: middle is " + middle + "; height is " + height + "; low is " + low);
                    low = middle + 1;
                }
            }
        } catch (NullPointerException e) {
            logger.debug("in NullPointerException return false");
            return false;
        }

        logger.debug("in the end return false");
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (InnerPair aTable: table) {
            if (aTable != null && !aTable.isDeleted() && aTable.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long[] keys() {
        long [] tempKeys = new long[table.length];
        long [] keys;
        int counter = 0;

        for (InnerPair aTable : table) {
            if (aTable != null && !aTable.isDeleted()) {
                tempKeys[counter] = aTable.getKey();
                counter++;
            }
        }

        logger.debug("counter " + counter);
        keys = Arrays.copyOf(tempKeys, counter);

        return keys;
    }

    @Override
    public V[] values() {
        InnerPair [] tempValues = (InnerPair []) Array.newInstance(InnerPair.class, table.length);
        int counter = 0;

        for (InnerPair ip: table){
            if (ip != null && !ip.isDeleted()) {
                tempValues[counter] = ip;
                counter++;
            }
        }

        V [] values = (V[]) Array.newInstance(tempValues[0].getValue().getClass(), counter);

        for (int i = 0; i < values.length; i++){
            values[i] = tempValues[i].getValue();
        }

        return values;
    }

    @Override
    public long size() {
        int counter = 0;

        for (InnerPair aTable : table) {
            if (aTable != null && !aTable.isDeleted()) {
                counter++;
            }
        }

        return counter;
    }

    @Override
    public void clear() {
        table = (InnerPair[]) Array.newInstance(InnerPair.class, 10);
    }

    //Inner class that making pair for embedded basket
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

    //Checking if a key value is negative, making it absolute
    private long makeAbsoluteKey(long key){
        Long newKey = key;

        if (newKey < 0){
            newKey = Math.abs(newKey);
            logger.debug("Key is positive with value " + newKey);
        }

        return newKey;
    }

    /*If entered key is bigger than basket size
    * makes basket size twice bigger but not bigger
    * than Integer.MAX_VALUE*/
    private void tableSizeCheck(long key){
        if (key > table.length && key < Integer.MAX_VALUE / 2){
            int k = (int) key * 2;
            copyMainTable(k);
        }
        if (key > table.length && key > Integer.MAX_VALUE / 2){
            int k = Integer.MAX_VALUE;
            copyMainTable(k);
        }
    }

    //Checking if a value of actual key is being
    private int presentsCheck(long key){
        logger.debug("Entered key: " + key);

        int index = (int) makeAbsoluteKey(key);

        if (table[index] == null || table[index].isDeleted()){
            logger.debug("Returned value is empty or deleted");
            throw new NullPointerException();
        }

        logger.debug("The index " + index + " is being");

        return index;
    }

    //Copy current basket in a bigger basket
    private void copyMainTable(int size){
        InnerPair [] tempTable = table;
        logger.debug("tempTable size is " + tempTable.length);
        table = Arrays.copyOf(tempTable, size);
        logger.debug("New table length is " + table.length);
    }
}
