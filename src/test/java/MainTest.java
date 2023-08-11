import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Disabled
    @Test
    @Timeout(22)
    public void mainTest(){
        try {
            Main.main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
