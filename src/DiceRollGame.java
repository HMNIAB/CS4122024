import java.util.Random;

public class DiceRollGame {
    public static String rollDice() {
        Random random = new Random();
        int randomInt = random.nextInt(1, 7);

        return String.valueOf(randomInt);
    }
}
