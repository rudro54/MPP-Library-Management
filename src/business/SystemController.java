package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {

	public static Auth currentAuth = null;
	private DataAccess dataAccess = new DataAccessFacade();

	public void addMember(String memberNo, String firstName, String lastName, String phoneNumber,
						  String state, String city, String street, String zip) {

		Address address = addAddress(state, city, street, zip);
		LibraryMember libraryMember = new LibraryMember(memberNo, firstName, lastName, phoneNumber, address);


		dataAccess.saveNewMember(libraryMember);
	}

	private Address addAddress(String state, String city, String street, String zip) {
		return new Address(state, city, street, zip);
	}


	public void login(String id, String password) throws LoginException {

			DataAccess da = new DataAccessFacade();

		HashMap<String, User> map = da.readUserMap();

		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		String passwordFound = map.get(id).getPassword();

		if(!passwordFound.equals(password)) {
			throw new LoginException("Password Incorrect");
		}

		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public LibraryMember getMember(String memberId) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		return members.get(memberId);
	}

	@Override
	public Book getBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		return books.get(isbn);
	}

}
