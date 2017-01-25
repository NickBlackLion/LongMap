import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.com.blacklion.classes.LongMap;

public class LongMapTest {
    private LongMap<String> longMap = new LongMap<>();
    Logger logger = Logger.getLogger(this.getClass());

    @Before
    public void init(){
        longMap.put(31, "Nick");
        longMap.put((long) Integer.MAX_VALUE + 31, "some");
        longMap.put(33, "Lora");
    }

    @Test
    public void putMethodTest(){
        System.out.println("\n");
        logger.debug("put method test");
        assertNotNull(longMap.put(2, "some"));
        assertNotNull(longMap.put((long) (Integer.MAX_VALUE / 8) + 2, "cat"));
        assertNotNull(longMap.put((long) (Integer.MAX_VALUE / 8) * 2 + 2, "byaka"));
        assertNotNull(longMap.put(76, "dog"));
        assertNotNull(longMap.put(31, "Baka-baka"));
    }

    @Test
    public void getMethodTest(){
        System.out.println("\n");
        logger.debug("get method test");

        assertEquals("Nick", longMap.get(31));
        assertEquals("some", longMap.get((long) (Integer.MAX_VALUE / 8) + 31));
    }

    @Test
    public void removeMethodTest(){
        System.out.println("\n");
        logger.debug("remove method test");
        assertNotNull(longMap.get(31));
        longMap.remove(31);
        assertEquals("Nick", longMap.get(31));
    }

    @Test
    public void isEmptyMethodTest(){
        System.out.println("\n");
        logger.debug("isEmpty method test");
        assertFalse(longMap.isEmpty());
    }

    @Test
    public void containsKeyMethodTest(){
        System.out.println("\n");
        logger.debug("containsKey method test");
        assertTrue(longMap.containsKey(31));
    }

    @Test
    public void containsValueMethodTest(){
        System.out.println("\n");
        logger.debug("containsValue method test");
        assertTrue(longMap.containsValue("Nick"));
    }

    @Test
    public void keysMethodTest(){
        System.out.println("\n");
        logger.debug("keys method test");
        assertArrayEquals(new long [] {31, 33}, longMap.keys());
    }

    @Test
    public void valuesMethodTest(){
        System.out.println("\n");
        logger.debug("values method test");
        assertArrayEquals(new String [] {"Nick", "Lora"}, longMap.values());
    }

    @Test
    public void sizeMethodTest(){
        System.out.println("\n");
        logger.debug("size method test");
        assertEquals(2, longMap.size());
    }
}
