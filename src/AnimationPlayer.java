import java.util.concurrent.TimeUnit;

public class AnimationPlayer {
    public static void play(GamePanel gamePanel, int resultFrame) {
        AnimatedImage animatedImage = gamePanel.getImage();

        for(int i = animatedImage.getAnimationStart(); i <= animatedImage.getAnimationEnd() + 1; i++) {
            int frame;
            if (i <= animatedImage.getAnimationEnd()) frame = i;
            else frame = resultFrame;

            try {
                TimeUnit.MILLISECONDS.sleep(42);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gamePanel.updateImagePanel(frame);
        }
    }
}
