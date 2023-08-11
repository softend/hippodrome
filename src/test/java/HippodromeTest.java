import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    @Test
    public void constructorTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        String message = "";
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        assertEquals("Horses cannot be null.", message);

        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        assertEquals("Horses cannot be empty.", message);
    }

    @Test
    public void horsesEqualsTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(new Horse("horse" + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        List<Horse> horses1 = new ArrayList<>(horses);
        assertEquals(horses1, hippodrome.getHorses());
    }

    @Test
    public void hippodromeMoveTest(){
        List<Horse> lst = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            lst.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(lst);
        hippodrome.move();

        for (Horse horse : hippodrome.getHorses()) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    public void getWinnerTest(){
        List<Horse> lst = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lst.add(new Horse("name"+i, i, Math.random()*10));
        }
        Hippodrome hippodrome = new Hippodrome(lst);
        Optional<Horse> maxHorse = lst.stream().max(Comparator.comparingDouble(Horse::getDistance));
        maxHorse.ifPresent(horse -> assertEquals(horse, hippodrome.getWinner()));
    }
}
