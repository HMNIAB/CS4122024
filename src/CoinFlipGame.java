import java.util.Random;

public class CoinFlipGame {
    public static String flipCoin() {
        Random random = new Random();
        int randomInt = random.nextInt();
        randomInt = randomInt % 2;

        if (randomInt == 0) return "HEADS";
        else return "TAILS";
    }
}
