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
        assertNotNull(longMap.put(44, "cat"));
        assertNotNull(longMap.put(45, "dog"));
    }

    @Test
    public void getMethodTest(){
        logger.debug("get method test");
        assertEquals("Nick", longMap.get(1));
    }

    @Test
    public void removeMethodTest(){
        logger.debug("remove method test");
        assertNotNull(longMap.get(31));
        longMap.remove(31);
        longMap.get(31);
    }

    @Test
    public void isEmptyMethodTest(){
        logger.debug("isEmpty method test");
        assertFalse(longMap.isEmpty());
    }
}
