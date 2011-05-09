package mess;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contact class.
 * @author Blazej
 */
public class Contact extends JPanel implements Serializable {
    private String name;
    public String getContactName() {
        return name;
    }

    transient private JLabel nameLabel;
    transient private JLabel statusLabel;

    public Contact(String name) {
        super();
        this.name = name;
        status = false;
        
        nameLabel = new JLabel(name);
        add(nameLabel);
        statusLabel = new JLabel(getStatus());
        add(statusLabel);

        this.setMinimumSize(new Dimension(200, 16));
        this.setMaximumSize(new Dimension(400, 24));

        addMouseListener(new ContactMouseAdapter(this));
    }

    transient private boolean status;
    private String getStatus() {
        updateStatus();
        return status ? "(+)" : "(-)";
    }

    private void updateStatus() {

    }

    private class ContactMouseAdapter extends MouseAdapter {
        private Contact source;

        public ContactMouseAdapter(Contact contact) {
            source = contact;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Messanger.activateConversation(source);
            }
        }
    }
}
