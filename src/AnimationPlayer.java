import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationPlayer {
    private Controller.GameButtonActionListener caller;
    private Timer timer;
    private GamePanel gamePanel;
    private AnimatedImage animatedImage;
    private int frame;
    private int resultFrame;

    public AnimationPlayer(GamePanel gamePanel, Controller.GameButtonActionListener caller) {
        this.gamePanel = gamePanel;
        this.caller = caller;
        animatedImage = gamePanel.getImage();
    }

    public void play(int resultFrame) {
        this.resultFrame = resultFrame;
        this.frame = animatedImage.getAnimationStart();

        timer = new Timer(42, new AnimationListener());
        timer.setInitialDelay(42);
        timer.start();
    }

    private void stop() {
        timer.stop();
        caller.resumeAfterAnimation();
    }

    public class AnimationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gamePanel.updateImagePanel(frame);

            if(frame == animatedImage.getAnimationEnd()) {
                frame = resultFrame;
                return;
            }

            if(frame != resultFrame) frame++;
            else stop();
        }
    }
}
