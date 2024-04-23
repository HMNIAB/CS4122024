import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class GamePanel extends JPanel {
    protected JLabel resultText;
    protected ButtonGroup buttonGroup;
    private JPanel gamePanel;
    protected JPanel imagePanel;
    private JPanel wagerPanel;
    private WagerField wagerField;
    private ImageIcon imageIcon = new ImageIcon();
    private AnimatedImage image;
    private int currentFrame = 0;

    public GamePanel() {
        gamePanel = constructGamePanel();
        wagerPanel = constructWagerPanel();

        add(wagerPanel);
        add(gamePanel);
    }

    public ButtonModel getCall() {
        return buttonGroup.getSelection();
    }

    public int getWagerAmount() {
        if(wagerField.getText() != null) {
            return Integer.parseInt(wagerField.getText());
        } else return 0;
    }

    public void setResultText(String text) {
        resultText.setText(text);
    }

    public AnimatedImage getImage() {
        return image;
    }

    public void setImage(AnimatedImage image) {
        this.image = image;
        updateImagePanel(currentFrame);
    }

    public void updateImagePanel(int frameIndex) {
        this.currentFrame = frameIndex;
        imageIcon.setImage(image.getAnimationFrame(currentFrame));
        imagePanel.repaint();
    }

    protected abstract JPanel constructGamePanel();

    private JPanel constructWagerPanel() {
        JPanel wagerPanel = new JPanel();
        wagerPanel.setBorder(new TitledBorder("Wager"));
        wagerPanel.setLayout(new GridLayout(2, 2));

        JLabel wagerLabel = new JLabel("Call:");
        JLabel amountLabel = new JLabel("Amount:");

        JPanel buttonsPanel = constructButtonsPanel();
        wagerField = new WagerField();

        wagerPanel.add(wagerLabel);
        wagerPanel.add(buttonsPanel);
        wagerPanel.add(amountLabel);
        wagerPanel.add(wagerField);

        return wagerPanel;
    }

    protected JPanel constructImagePanel() {
//        JPanel jPanel = new JPanel() {
//            @Override
//            public void paintComponent(Graphics g) {
//                g.drawImage(image.getAnimationFrame(currentFrame), 0, 0, new ImageObserver() {
//                    @Override
//                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//                        return false;
//                    }
//                });
//            }
//        };
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel(imageIcon);
        jPanel.add(jLabel);

        jPanel.setSize(128,168);
        jPanel.setVisible(true);
        return jPanel;
    }

    protected abstract JPanel constructButtonsPanel();
}
