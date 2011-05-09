package mess;

/**
 *
 * @author Blazej
 */
public class ClientProtocol extends LinkProtocol {

    public static void processInput(ConnectionMessage m) {
        Messanger.setState(m);
    }

    public static ConnectionMessage getOutput() {
//        if (!messages.empty())
//            JOptionPane.showMessageDialog(Messanger.getFrame(), "Message sent");
        ConnectionMessage m = new ConnectionMessage(messages, null, Messanger.getName());
        messages.clear();
        return m;
    }

}
