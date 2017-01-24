import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.com.blacklion.classes.LongMap;

public class LongMapTest {
    private LongMap<String> longMap;

    @Before
    public void init(){
        longMap = new LongMap<>();
    }

    @Test
    public void putMethodTest(){
        assertNotNull(longMap.put(1, "Nick"));
    }
}
