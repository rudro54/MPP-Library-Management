package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecord implements Serializable{
	private static final long serialVersionUID = -1819441423913236168L;
	private LocalDate cDate, dDate;
	private BookCopy b;
	
	public CheckoutRecord(LocalDate c, LocalDate d, BookCopy b) {
		b.changeAvailability();
		cDate = c; dDate = d; this.b = b;
	}

	public String getIsbn() {
		return b.getBook().getIsbn();
	}

	public String getCheckoutDateAsString() {
		return cDate.toString();
	}

	public String getDueDateAsString() {
		return dDate.toString();
	}
}
