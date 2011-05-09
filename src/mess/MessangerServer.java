package mess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Blazej
 */
public class MessangerServer {

    private ServerSocket serverSocket;

    public MessangerServer() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
            System.exit(-1);
        }
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Connection accepted on port: 4444");
                Thread t = new Thread(new ClientThread(clientSocket));
                t.start();
            } catch (IOException e) {
                System.out.println("Accept failed on port: 4444");
            }
        }
    }

}
