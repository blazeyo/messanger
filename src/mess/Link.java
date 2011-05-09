package mess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Link.
 * @author Blazej
 */
public class Link {

    Socket kkSocket;
    ObjectOutputStream out;
    ObjectInputStream in;

    private String host;

    public Link(String hostName) {
        host = hostName;
        try {
            kkSocket = new Socket(host, 4444);
            out = new ObjectOutputStream(kkSocket.getOutputStream());
            in = new ObjectInputStream(kkSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host);
        }

        Thread t = new Thread(new ConnectionLoop());
        t.start();

    }

    private class ConnectionLoop implements Runnable {

        public void run() {
            ConnectionMessage fromServer;
            try {
                out.writeObject(ClientProtocol.getOutput());
                while ((fromServer = (ConnectionMessage) in.readObject()) != null) {
                    ClientProtocol.processInput(fromServer);
                    out.writeObject(ClientProtocol.getOutput());
                    Thread.sleep(100);
                }
            } catch(IOException e) {
                System.err.println("Connection error");
            } catch (ClassNotFoundException e) {
                System.err.println("Connection error");
            } catch (InterruptedException e) {
                System.err.println("Connection loop interrupted");
            }
        }

    }
}