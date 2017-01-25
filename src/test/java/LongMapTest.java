import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.com.blacklion.classes.LongMap;

public class LongMapTest {
    private LongMap<String> longMap;
    Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void init(){
        longMap = new LongMap<>();
        longMap.put((long) (Integer.MAX_VALUE / 8) * 2 + 37, "some2");
        longMap.put((long) (Integer.MAX_VALUE / 8) * 2 + 31, "some1");
        longMap.put(31, "Nick");
        longMap.put(33, "Lora");
        longMap.put(2, "some3");
    }

    @Test
    public void putMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertNull(longMap.put((long) (Integer.MAX_VALUE / 8) * 2 + 2, "byaka"));
        assertNull(longMap.put(2, "some"));
        assertNull(longMap.put((long) (Integer.MAX_VALUE / 8) + 2, "cat"));
        assertNull(longMap.put(76, "dog"));
        assertNull(longMap.put(31, "Baka-baka"));
    }

    @Test
    public void getMethodTest(){
        System.out.println("\n");
        logger.debug("test");

        assertEquals("Nick", longMap.get(31));
        assertEquals("some1", longMap.get((long) (Integer.MAX_VALUE / 8) * 2 + 31));
    }

    @Test
    public void removeMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertEquals("Nick", longMap.get(31));
        assertEquals("some1", longMap.get((long) (Integer.MAX_VALUE / 8) * 2 + 31));
        logger.debug("Removed " + longMap.remove(31));
    }

    @Test
    public void isEmptyMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertFalse(longMap.isEmpty());
        longMap.clear();
        assertTrue(longMap.isEmpty());
    }

    @Test
    public void containsKeyMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertTrue(longMap.containsKey((long) (Integer.MAX_VALUE / 8) * 2 + 37));
        assertTrue(longMap.containsKey((long) (Integer.MAX_VALUE / 8) * 2 + 31));
        assertTrue(longMap.containsKey(2));
    }

    @Test
    public void containsValueMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertTrue(longMap.containsValue("some1"));
        assertTrue(longMap.containsValue("some3"));

    }

    @Test
    public void keysMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertArrayEquals(new long [] {2, 31, 33,
                        (long) (Integer.MAX_VALUE / 8) * 2 + 31,
                        (long) (Integer.MAX_VALUE / 8) * 2 + 37},
                longMap.keys());
    }

    @Test
    public void valuesMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertArrayEquals(new String [] {"Lora", "Nick", "some1", "some2", "some3"}, longMap.values());
    }

    @Test
    public void sizeMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertEquals(5, longMap.size());
    }
}
