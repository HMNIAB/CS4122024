import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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
        setLayout(new GridLayout(1, 2));

        gamePanel = constructGamePanel();
        wagerPanel = constructWagerPanel();

        add(wagerPanel);
        add(gamePanel);
        setSize(200, 200);
    }

    abstract public void disableInput();

    abstract public void enableInput();

    public ButtonModel getCall() {
        return buttonGroup.getSelection();
    }

    public int getWagerAmount() {
        String wagerText = wagerField.getText();
        if (wagerText == null || wagerText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a wager amount.");
            return 0;
        }

        try {
            return Integer.parseInt(wagerText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid integer for the wager amount.");
            return 0;
        }
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
        wagerField.setSize(80, 50);

        wagerPanel.add(wagerLabel);
        wagerPanel.add(buttonsPanel);
        wagerPanel.add(amountLabel);
        wagerPanel.add(wagerField);

        return wagerPanel;
    }

    protected JPanel constructImagePanel() {
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel(imageIcon);
        jPanel.add(jLabel);

        jPanel.setSize(128,168);
        jPanel.setVisible(true);
        return jPanel;
    }

    protected abstract JPanel constructButtonsPanel();
}

