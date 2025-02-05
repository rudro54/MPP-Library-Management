package librarysystem;

import business.CheckoutRecord;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class AllMembers extends JPanel {
    ControllerInterface ci = new SystemController();
    public JPanel getMainPanel() {
        return mainPanel;
    }
    JLabel errorField = new JLabel("$");
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private TextArea textArea;
    private JList<String> memberIdList;
    private JLabel memberInfoLabel;
    private JTextArea memberInfoTextArea = new JTextArea();

    public AllMembers() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        memberIdList = new JList<>();
        memberIdList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberIdList.addListSelectionListener(new MemberIdListSelectionListener());
        defineTopPanel();
        defineMiddlePanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        mainPanel.add(defineUpperPanel(), BorderLayout.CENTER);
        refresh();
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("All Members");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1, 2)); // Split the panel into two columns

        JScrollPane listScrollPane = new JScrollPane(memberIdList);
        listScrollPane.setPreferredSize(new Dimension(200, 300)); // Set the preferred size of the list
        middlePanel.add(listScrollPane);

        memberInfoTextArea.setEditable(false);
        memberInfoTextArea.setLineWrap(true);
        memberInfoTextArea.setWrapStyleWord(true);
        JScrollPane infoScrollPane = new JScrollPane(memberInfoTextArea);
        infoScrollPane.setPreferredSize(new Dimension(400, 300)); // Set the preferred size of the text area
        middlePanel.add(infoScrollPane);
    }

    public JPanel defineUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(middlePanel, BorderLayout.CENTER);
        return upperPanel;
    }

    private void populateMemberIdList() {
        List<String> ids = ci.allMemberIds();
        Collections.sort(ids);
        memberIdList.setListData(ids.toArray(new String[0]));
    }

    private class MemberIdListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                return;
            }
            String selectedMemberId = memberIdList.getSelectedValue();
            if (selectedMemberId!= null) {
                LibraryMember member = ci.getMember(selectedMemberId);
                if (member!= null) {
                    StringBuilder sb = new StringBuilder();
                    // Add checkout info
                    Collection<CheckoutRecord> checkoutRecords = member.getCheckouts();
                    if (checkoutRecords != null && !checkoutRecords.isEmpty()) {
                        sb.append("\nCheckout Records:\n");
                        for (CheckoutRecord cRecord : checkoutRecords) {
                            sb.append("ISBN: ").append(cRecord.getIsbn()).append("\n");
                            sb.append("Checkout Date: ").append(cRecord.getCheckoutDateAsString()).append("\n");
                            sb.append("Due Date: ").append(cRecord.getDueDateAsString()).append("\n");
                            sb.append("\n"); // add a blank line between records
                        }
                    }
                    memberInfoTextArea.setText(sb.toString());
                }
            }
        }
    }

    public void refresh() {
        populateMemberIdList();
    }
}