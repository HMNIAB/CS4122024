import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientNetwork {
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public ClientNetwork() {
        try {
            Socket socket = new Socket("localhost", 6000);
            OutputStream outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
            bufferedReader = new BufferedReader((new InputStreamReader(socket.getInputStream())));
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error with the server occurred");
        }
    }

    public void sendRequest(String request) {
        if (printWriter != null) {
            printWriter.println(request);
        } else {
            System.err.println("PrintWrite is not initialized");
        }
    }

    public String getResponse() {
        String response = null;
        try {
            response = bufferedReader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
