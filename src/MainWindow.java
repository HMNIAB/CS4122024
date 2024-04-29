import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JTabbedPane jTabs;
    private CoinFlipPanel coinFlipPanel;
    private DiceRollPanel diceRollPanel;
    private JLabel scoreText;
    private JLabel usernameText;
    private JButton helpButton;
    private JButton logoutButton;
    private TableModel model;

    public MainWindow() {
        super("Coin Flip Game");
        JPanel infoPanel = constructInfoPanel();
        JPanel leaderboardPanel = constructLeaderboardPanel();
        JPanel bottomPanel = constructBottomPanel();
        coinFlipPanel = new CoinFlipPanel();
        diceRollPanel = new DiceRollPanel();

        jTabs = new JTabbedPane();
        jTabs.add("COIN", coinFlipPanel);
        jTabs.add("DICE", diceRollPanel);
        jTabs.setSize(200,200);

        getContentPane().add(BorderLayout.NORTH, infoPanel);
        getContentPane().add(BorderLayout.CENTER, jTabs);
        getContentPane().add(BorderLayout.EAST, leaderboardPanel);
        getContentPane().add(BorderLayout.SOUTH, bottomPanel);

        setSize(700, 500);
        getRootPane().setBorder(new EmptyBorder(10,10,10,10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // TODO: when logout button exists, toggle setEnabled along with jTabs. maybe disable window closing if possible?

    public void disableInput() {
        jTabs.setEnabled(false);
    }

    public void enableInput() {
        jTabs.setEnabled(true);
    }

    public GamePanel getCurrentPanel() {
        return (GamePanel) jTabs.getSelectedComponent();
    }

    public void setUsernameText(String text) {
        usernameText.setText(text);
    }

    public void setScoreText(int score) {
        scoreText.setText(score + " points");
    }

    public void addCoinFlipActionListener(ActionListener a) {
        coinFlipPanel.addCoinFlipActionListener(a);
    }

    public void addDiceRollActionListener(ActionListener a) {
        diceRollPanel.addDiceRollActionListener(a);
    }

    public void addHelpButtonActionListener(ActionListener a) {
        helpButton.addActionListener(a);
    }

    public void addLogoutButtonActionListener(ActionListener a) { logoutButton.addActionListener(a);}

    public void addTabChangeListener(ChangeListener c) {
        jTabs.addChangeListener(c);
    }

    private JPanel constructInfoPanel() {
        usernameText = new JLabel();
        scoreText = new JLabel();

        JPanel jPanel = new JPanel();

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(1,2));
        subPanel.add(usernameText);
        subPanel.add(scoreText);

        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        Border emptyBorder = BorderFactory.createEmptyBorder(5,5,5,5);

        subPanel.setSize(500, 50);
        subPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        logoutButton = new JButton("Log Out");

        jPanel.add(subPanel);
        jPanel.add(logoutButton);
        return jPanel;
    }

    private JPanel constructLeaderboardPanel() {
        JPanel jPanel = new JPanel();
        model = new DefaultTableModel(new String[]{"Username", "Score"}, 3);
        JTable jTable = new JTable(model);
        jPanel.add(jTable);

        Border lineBorder = BorderFactory.createTitledBorder("Leaderboard");
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        jPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        jPanel.setSize(100,500);
        return jPanel;
    }

    private JPanel constructBottomPanel() {
        JPanel jPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        jPanel.setLayout(flowLayout);

        helpButton = new JButton("?");
        helpButton.setSize(10,10);
        jPanel.add(helpButton);

        return jPanel;
    }

    public void updateLeaderboard(String scores) {
        scores = scores.replace("LEADERBOARD ", "");
        String[] scoreList = scores.split(",");
        for(int i = 0; i < scoreList.length; i++){
            String[] user = scoreList[i].split(" ");
            model.setValueAt(user[0], i, 0);
            model.setValueAt(user[1], i, 1);
        }
    }
}
