package mess;

import java.util.Stack;

/**
 *
 * @author Blazej
 */
public class LinkProtocol {

    static {
        messages = new Stack<Message>();
    }

    protected static Stack<Message> messages;
    public static void sendMessage(Message m) {
        messages.push(m);
    }

}
