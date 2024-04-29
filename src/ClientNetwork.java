import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientNetwork {
    private Controller controller;
    private Socket socket;
    private PrintWriter printWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public ClientNetwork(Controller controller) {
        this.controller = controller;
        try {
            socket = new Socket("127.0.0.1", 5000);
            System.out.println("Connected to server");

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendRequest(String request) {
        printWriter.println(request);
        printWriter.flush();
    }

    public String getResponse() {
        String response = null;
        try {
            response = bufferedReader.readLine();
        } catch (SocketException e) {
            closeConnection();
            controller.connectionClosed();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public void closeConnection() {
        printWriter.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
