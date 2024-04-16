import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameWindow {
    private JFrame jFrame;
    private JButton coinFlipButton;
    private JLabel resultText;

    public GameWindow() {
        jFrame = new JFrame();
        resultText = new JLabel();
        coinFlipButton = new JButton("Flip");

        resultText.setText("(flip to get results)");
        jFrame.getContentPane().add(BorderLayout.NORTH, resultText);
        jFrame.getContentPane().add(BorderLayout.CENTER, coinFlipButton);

        jFrame.setSize(300, 300);
    }

    public void open() {
        jFrame.setVisible(true);
    }

    public void setResultText(String text) {
        resultText.setText(text);
    }

    public void addCoinFlipActionListener(ActionListener a) {
        coinFlipButton.addActionListener(a);
    }
}
