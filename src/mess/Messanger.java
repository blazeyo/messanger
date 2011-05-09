package mess;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Instant messanger.
 * @author Blazej
 */
public class Messanger {

    private static ConnectionMessage state;

    private static HashMap<Contact, Conversation> conversations;
    static {
        conversations = new HashMap<Contact, Conversation>();
    }

    static Conversation getConversation(Contact interlocutor) {
        return conversations.get(interlocutor);
    }

    static void activateConversation(Contact interlocutor) {
        if (conversations.containsKey(interlocutor)) {
            getConversation(interlocutor).activate();
        }
    }

    static void putConverstaion(Contact interlocutor) {
        conversations.put(interlocutor, new Conversation(interlocutor));
    }
    
    static void setState(ConnectionMessage state) {
        Messanger.state = state;
        Message m;
        while ((m = state.getMessage()) != null) {
            Contact sender = ContactList.getContact(m.getSender());
            if (sender == null) {
                sender = new Contact(m.getSender());
                contacts.addNewContact(sender);
                putConverstaion(sender);
            }
            getConversation(sender).receiveMessage(m);
        }
    }

    static String getName() {
        return name;
    }

    private static JFrame window;

    static JFrame getFrame() {
        return window;
    }

    static void showError(String message) {
        JOptionPane.showMessageDialog(window, message, "Błąd", JOptionPane.ERROR_MESSAGE, null);
    }

    private JMenuBar menuBar;
    private JMenu menu;

    private static String name = null;
    private void loadName() {
        final String filename = "profile.dat";
        try {
            ObjectInputStream s = new ObjectInputStream(new FileInputStream(filename));
            name = (String)s.readObject();
            s.close();
        } catch (ClassNotFoundException ex) {
            Messanger.showError("Błąd pliku z profilem nie istnieje");
        } catch (IOException e) {
            Messanger.showError("Błąd pliku z profilem nie istnieje");
        }
        if (name == null) {
            name = (String)JOptionPane.showInputDialog(
                    window,
                    "Podaj swoje imię",
                    "Dodaj profil",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
            try {
                new File(filename).delete();
                ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(filename));
                s.writeObject(name);
                s.close();
            } catch (IOException ex) {
                Messanger.showError("Błąd zapisu profilu");
            }
        }
    }

    private static ContactList contacts;

    private Link link;

    public Messanger(String host) {
        contacts = new ContactList();
        loadName();
        link = new Link(host);

        menuBar = new JMenuBar();
        menu = new JMenu("Komunikator");
        menu.add(new JMenuItem(new NewContactAction()));
        menuBar.add(menu);

        window = new JFrame(name + " - Messanger");
        window.setContentPane(contacts);
        window.setJMenuBar(menuBar);
        window.setSize(200, 450);
        window.setVisible(true);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class NewContactAction extends AbstractAction {

        public NewContactAction() {
            super("Dodaj nowy kontakt");
        }

        public void actionPerformed(ActionEvent e) {
            String name = (String)JOptionPane.showInputDialog(
                    window,
                    "Podaj imię",
                    "Dodaj nowy kontakt",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
            if (!name.equals("")) {
                contacts.addNewContact(new Contact(name));
            }
        }

    }
}
