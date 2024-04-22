import java.util.Random;

public class DieRollGame {
    public static String rollDie() {
        Random random = new Random();
        int randomInt = random.nextInt(6) + 1;

        return "Roll: " + randomInt;
    }
}
