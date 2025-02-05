package librarysystem;

import business.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;


public class AddNewMember extends JPanel {

    public static final AddNewMember INSTANCE = new AddNewMember();

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField memberNo;
    private JTextField lastName;
    private JTextField firstName;
    private JTextField phoneNumber;
    private JTextField state;
    private JTextField city;
    private JTextField zip;
    private JTextField street;

    private JButton submitButton;

    ControllerInterface ci = new SystemController();

    public AddNewMember() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        defineLowerHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        mainPanel.add(lowerHalf, BorderLayout.SOUTH);

        add(mainPanel); // Add the main panel to the AddNewMember panel
    }

    private void defineUpperHalf() {

        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(middlePanel, BorderLayout.CENTER);
        upperHalf.add(lowerPanel, BorderLayout.SOUTH);

    }

    private void defineMiddleHalf() {

        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        middleHalf.add(s, BorderLayout.SOUTH);

    }

    private void defineLowerHalf() {

        lowerHalf = new JPanel();
        lowerHalf.setLayout(new BorderLayout());

        String[] columnNames = {"Member ID"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This causes the table not to be editable
            }
        };
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(20, 20));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // double-click
                    int row = table.getSelectedRow();
                    int col = table.getSelectedColumn();
                    String memberId = (String) table.getValueAt(row, col);
                    showMemberDetails(memberId);
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        lowerHalf.add(tableScrollPane, BorderLayout.CENTER);
        lowerHalf.add(refreshButton, BorderLayout.NORTH);
        refreshButton.addActionListener(e -> updateTable(tableModel));
        updateTable(tableModel);
    }

    private void updateTable(DefaultTableModel tableModel) {

        tableModel.setRowCount(0);
        Collection<String> memberIds = ci.allMemberIds();
        for (String memberId : memberIds) {
            tableModel.addRow(new Object[]{memberId});
        }
        tableModel.fireTableDataChanged();
    }

    private void defineTopPanel() {

        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Add New Member");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);

    }

    private void defineMiddlePanel() {

        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1, 2));

        defineLeftTextPanel();
        defineRightTextPanel();

        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }


    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        submitButton = new JButton("Submit");
        addSubmitButtonListener(submitButton);
        lowerPanel.add(submitButton);
    }

    private void defineLeftTextPanel() {

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new GridLayout(4, 2));

        memberNo = new JTextField(10);
        JLabel memberNoLabel = new JLabel("Member No");
        memberNoLabel.setFont(Util.makeSmallFont(memberNoLabel.getFont()));
        leftTextPanel.add(memberNoLabel);
        leftTextPanel.add(memberNo);

        firstName = new JTextField(10);
        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(Util.makeSmallFont(firstNameLabel.getFont()));
        leftTextPanel.add(firstNameLabel);
        leftTextPanel.add(firstName);

        lastName = new JTextField(10);
        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(Util.makeSmallFont(lastNameLabel.getFont()));
        leftTextPanel.add(lastNameLabel);
        leftTextPanel.add(lastName);

        phoneNumber = new JTextField(10);
        JLabel phoneNumberLabel = new JLabel("Phone Number");
        phoneNumberLabel.setFont(Util.makeSmallFont(phoneNumberLabel.getFont()));
        leftTextPanel.add(phoneNumberLabel);
        leftTextPanel.add(phoneNumber);
    }

    private void defineRightTextPanel() {

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new GridLayout(4, 2));

        state = new JTextField(10);
        JLabel stateLabel = new JLabel("State");
        stateLabel.setFont(Util.makeSmallFont(stateLabel.getFont()));
        rightTextPanel.add(stateLabel);
        rightTextPanel.add(state);

        city = new JTextField(10);
        JLabel cityLabel = new JLabel("City");
        cityLabel.setFont(Util.makeSmallFont(cityLabel.getFont()));
        rightTextPanel.add(cityLabel);
        rightTextPanel.add(city);

        street = new JTextField(10);
        JLabel streetLabel = new JLabel("Street");
        streetLabel.setFont(Util.makeSmallFont(streetLabel.getFont()));
        rightTextPanel.add(streetLabel);
        rightTextPanel.add(street);

        zip = new JTextField(10);
        JLabel zipLabel = new JLabel("Zip");
        zipLabel.setFont(Util.makeSmallFont(zipLabel.getFont()));
        rightTextPanel.add(zipLabel);
        rightTextPanel.add(zip);
    }


    private void addSubmitButtonListener(JButton butn) {

        butn.addActionListener(evt -> {
            try {
                Validation.nonEmpty(memberNo.getText());
                Validation.nonEmpty(lastName.getText());
                Validation.nonEmpty(firstName.getText());
                Validation.nonEmpty(state.getText());
                Validation.nonEmpty(street.getText());
                Validation.nonEmpty(city.getText());
                Validation.nonEmpty(zip.getText());
                Validation.isZip(zip.getText());
                Validation.nonEmpty(phoneNumber.getText());
                Validation.isTelephone(phoneNumber.getText());

                SystemController sc = new SystemController();
                sc.addMember(memberNo.getText(), firstName.getText(), lastName.getText(), phoneNumber.getText(), state.getText(), city.getText(), street.getText(), zip.getText());
                JOptionPane.showMessageDialog(AddNewMember.this, "Member id added");
            } catch (ValidationException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });
    }

    private void showMemberDetails(String memberId) {
        LibraryMember member = ci.getMember(memberId);
        JPanel detailPanel = new JPanel();
        detailPanel.setSize(300, 250);
        detailPanel.setLayout(new BorderLayout());
        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.append("Member ID: " + member.getMemberId() + "\n");
        detailArea.append("First Name: " + member.getFirstName() + "\n");
        detailArea.append("Last Name: " + member.getLastName() + "\n");
        detailArea.append("Phone Number: " + member.getTelephone() + "\n");
        detailArea.append("State: " + member.getAddress() + "\n");

        detailPanel.add(new JScrollPane(detailArea), BorderLayout.CENTER);

        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setTitle("Member Details");
        dialog.getContentPane().add(detailPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}