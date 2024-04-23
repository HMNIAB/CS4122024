public class CoinImage extends AnimatedImage {
    public static final int HEADS = 0;
    public static final int TAILS = 27;
    private final String FILEPATH = "img/coin.png";

    public CoinImage() {
        setBufferedImage(FILEPATH);
        setFrameWidth(128);
        setFrameHeight(168);
        setAnimationStart(1);
        setAnimationEnd(26);
    }
}
