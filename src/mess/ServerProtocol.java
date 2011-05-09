package mess;

import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Blazej
 */
public class ServerProtocol {

    static {
        messages = new CopyOnWriteArrayList<Message>();
    }
    private static CopyOnWriteArrayList<Message> messages;

    public static void processInput(ConnectionMessage m) {
        Message mess;
        while ((mess = m.getMessage()) != null) {
            System.out.println("message received from " + mess.getSender() + " to " + mess.getRecipient() + ": " + mess.getMessage());
            sendMessage(mess);
        }
    }

    public static void sendMessage(Message m) {
        messages.add(m);
    }

    public static ConnectionMessage getOutput(String clientName) {
        Stack<Message> clientMessages = new Stack<Message>();
        Iterator i = messages.iterator();
        while (i.hasNext()) {
            Message m = (Message)i.next();
            if (m.getRecipient().equals(clientName)) {
                clientMessages.push(m);
                messages.remove(m);
            }
        }
        return new ConnectionMessage(clientMessages, null, "server");
    }

}
