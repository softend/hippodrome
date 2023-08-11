import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.plaf.PanelUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.times;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HorseTest {

    @Test
    public void constructorFirstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));

        String message = "";
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        assertEquals("Name cannot be null.", message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"         ", "     \n  ", "      ", "     \n    ", "   \n     ", ""})
    public void constructorEmptyStringTest(String arg) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(arg, 1, 1));

        String message = "";
        try {
            new Horse(arg, 1, 1);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        assertEquals("Name cannot be blank.", message);
    }

    @Test
    public void constructorIntsTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 1, -1));

        String message = "";
        try {
            new Horse("Name", -1, 1);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        assertEquals("Speed cannot be negative.", message);

        try {
            new Horse("Name", 1, -1);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        assertEquals("Distance cannot be negative.", message);
    }

    @Test
    public void getNameTest() {
        String name = "horse";
        Horse horse = new Horse(name, 1, 1);
        assertEquals(name, horse.getName());
    }

    @Test
    public void getSpeedTest() {
        int speed = 1;
        Horse horse = new Horse("horse", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void distanceTest() {
        int distance = 5;
        Horse horse = new Horse("name", 4, distance);
        assertEquals(distance, horse.getDistance());
        horse = new Horse("name", 4);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void verifyGetRDInsideMoveTest() {
        try (MockedStatic<Horse> mockHorse = Mockito.mockStatic(Horse.class)) {
            new Horse("name", 1).move();
            mockHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 3, 4})
    public void moveTest(double result) {
        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {
            horseMock.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(result);
            Horse horse = new Horse("name", 1);
            double expected = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
            horse.move();
            assertEquals(expected, horse.getDistance());
        }
    }


}
