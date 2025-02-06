package business;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class Main {

	public static void main(String[] args) {
		System.out.println(allWhoseZipContains3());
		System.out.println(allHavingOverdueBook());
		System.out.println(allHavingMultipleAuthors());

	}
	//Returns a list of all ids of LibraryMembers whose zipcode contains the digit 3
	public static List<String> allWhoseZipContains3() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		return mems.stream()
				.filter(value -> value.getAddress().getZip().contains("3"))
				.map(LibraryMember::getMemberId)
				.toList();
	}
	//Returns a list of all ids of  LibraryMembers that have an overdue book
	public static List<String> allHavingOverdueBook() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		//implement
		return members.stream()
				.filter(member -> member.getCheckouts().stream()
						.anyMatch(checkout -> checkout.getDueDate().isBefore(LocalDate.now())))
				.map(LibraryMember::getMemberId)
				.collect(Collectors.toList());
		
	}
	
	//Returns a list of all isbns of  Books that have multiple authors
	public static List<String> allHavingMultipleAuthors() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBooksMap().values();
		List<Book> bs = new ArrayList<>();
		bs.addAll(books);
		return books.stream() // Stream of Books
				.filter(book -> book.getAuthors().size() > 1)
				.map(Book::getIsbn)
				.toList();
		
	}

}
