import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    protected static UserDatabase database;
    protected static LoginManager loginManager;

    public static void main(String[] args) {
        database = new UserDatabase();
        loginManager = new LoginManager(database);

        try {
            //need to close socket somewhere - resource leak otherwise
            ServerSocket server = new ServerSocket(6000);
            System.out.println("Server established on port 6000");
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
