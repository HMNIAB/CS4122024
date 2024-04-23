import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class AnimatedImage {
    private BufferedImage bufferedImage;
    private int frameWidth;
    private int frameHeight;
    private int animationStart;
    private int animationEnd;

    public BufferedImage getAnimationFrame(int index) {
        int xpos = index * 128;
        return bufferedImage.getSubimage(xpos, 0, frameWidth, frameHeight);
    }

    public int getAnimationStart() {
        return animationStart;
    }

    public int getAnimationEnd() {
        return animationEnd;
    }

    protected void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    protected void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    protected void setAnimationStart(int animationStart) {
        this.animationStart = animationStart;
    }

    protected void setAnimationEnd(int animationEnd) {
        this.animationEnd = animationEnd;
    }

    protected void setBufferedImage(String path) {
        try {
            bufferedImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
