package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
<<<<<<< HEAD
import static java.time.LocalDate.now;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<CheckoutRecord> checkoutRecords;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
=======

import static java.time.LocalDate.now;

final public class LibraryMember extends Person implements Serializable {

	private static final long serialVersionUID = -2226197306790714013L;

	private final String memberId;

	private final List<CheckoutRecord> checkoutRecords;

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
>>>>>>> 1ba3d664393a096a19b406ed01fde9ac1b86a4f6
		this.memberId = memberId;
		this.checkoutRecords = new ArrayList<>();
	}

	public String getMemberId() {
		return memberId;
	}

<<<<<<< HEAD
	public String getFullName() {
		return super.getFirstName() + " "+ super.getLastName();
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
=======
	public void addCheckoutRecord(CheckoutRecord record) {
		this.checkoutRecords.add(record);
>>>>>>> 1ba3d664393a096a19b406ed01fde9ac1b86a4f6
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
