import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server established on port 5000");
            while(true) {
                System.out.println("Waiting for client to connect...");
                Socket clientSocket = server.accept();

                Thread thread = new Thread(new ServerThread(clientSocket));
                thread.start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
