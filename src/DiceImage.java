public class DiceImage extends AnimatedImage {
    public static final int ONE = 0;
    public static final int TWO = 1;
    public static final int THREE = 2;
    public static final int FOUR = 3;
    public static final int FIVE = 4;
    public static final int SIX = 5;
    private final String FILEPATH = "img/dice.png";

    public DiceImage() {
        setBufferedImage(FILEPATH);
        setFrameWidth(128);
        setFrameHeight(168);
        setAnimationStart(6);
        setAnimationEnd(17);
    }
}
