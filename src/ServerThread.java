import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Server implements Runnable {
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
                String response;
                if(input.equals("FLIP")) {
                    response = CoinFlipGame.flipCoin();
                } else if(input.equals("ROLL")) {
                    response = DiceRollGame.rollDice();
                } else {
                    String[] splitInput = input.split(" ");
                    response = parseLongInput(splitInput);
                }
                printWriter.println(response);
                printWriter.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private String parseLongInput(String[] splitInput) {
        switch(splitInput[0]) {
            case "LOGIN":
                User user = loginManager.login(splitInput[1], splitInput[2]);
                if(user != null) return ("TRUE " + user);
                else return "FALSE";
            case "CREATE":
                if(loginManager.createAccount(splitInput[1], splitInput[2]))
                    return "TRUE";
                else return "FALSE";
            case "ADD":
                String username = splitInput[1];
                int change = Integer.parseInt(splitInput[2]);
                return requestScoreUpdate(username, change);
            case "LOSE":
                username = splitInput[1];
                change = -1 * (Integer.parseInt(splitInput[2]));
                return requestScoreUpdate(username, change);
            default:
                return "INVALID";
        }
    }

    private String requestScoreUpdate(String username, int change) {
        User user = database.getUser(username);
        int newScore = user.getScore() + change;
        database.updateScore(username, newScore);
        return String.format(STR."SCORE \{newScore}");
    }
}
