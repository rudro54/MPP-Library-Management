package librarysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.Validation;
import business.ValidationException;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.*;

public class DuplicateBookUI extends JPanel {

    private static final long serialVersionUID = 7167511124084521494L;

    JPanel mainPanel, topPanel, middlePanel, bottomPanel;
    JButton checkAvailabilityButton, addCopyButton;
    JTextField isbnField;
    DefaultTableModel dtm = new DefaultTableModel(new Object[]{"ISBN", "Title", "Copies"}, 0);
    JLabel errorField = new JLabel("");
    Book selectedBook;

    public DuplicateBookUI() {
        mainPanel = new JPanel(new BorderLayout(5, 5));

        defineTopPanel();
        defineMiddlePanel();
        defineBottomPanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void defineTopPanel() {

        topPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        isbnField = new PlaceholderTextField("Enter ISBN");
        defineCheckAvailabilityButton();
        defineAddCopyButton();
        topPanel.add(isbnField);
        topPanel.add(checkAvailabilityButton);
        topPanel.add(addCopyButton);

    }

    private void defineMiddlePanel() {

        middlePanel = new JPanel();
        JTable detailsTable = new JTable(dtm);
        JScrollPane detailsScrollPane = new JScrollPane(detailsTable);

        middlePanel.add(detailsScrollPane);
    }

    private void defineBottomPanel() {

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(errorField, BorderLayout.CENTER);
    }

    private void defineCheckAvailabilityButton() {
        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.addActionListener(evt -> {
            selectedBook = null;
            var isbn = isbnField.getText();
            try {
                Validation.isIsbn(isbn);
            } catch (ValidationException e) {
                errorField.setText(e.getMessage());
                return;
            }

            DataAccess da = new DataAccessFacade();
            var bMap = da.readBooksMap();
            if (!bMap.containsKey(isbn)) {
                errorField.setText("Book not found!");
                return;
            }

            var b = bMap.get(isbn);
            selectedBook = b;
            dtm.addRow(new Object[]{b.getIsbn(), b.getTitle(), b.getCopies().length});
        });
    }

    private void defineAddCopyButton() {
        addCopyButton = new JButton("Add New Copy");
        addCopyButton.addActionListener(evt -> {
            if (selectedBook == null) {
                errorField.setText("No book selected");
                return;
            }

            DataAccess da = new DataAccessFacade();
            selectedBook.addCopy();
            da.saveNewBook(selectedBook);
            errorField.setText("New book copy added successfully!");
            dtm.removeRow(0);
            dtm.addRow(new Object[]{selectedBook.getIsbn(), selectedBook.getTitle(), selectedBook.getCopies().length});
        });
    }

    public static void main(String[] args) {
        var f = new JFrame();
        f.add(new DuplicateBookUI());
    }
}