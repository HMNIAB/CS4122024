import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    GameWindow gameWindow;
    ClientNetwork clientNetwork;

    public Controller() {
        clientNetwork = new ClientNetwork();
        gameWindow = new GameWindow();

        gameWindow.addCoinFlipActionListener(new CoinFlipActionListener());
        gameWindow.open();

        // TODO: restructure when gameWindow construction/opening happens when more windows are implemented
    }

    public class CoinFlipActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientNetwork.sendRequest("FLIP");
            gameWindow.setResultText(clientNetwork.getResponse());
        }
    }
}
