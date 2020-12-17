package ServerDatabase;

import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
     Server() {
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public static void main(String[] args) {
        Database.getInstance();
        Server server = new Server();
    }

    public void serve(Socket clientSocket) throws IOException {

        System.out.println("in serve method");

         NetworkUtil nc = new NetworkUtil(clientSocket);
        System.out.println("server read start");
         new ReadClientThread(nc);
    }

}
