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
        longMap.put(33, "Lora");
    }

    @Test
    public void putMethodTest(){
        logger.debug("put method test");
        assertNotNull(longMap.put(75, "cat"));
        assertNotNull(longMap.put(76, "dog"));
    }

    @Test
    public void getMethodTest(){
        logger.debug("get method test");
        assertEquals("Nick", longMap.get(31));
    }

    @Test
    public void removeMethodTest(){
        logger.debug("remove method test");
        assertNotNull(longMap.get(31));
        longMap.remove(31);
        assertEquals("Nick", longMap.get(31));
    }

    @Test
    public void isEmptyMethodTest(){
        logger.debug("isEmpty method test");
        assertFalse(longMap.isEmpty());
    }

    @Test
    public void containsKeyMethodTest(){
        logger.debug("containsKey method test");
        assertTrue(longMap.containsKey(31));
    }

    @Test
    public void containsValueMethodTest(){
        logger.debug("containsValue method test");
        assertTrue(longMap.containsValue("Nick"));
    }

    @Test
    public void keysMethodTest(){
        logger.debug("keys method test");
        assertArrayEquals(new long [] {31, 33}, longMap.keys());
    }

    @Test
    public void valuesMethodTest(){
        logger.debug("values method test");
        assertArrayEquals(new String [] {"Nick", "Lora"}, longMap.values());
    }

    @Test
    public void sizeMethodTest(){
        logger.debug("size method test");
        assertEquals(2, longMap.size());
    }
}
