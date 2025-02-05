package librarysystem;

import business.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class AllBooks extends JPanel {
    private static final long serialVersionUID = 1L;
    ControllerInterface ci = new SystemController();

    JLabel errorField = new JLabel("$", JLabel.CENTER);
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JTextArea detailField = new JTextArea();

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public AllBooks() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        defineTopPanel();
        defineMiddlePanel();
        defineBottomPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        midPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        midPanel.add(middlePanel);
        midPanel.add(bottomPanel);

        mainPanel.add(midPanel, BorderLayout.CENTER);
        mainPanel.setVisible(true);
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("List of Books");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"ISBN", "Title", "Max Checkout Duration"};
        DefaultTableModel booksTable = new DefaultTableModel(columnNames, 0);

        JTable table = new JTable(booksTable) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This causes the table not to be editable
            }
        };

        table.setPreferredScrollableViewportSize(new Dimension(460, 220));
        table.setFillsViewportHeight(true);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(20, 20));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // double-click
                    int row = table.getSelectedRow();
                    String isbn = (String) table.getValueAt(row, 0);
                    showMemberDetails(isbn);
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(480, 230));

        middlePanel.add(tableScrollPane, BorderLayout.CENTER);
        middlePanel.add(refreshButton, BorderLayout.NORTH);
        refreshButton.addActionListener(e -> updateTable(booksTable));
        updateTable(booksTable);
        middlePanel.setMinimumSize(new Dimension(460, 200));
        middlePanel.setPreferredSize(new Dimension(460, 260));
        middlePanel.setMaximumSize(new Dimension(460, 280));

    }

    private void defineBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorPanel.add(errorField);
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailField.setEditable(false);
        detailPanel.add(detailField);
        bottomPanel.add(detailPanel, BorderLayout.NORTH);
        bottomPanel.add(errorPanel, BorderLayout.SOUTH);
    }

    private void updateTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        DataAccess da = new DataAccessFacade();
        Collection<Book> books = da.readBooksMap().values();

        for (Book book : books) {
            tableModel.addRow(new Object[]{book.getIsbn(),
                    book.getTitle(),
                    book.getMaxCheckoutLength()});
        }
        tableModel.fireTableDataChanged();
    }

    private void showMemberDetails(String isbn) {
        Book book = ci.getBook(isbn);

        JPanel detailPanel = new JPanel();
        detailPanel.setSize(460, 300);
        detailPanel.setLayout(new BorderLayout());

        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.append("ISBN: " + book.getIsbn() + "\n");
        detailArea.append("Title: " + book.getTitle() + "\n");
        for (Author author : book.getAuthors()) {
            detailArea.append("Author: " + author.getFirstName() + " " + author.getLastName() + "\n");
        }
        detailArea.append("Max Checkout Duration: " + book.getMaxCheckoutLength() + "\n");
        detailArea.append("Number of copies: " + book.getCopyNums().size() + "\n");

        detailField.setText(detailArea.getText());
    }
}

