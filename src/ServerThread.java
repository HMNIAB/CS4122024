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
                String response = null;
                if(input.equals("FLIP")) {
                    response = CoinFlipGame.flipCoin();
                }
                else {
                    String[] splitInput = input.split(" ");
                    if(splitInput[0].equals("LOGIN")) {
                        response = Server.login(splitInput);
                    } else if (splitInput[0].equals("CREATE")) {
                        response = Server.createAccount(splitInput);
                    }
                }
                printWriter.println(response);
                printWriter.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
