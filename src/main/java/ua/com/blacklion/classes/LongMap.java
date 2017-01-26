package ua.com.blacklion.classes;

import org.apache.log4j.Logger;
import ua.com.blacklion.interfaces.TestMap;

import java.lang.reflect.Array;
import java.util.Arrays;

/**Class that implements part of the logic from HashMap class*/
public class LongMap<V> implements TestMap<V> {
    private Logger logger = Logger.getLogger(this.getClass());
    private InnerPair[] table;
    private final int multipleOfArray = Integer.MAX_VALUE / 8;
    private final int tableInitSize = 10;
    private Class<?> kind;

    public LongMap() {
        table = (InnerPair[]) Array.newInstance(InnerPair.class, tableInitSize);
        logger.debug("Constructor was created. Table size = " + table.length);
    }

    @Override
    public V put(long key, V value) {
        logger.debug("Incoming data: key: " + key + " value: " + value);
        long k = makeNewKey(key);
        int index = (int) k;
        tableSizeCheck(k);

        try {
            if (table[index].isDeleted() || table[index].getKey() == key) {
                table[index].setNewInnerPair(new InnerPair(key, value));
                logger.debug("Set value in basket " + table[index]);
            } else {
                for (InnerPair ip = table[index]; ip != null; ip = ip.getNext()) {
                    if (ip.getNext() == null) {
                        ip.setNext(new InnerPair(key, value));
                        logger.debug("Next created linked value is " + ip.getNext());
                        break;
                    } else if (ip.getNext().isDeleted() || ip.getNext().getKey() == key) {
                        ip.getNext().setNewInnerPair(new InnerPair(key, value));
                        logger.debug("Next changed linked value is " + ip.getNext());
                        break;
                    }
                }
            }

            InnerPair ip = table[index];
            while (ip != null) {
               logger.debug("ip is " + ip + ". Index of basket is " + index);
                ip = ip.getNext();
            }

        } catch (NullPointerException e) {
            table[index] = new InnerPair(key, value);
            logger.debug("Caught NullPointerException."
                    + "Created value in basket " + table[index]);
        }

        return null;
    }

    @Override
    public V get(long key) {
        logger.debug("Key for getting value is " + key);
        return presentsCheck(key);
    }

    @Override
    public V remove(long key) {
        logger.debug("Key for removing value is " + key);

        V value = presentsCheck(key);
        int index = (int) makeNewKey(key);

        for (InnerPair ip = table[index]; ip != null; ip = ip.getNext()) {
            if (ip.getKey() == key) {
                ip.deletePair();
            }
        }

        logger.debug("Deleted value is " + value);
        return value;
    }

    @Override
    public boolean isEmpty() {
        if (amountOfObjects() > 0) return false;

        logger.debug("Map is empty");
        return true;
    }

    @Override
    public boolean containsKey(long key) {
        logger.debug("searching key is " + key);
        int low = 0;
        int height = table.length;

        int index = (int) makeNewKey(key);

        try {
            while (low < height) {
                int middle = low + (height - low) / 2;
                logger.debug("index is " + index
                        + "; middle is " + middle
                        + "; height is " + height
                        + "; low is " + low);

                if (index == middle) {
                    for (InnerPair ip: table) {
                        if (ip != null && ip.getKey() == key) {
                            logger.debug("founded key is " + key);
                            return true;
                        } else {
                            for (InnerPair inner = ip; inner != null; inner = inner.getNext()) {
                                if (ip.getNext() != null && ip.getNext().getKey() == key) {
                                    logger.debug("founded key is " + key);
                                    return true;
                                }
                            }
                        }
                    }

                    logger.debug("Key is not founded");
                    return false;
                }

                if (index < middle) {
                    logger.debug("in key < middle: middle is " + middle
                            + "; height is " + height
                            + "; low is " + low);
                    height = middle;
                }

                if (middle < index) {
                    logger.debug("in middle < key: middle is " + middle
                            + "; height is " + height
                            + "; low is " + low);
                    low = middle;
                }
            }
        } catch (NullPointerException e) {
            logger.debug("Key is not founded in NullPointerException");
            return false;
        }

        logger.debug("Key is not founded");
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        logger.debug("searching value is " + value);
        for (InnerPair ip: table) {
            if (ip != null && !ip.isDeleted() && ip.getValue().equals(value)) {
                logger.debug("founded value is " + value);
                return true;
            } else {
                for (InnerPair inner = ip; inner != null; inner = inner.getNext()) {
                    if (ip.getNext() != null && !ip.getNext().isDeleted()
                            && ip.getNext().getValue().equals(value)) {
                        logger.debug("founded value is " + value);
                        return true;
                    }
                }
            }
        }

        logger.debug("Value is not founded");
        return false;
    }

    @Override
    public long[] keys() {
        int counter = amountOfObjects();

        logger.debug("Amount of founded keys are " + counter);
        long[] keys = new long[counter];

        counter = 0;

        for (InnerPair ip: table) {
            if (ip != null && !ip.isDeleted()) {
                keys[counter++] = ip.getKey();
            }

            for (InnerPair innerPair = ip; innerPair != null; innerPair = innerPair.getNext()) {
                if (innerPair.getNext() != null && !ip.getNext().isDeleted()) {
                    keys[counter++] = innerPair.getNext().getKey();
                }
            }
        }

        logger.debug("Before sorting " + printArray(keys));
        Arrays.sort(keys);
        logger.debug("After sorting " + printArray(keys));
        return keys;
    }

    @Override
    public V[] values() {
        int counter = amountOfObjects();

        if (counter == 0) {
            return null;
        }

        V[] values = (V[]) Array.newInstance(kind, counter);

        counter = 0;

        for (InnerPair ip: table) {
            if (ip != null && !ip.isDeleted()) {
                values[counter++] = ip.getValue();
            }

            for (InnerPair innerPair = ip; innerPair != null; innerPair = innerPair.getNext()) {
                if (innerPair.getNext() != null && !ip.getNext().isDeleted()) {
                    values[counter++] = innerPair.getNext().getValue();
                }
            }
        }

        logger.debug("Before sorting " + printArray(values));
        Arrays.sort(values);
        logger.debug("After sorting " + printArray(values));
        return values;
    }

    @Override
    public long size() {
        int counter = amountOfObjects();
        return counter;
    }

    @Override
    public void clear() {
        logger.debug("Cleaning map");
        table = (InnerPair[]) Array.newInstance(InnerPair.class, tableInitSize);
    }

    //Inner class that making pair for embedded basket
    //Works like LinkedList
    private class InnerPair {
        private Long key;
        private V value;
        private boolean deleted;
        private InnerPair next;

        InnerPair(Long key, V value) {
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

        public boolean isDeleted() {
            return deleted;
        }

        public boolean deletePair() {
            if (!this.deleted) {
                this.deleted = true;
                return true;
            } else {
                return false;
            }
        }

        public InnerPair getNext() {
            return next;
        }

        public void setNext(InnerPair next) {
            this.next = next;
        }

        public void setNewInnerPair(InnerPair innerPair) {
            this.key = innerPair.getKey();
            this.value = innerPair.getValue();
        }

        @Override
        public String toString() {
            return "Key = " + key + "; value = " + value;
        }
    }

    /*Check if a key value is negative, making it absolute
    * or if key value bigger then final computed value*/
    private long makeNewKey(long key) {
        Long newKey = key;

        if (newKey < 0) {
            newKey = Math.abs(newKey);
            logger.debug("Key is positive with value " + newKey);
        }

        while (newKey > multipleOfArray) {
            newKey -= multipleOfArray;
            logger.debug("Key made from value bigger"
                    + " then multipleOfArray. New key value is " + newKey);
        }

        return newKey;
    }

    /*If entered key is bigger than basket size
    * makes basket size twice bigger but not bigger
    * than Integer.MAX_VALUE*/
    private void tableSizeCheck(long key) {
        if (key > table.length && key < multipleOfArray) {
            int k = (int) key * 2;
            copyMainTable(k);
        }
        if (key > table.length && key > multipleOfArray) {
            int k = Integer.MAX_VALUE / multipleOfArray;
            copyMainTable(k);
        }
    }

    //Check if an object of actual key is being
    private V presentsCheck(long key) {
        logger.debug("Entered key: " + key);
        V value = null;

        int index = (int) makeNewKey(key);
        boolean isPresentFlag = false;

        for (InnerPair ip = table[index]; ip != null; ip = ip.getNext()) {
            if (ip.getKey() == key && !ip.isDeleted()) {
                value = ip.getValue();
                isPresentFlag = true;
                logger.debug("Key " + key + " is presented with value \'" + value + "\'");
            }
        }

        if (!isPresentFlag) {
            logger.debug("Key " + key + " is absent or deleted");
            throw new NullPointerException();
        }

        return value;
    }

    //Copy current basket in a bigger basket
    private void copyMainTable(int size) {
        InnerPair[] tempTable = table;
        logger.debug("tempTable size is " + tempTable.length);
        table = Arrays.copyOf(tempTable, size);
        logger.debug("New table length is " + table.length);
    }

    private String printArray(long[] array) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                stringBuilder.append("[" + array[i]);
            } else if (i != array.length - 1) {
                stringBuilder.append(", " + array[i]);
            } else {
                stringBuilder.append(", " + array[i] + "]");
            }
        }

        return stringBuilder.toString();
    }

    private String printArray(V[] array) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                stringBuilder.append("[" + array[i]);
            } else if (i != array.length - 1) {
                stringBuilder.append(", " + array[i]);
            } else {
                stringBuilder.append(", " + array[i] + "]");
            }
        }

        return stringBuilder.toString();
    }

    //Count amount of objects in map
    private int amountOfObjects() {
        logger.debug("Counting amount of objects in map");
        int counter = 0;
        boolean flag = false;

        for (InnerPair ip: table) {
            if (ip != null && !ip.isDeleted()) {
                counter++;
                if (!flag) {
                    kind = ip.getValue().getClass();
                    flag = true;
                }
            }

            for (InnerPair innerPair = ip; innerPair != null; innerPair = innerPair.getNext()) {
                if (innerPair.getNext() != null && !ip.getNext().isDeleted()) {
                    counter++;
                }
            }
        }

        logger.debug("Amount of objects is " + counter);
        return counter;
    }
}
