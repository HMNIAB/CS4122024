import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    View view;
    ClientNetwork clientNetwork;

    public Controller() {
        clientNetwork = new ClientNetwork();
        view = new View();

        view.getGameWindow().addCoinFlipActionListener(new CoinFlipActionListener());
    }

    public class CoinFlipActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientNetwork.sendRequest("FLIP");
            view.getGameWindow().setResultText(clientNetwork.getResponse());
        }
    }
}
