package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<CheckoutRecord> cRec = new ArrayList<>();
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		cRec = new ArrayList<CheckoutRecord>();
	}
	
	public void addCheckout(CheckoutRecord cR) {
		this.cRec.add(cR);
	}
	public void removeCheckout(CheckoutRecord cr) {
		this.cRec.remove(cr);
	}
	public List<CheckoutRecord> getCheckouts() {
		return this.cRec;
	}
	
	public String getMemberId() {
		return memberId;
	}

	
	
	@Override
	public String toString() {
		return "Member Info:\n" +
				"ID: " + memberId + "\n" +
				"FirstName: " + getFirstName() + "\n" +
				"LastName: " + getLastName() + "\n" +
				"PhoneNumber: " + getTelephone() + "\n" +
				"Address: " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
