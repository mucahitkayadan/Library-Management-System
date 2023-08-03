package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDate.now;

final public class LibraryMember extends Person implements Serializable {

	private static final long serialVersionUID = -2226197306790714013L;

	private final String memberId;

	private final List<CheckoutRecord> checkoutRecords;

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
		this.memberId = memberId;
		this.checkoutRecords = new ArrayList<>();
	}

	public String getMemberId() {
		return memberId;
	}

	public void addCheckoutRecord(CheckoutRecord record) {
		this.checkoutRecords.add(record);
	}

	public List<CheckoutRecord> getCheckoutRecords() {
		return Collections.unmodifiableList(checkoutRecords);
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
						", " + getTelephone() + " " + getAddress();
	}

	public void checkout(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		copy.changeAvailability();
		CheckoutRecordEntry entry = new CheckoutRecordEntry(copy, checkoutDate, dueDate);
		CheckoutRecord checkoutRecord = new CheckoutRecord(this, List.of(entry));
//        checkoutRecord.addEntry(entry);
		this.addCheckoutRecord(checkoutRecord);
	}

	public void checkout(BookCopy copy, int maxCheckoutLength) {
		checkout(copy, now(), now().plusDays(maxCheckoutLength));
	}
}
