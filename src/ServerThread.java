import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        System.out.println("Connected to client " + clientSocket);
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

            String input;
            while((input = bufferedReader.readLine()) != null) {
                if(input.equals("FLIP")) {
                    String result = CoinFlipGame.flipCoin();
                    printWriter.println(result);
                    printWriter.flush();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
