package librarysystem;

import business.SystemController;
import dataaccess.Auth;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Main extends JFrame {
	private static final LibWindow login = LoginWindow.INSTANCE;

	public Main(String accessRight) {
		super("Librarian - [" + accessRight + "]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        
        var menuItems = new ArrayList<String>();
        if(SystemController.currentAuth == null) {
            showLogin();
            return;
        } else if(SystemController.currentAuth == Auth.ADMIN) {
        	menuItems.add("Add new book");
        	menuItems.add("Add new member");
        	menuItems.add("Add copy of a book");
        } else if(SystemController.currentAuth == Auth.LIBRARIAN) {
        	menuItems.add("Checkout book");
        	menuItems.add("All members");
        } else if(SystemController.currentAuth == Auth.BOTH){
        	menuItems.add("Add new book");
        	menuItems.add("Add new member");
        	menuItems.add("Add copy of a book");
        	menuItems.add("Checkout book");
        	menuItems.add("All members");
        }
        menuItems.add("All books");
        menuItems.add("Logout");

        String[] m = menuItems.toArray(new String[0]);
        var links = new JList<String>(m);     
        var cards = new JPanel(new CardLayout());

        var welcome = new JPanel();
        welcome.setSize(480, 640);
        welcome.add(new JLabel("Library Management"));
        welcome.add(links);
        String currDirectory = System.getProperty("user.dir");
        ImageIcon image = new ImageIcon(currDirectory+"/src/librarysystem/LibraryManagement.jpg");
        welcome.add(new JLabel(image));

        cards.add(welcome);
        
        var checkout = new LibraryCheckoutUI();
        cards.add(checkout, "Checkout book");

        var newMember= new AddNewMember();
        cards.add(newMember, "Add new member");

        var allMembers = new AllMembers();
        cards.add(allMembers.getMainPanel(), "All members");

        var allBooks = new AllBooks();
        cards.add(allBooks.getMainPanel(), "All books");
        
        var newBook = new AddNewBookUI();
        cards.add(newBook, "Add new book");
        
        var duplicateBook = new DuplicateBookUI();
        cards.add(duplicateBook, "Add copy of a book");
        
        links.addListSelectionListener(evt -> {
        	var selected = links.getSelectedValue().toString();
        	if(selected.equals("Logout")) {
        		this.dispose();
                showLogin();
        	}
        	links.setSelectionBackground(Color.LIGHT_GRAY);
        	var cl = (CardLayout) (cards.getLayout());
        	cl.show(cards, selected);
        });
        
        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, links, cards);
        splitPane.setDividerLocation(640 - 480);
        add(splitPane, BorderLayout.CENTER);
        
        setVisible(true);
	}

	public static void main(String[] args) {
        login.init();
        Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
        login.setVisible(true);
	}

    private void showLogin() {
        setVisible(false);
        login.setVisible(true);
    }
}
