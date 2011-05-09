package mess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Blazej
 */
public class ClientThread implements Runnable {

    ObjectOutputStream out;
    ObjectInputStream in;
    private Socket socket;

    public ClientThread(Socket clientSocket) {
        socket = clientSocket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e) {
            System.out.println("IO exception 1 on port: 4444");
            System.exit(-1);
        }
    }

    public void run() {
        System.out.println("Client thread ran.");
        ConnectionMessage m;
        try {
            while ((m = (ConnectionMessage) in.readObject()) != null) {
                ServerProtocol.processInput(m);
                out.writeObject(ServerProtocol.getOutput(m.getSender()));
            }
        } catch (IOException e) {
            System.out.println("IO exception 2 on port: 4444");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception on port: 4444");
        }
    }

}