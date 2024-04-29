import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public abstract class GamePanel extends JPanel {
    protected JLabel resultText;
    protected ButtonGroup buttonGroup;
    private JPanel gamePanel;
    protected JPanel imagePanel;
    private JPanel wagerPanel;
    private SpinnerNumberModel spinnerModel;
    private JSpinner wagerSpinner;
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
        return (int) wagerSpinner.getValue();
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

    public void updateWagerSpinner(int userScore) {
        spinnerModel.setMaximum(userScore);
        if((Integer)wagerSpinner.getValue() > (Integer)spinnerModel.getMaximum()) {
            wagerSpinner.setValue(spinnerModel.getMaximum());
        }
    }

    protected abstract JPanel constructGamePanel();

    private JPanel constructWagerPanel() {
        JPanel wagerPanel = new JPanel();
        wagerPanel.setBorder(new TitledBorder("Wager"));
        wagerPanel.setLayout(new GridLayout(2, 2));

        JLabel wagerLabel = new JLabel("Call:");
        JLabel amountLabel = new JLabel("Amount:");

        JPanel buttonsPanel = constructButtonsPanel();

        spinnerModel = new SpinnerNumberModel(0, 0, 1, 1);
        wagerSpinner = new JSpinner(spinnerModel);

        // wagerField = new WagerField();
        // wagerField.setSize(80, 50);

        wagerPanel.add(wagerLabel);
        wagerPanel.add(buttonsPanel);
        wagerPanel.add(amountLabel);
        wagerPanel.add(wagerSpinner);

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

