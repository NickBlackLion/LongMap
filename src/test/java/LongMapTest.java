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
        longMap.put(31, "Nick");
        longMap.put((long) (Integer.MAX_VALUE / 8) * 2 + 31, "some1");
        longMap.put((long) (Integer.MAX_VALUE / 8) * 2 + 37, "some2");
        longMap.put(33, "Lora");
        longMap.put(2, "some3");
    }

    @Test
    public void putMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertNotNull(longMap.put((long) (Integer.MAX_VALUE / 8) * 2 + 2, "byaka"));
        assertNotNull(longMap.put(2, "some"));
        assertNotNull(longMap.put((long) (Integer.MAX_VALUE / 8) + 2, "cat"));
        assertNotNull(longMap.put(76, "dog"));
        assertNotNull(longMap.put(31, "Baka-baka"));
    }

    @Test
    public void getMethodTest(){
        System.out.println("\n");
        logger.debug("test");

        assertEquals("Nick", longMap.get(31));
        assertEquals("some", longMap.get((long) (Integer.MAX_VALUE / 8) * 2 + 31));
    }

    @Test
    public void removeMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertEquals("Nick", longMap.get(31));
        assertEquals("some", longMap.get((long) (Integer.MAX_VALUE / 8) * 2 + 31));
        logger.debug("Removed " + longMap.remove(31));
        assertEquals("some", longMap.get((long) (Integer.MAX_VALUE / 8) * 2 + 31));
        assertEquals("Nick", longMap.get(31));
    }

    @Test
    public void isEmptyMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertFalse(longMap.isEmpty());
        longMap.remove(31);
        longMap.remove((long) (Integer.MAX_VALUE / 8) * 2 + 31);
        longMap.remove(33);
        assertFalse(longMap.isEmpty());
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
        assertArrayEquals(new long [] {31, 33}, longMap.keys());
    }

    @Test
    public void valuesMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertArrayEquals(new String [] {"Nick", "Lora"}, longMap.values());
    }

    @Test
    public void sizeMethodTest(){
        System.out.println("\n");
        logger.debug("test");
        assertEquals(2, longMap.size());
    }
}
