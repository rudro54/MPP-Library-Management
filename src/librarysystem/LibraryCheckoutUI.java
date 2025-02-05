package librarysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.BookCopy;
import business.CheckoutRecord;
import business.LoginException;
import business.Validation;
import business.ValidationException;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;

public class LibraryCheckoutUI extends JPanel {

	JLabel errorField = new JLabel("");
	CheckoutRecord cRecord;
	DefaultTableModel dtm = new DefaultTableModel(new Object[] {"Member ID", "ISBN", "Checkout", "Due"}, 0);
	
    public LibraryCheckoutUI() {

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel with labels and text fields
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.add(new JLabel("Member ID"));
        var mID = new JTextField("");
        topPanel.add(mID);
        topPanel.add(new JLabel("ISBN"));
        var ISBN = new JTextField("");
        topPanel.add(ISBN);
        
        var okB = new JButton("Checkout");
        okB.addActionListener(evt -> {
        	var memberID = mID.getText();
        	var isbn = ISBN.getText();
        	
        	try {
				Validation.isID(memberID);
				Validation.isIsbn(isbn);
			} catch (ValidationException e) {
				errorField.setText(e.getMessage());
				return;
			}
        	
        	DataAccess da = new DataAccessFacade();
    		var mMap = da.readMemberMap();
    		var bMap = da.readBooksMap();
    		if(!mMap.containsKey(memberID)) {
    			errorField.setText("User not found!");
    			return;
    		}
    		if(!bMap.containsKey(isbn)) {
    			errorField.setText("Book not found!");
    			return;
    		}
    		
    		var b = bMap.get(isbn);
    		if(b.isAvailable()) {
    			var a = b.getNextAvailableCopy();
    			var now = LocalDate.now();
    			var then = LocalDate.now().plusDays(b.getMaxCheckoutLength());
    			cRecord = new CheckoutRecord(now, then, a);
    			b.updateCopies(a);
    			var member = mMap.get(memberID);
    			member.addCheckout(cRecord);
    			da.saveNewMember(member);
    			da.saveNewBook(b);
    			dtm.addRow(new Object[] {memberID, isbn, now.toString(), then.toString()});
				errorField.setText("Book checkout successful!");
    		} else {
    			errorField.setText("No more books available!");
    		}
    		
        	
        });
        topPanel.add(okB);

        // Error message panel
        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorPanel.add(errorField);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
//        tablePanel.add(new JLabel("Book:"), BorderLayout.NORTH);
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to the content pane
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(errorPanel, BorderLayout.SOUTH);
        contentPane.add(tablePanel, BorderLayout.EAST);

        add(contentPane);
        setVisible(true);
    }
}