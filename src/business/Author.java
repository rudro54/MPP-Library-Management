package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String bio;
	public String getBio() {
		return bio;
	}
	
	public Author(String firstName, String lastName, String telephone, Address address, String bio) {
		super(firstName, lastName, telephone, address);
		this.bio = bio;
	}

	private static final long serialVersionUID = 7508481940058530471L;
}
