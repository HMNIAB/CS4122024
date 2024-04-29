import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    private static ServerSocket server;
    protected static UserDatabase database;
    protected static LoginManager loginManager;
    private static ArrayList<ServerThread> socketList;
    private static ArrayList<Thread> threadList;

    public static void main(String[] args) {
        database = new UserDatabase();
        loginManager = new LoginManager(database);

        try {
            server = new ServerSocket(5000);
            System.out.println("Server established on port 5000");
            threadList = new ArrayList<Thread>();
            socketList = new ArrayList<ServerThread>();
            addShutdownAction();

            while(!server.isClosed()) {
                try {
                    Socket clientSocket = server.accept();

                    ServerThread serverThread = new ServerThread(clientSocket);
                    Thread thread = new Thread(serverThread);
                    threadList.add(thread);
                    socketList.add(serverThread);
                    thread.start();
                } catch(SocketException e) {
                    System.out.println("ERROR: SocketException (If you're seeing this while purposefully terminating" +
                            "the server, disregard.)");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void addShutdownAction() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for(ServerThread t : socketList) {
                    t.close();
                }
                for(Thread t : threadList) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    server.close();
                    System.out.println("Server closed.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
