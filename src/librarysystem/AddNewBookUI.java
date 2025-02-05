package librarysystem;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.Validation;
import business.ValidationException;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class AddNewBookUI extends JPanel{

	JPanel mainPanel, topPanel, bottomPanel, buttonPanel, errorPanel;
	JTextField isbnF, titleF, authorF, authorF1;
	JSpinner cLenF, copyF;
	JLabel errorField = new JLabel("");
	
	public AddNewBookUI() {
		mainPanel = new JPanel(new BorderLayout(5, 5));

		defineTopPanel();
		defineBottomPanel();
		defineErrorPanel();

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(bottomPanel, BorderLayout.CENTER);
		mainPanel.add(errorPanel, BorderLayout.SOUTH);
		add(mainPanel);
	}

	private void defineTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(6, 2, 10, 10));

		topPanel.add(new JLabel("ISBN"));
		isbnF = new JTextField();
		topPanel.add(isbnF);

		topPanel.add(new JLabel("Title"));
		titleF = new JTextField();
		topPanel.add(titleF);

		topPanel.add(new JLabel("Author's First Name"));
		authorF = new JTextField();
		topPanel.add(authorF);

		topPanel.add(new JLabel("Author's Last Name"));
		authorF1 = new JTextField();
		topPanel.add(authorF1);

		topPanel.add(new JLabel("Maximum checkout (days)"));
		cLenF = new JSpinner(new SpinnerNumberModel(7, 7, 21, 1));
		topPanel.add(cLenF);

		topPanel.add(new JLabel("Copies"));
		copyF = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		topPanel.add(copyF);
	}

	private void defineBottomPanel() {

		bottomPanel = new JPanel();
		defineButtonPanel();

		bottomPanel.add(buttonPanel, BorderLayout.NORTH);
	}

	private void defineButtonPanel() {

		buttonPanel = new JPanel(new BorderLayout());

		JButton button = new JButton("Add");
		button.addActionListener(evt -> {
			var isbn = isbnF.getText();
			var title = titleF.getText();
			var authorFirstName = authorF.getText();
			var authorLastName = authorF1.getText();
			var cLen = (int) cLenF.getValue();
			var copies = (int) copyF.getValue();


			try {
				Validation.nonEmpty(title);
				Validation.nonEmpty(authorFirstName);
				Validation.nonEmpty(authorLastName);
				Validation.isIsbn(isbn);
			} catch (ValidationException e) {
				errorField.setText(e.getMessage());
				return;
			}

			var aList = Arrays.asList()
					.stream().map(el -> {
						return new Author(authorFirstName,authorLastName, "", null, "");
					})
					.collect(Collectors.toList());

			DataAccess da = new DataAccessFacade();
			var nB = new Book(isbn, title, cLen, aList);
			da.saveNewBook(nB);
			errorField.setText(nB.getTitle() + "Book saved successfully!");
		});

		buttonPanel.add(button);
	}

	private void defineErrorPanel() {
		errorPanel = new JPanel(new BorderLayout());
		errorPanel.add(errorField);
	}
	
	public static void main(String[] args) {
		var f = new JFrame();
		f.add(new AddNewBookUI());
		f.setVisible(true);
	}
}
