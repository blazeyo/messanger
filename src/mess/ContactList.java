package mess;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Blazej
 */
public class ContactList extends JPanel {
    private static Vector<Contact> contacts;

    private JPopupMenu menu;
    /** Holds recently clicked contact for context menu actions. */
    private Contact clickedContact;
    private final String filename = "contacts.dat";

    private ContactMouseAdapter mouseAdapter;

    public static Contact getContact(String name) {
        Iterator i = contacts.iterator();
        while (i.hasNext()) {
            Contact t = (Contact)i.next();
            if (t.getContactName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    ContactList() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        contacts = new Vector<Contact>();

        menu = new JPopupMenu("Opcje");
        menu.add(new JMenuItem(new DeleteContactAction()));

        load();
    }

    public void addNewContact(Contact contact) {
        addToList(contact);
        save();
    }

    private void addToList(Contact contact) {
        contact.addMouseListener(new ContactMouseAdapter(contact));
        contacts.add(contact);
        Messanger.putConverstaion(contact);
        add(contact);
        revalidate();
    }

    private void removeFromList(Contact contact) {
        contacts.remove(contact);
        remove(contact);
        revalidate();
        repaint();
        save();
    }

    private void save() {
        try {
            new File(filename).delete();
            ObjectOutputStream s = new ObjectOutputStream(
                    new FileOutputStream(filename));
            Iterator i = contacts.iterator();
            while (i.hasNext()) {
                s.writeObject((Contact)i.next());
            }
            s.close();
        } catch (IOException ex) {
            Messanger.showError("Błąd zapisu kontaktów");
        }
    }

    private void load() {
        try {
            ObjectInputStream s = new ObjectInputStream(new FileInputStream(filename));
            Contact c;
            try {
                while ((c = (Contact)s.readObject()) != null) {
                    addToList(c);
                }
                s.close();
            } catch (ClassNotFoundException ex) {
                Messanger.showError("Nieprawidłowy plik z kontaktami");
            }
        } catch (FileNotFoundException ex) {
            Messanger.showError("Plik z kontaktami nie istnieje");
        } catch (IOException ex) {
            //Messanger.showError("Błąd odczytu kontaktów");
        }
    }

    private class DeleteContactAction extends AbstractAction {
        public DeleteContactAction() {
            super("Usuń");
        }

        public void actionPerformed(ActionEvent e) {
            int confirmed = JOptionPane.showConfirmDialog(
                    Messanger.getFrame(),
                    "Usunąć użytkownika " + clickedContact.getContactName() + " z listy?",
                    "Usuwanie kontaktu",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirmed == JOptionPane.YES_OPTION) {
                removeFromList(clickedContact);
            }
        }
    }

    private class ContactMouseAdapter extends MouseAdapter {
        private Contact source;

        public ContactMouseAdapter(Contact contact) {
            source = contact;
        }

        @Override
        public void mousePressed(MouseEvent e) { showMenu(e); }
        @Override
        public void mouseReleased(MouseEvent e) { showMenu(e); }

        private void showMenu(MouseEvent e) {
            if (e.isPopupTrigger()) {
                clickedContact = (Contact)e.getComponent();
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Messanger.activateConversation(source);
            }
        }
    }
}
