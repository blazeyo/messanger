package mess;

import java.io.Serializable;
import java.util.Stack;

/**
 *
 * @author Blazej
 */
public class ConnectionMessage implements Serializable {

    private String from;
    public String getSender() {
        return from;
    }

    private int currentMessage;
    private int messageCount;
    private Message messages[];
    public Message getMessage() {
        if (messageCount > 0 && currentMessage < messageCount)
            return messages[currentMessage++];
        else
            return null;
    }

    public int getMessagesCount() {
        return messageCount;
    }

    private String onlineUsers[];
    public boolean isOnline(String name) {
        for (String s : onlineUsers) {
            if (s.equals(name))
                return true;
        }
        return false;
    }
    
    public ConnectionMessage(Stack<Message> messages, String[] online, String from) {
        this.messageCount = messages.size();
        this.currentMessage = 0;
        this.messages = new Message[this.messageCount];
        messages.toArray(this.messages);
        this.onlineUsers = online;
        this.from = from;
    }

}