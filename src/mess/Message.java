package mess;

import java.io.Serializable;

/**
 *
 * @author Blazej
 */
public class Message implements Serializable {

    private String from;
    public String getSender() {
        return from;
    }

    private String to;
    public String getRecipient() {
        return to;
    }

    private String message;
    public String getMessage() {
        return message;
    }

    public Message(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

}
