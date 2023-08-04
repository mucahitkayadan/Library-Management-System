package business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
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
    public Collection<Book> allBooks() {
		DataAccess da = new DataAccessFacade();
        return da.readBooksMap().values();
    }

    @Override
    public Collection<LibraryMember> allLibraryMembers() {
    	DataAccess da = new DataAccessFacade();
        return da.readMemberMap().values();
    }
    
    @Override
    public Book getBookByISBN(String isbn) {
    	DataAccess da = new DataAccessFacade();
        return da.findBookByIsbn(isbn).orElse(null);
    }

    @Override
    public LibraryMember findMemberById(String memberId) {
    	DataAccess da = new DataAccessFacade();
        return da.findMemberById(memberId).orElse(null);
    }
    
    @Override
    public void deleteMember(String memberId) {
    	DataAccess da = new DataAccessFacade();
        da.deleteMember(memberId);

    }

    @Override
    public LibraryMember getLibraryMemberById(String memberId) {
    	DataAccess da = new DataAccessFacade();
        Collection<LibraryMember> members = da.readMemberMap().values();
        for (LibraryMember member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
    
    @Override
    public void saveBook(Book book) {
    	DataAccess da = new DataAccessFacade();
        da.saveBook(book);
    } 
    
    @Override
    public void saveMember(LibraryMember member) {
    	DataAccess da = new DataAccessFacade();
        da.saveNewMember(member);

    }
    
    @Override
    public void checkBook(String memberId, String isbn) throws LibrarySystemException {
        DataAccess dao = new DataAccessFacade();
        //search member from data storage
        LibraryMember member = dao.findMemberById(memberId)
                .orElseThrow(() -> new LibrarySystemException("Member id ID: " + memberId + " not found."));

        //search book from storage using ISBN
        Book book = getBookByISBN(isbn);
        if (book == null) {
            throw new LibrarySystemException("Book with ISBN: " + isbn + " not found.");
        }
        //Check if the book is available
        if (!book.isAvailable()) {
            throw new LibrarySystemException("Book is not available for checkout");
        }

        //call nextNextAvailableCopy
        BookCopy copy = book.getNextAvailableCopy();

        //call checkout method from a member
        //mark the copy that is not available
        // create checkoutEntry
        // Add checkoutEntry to CheckoutRecord
        member.checkout(copy, book.getMaxCheckoutLength());
        //save member
        dao.saveNewMember(member);
        // save book
        dao.saveBook(book);
    }
    @Override
    public List<CheckoutHistory> getCheckoutHistory() {
    	DataAccess da = new DataAccessFacade();
        Collection<LibraryMember> members = da.readMemberMap().values();

        List<CheckoutRecord> records = members.stream()
                .map(LibraryMember::getCheckoutRecords)
                .filter(checkoutRecords -> checkoutRecords.size() > 0)
                .flatMap(List::stream)
                .toList();

        List<CheckoutHistory> history = new ArrayList<>();
        records.forEach(record -> record.getEntries().forEach(entry -> history.add(new CheckoutHistory(entry.getCopy(), record.getMember(), entry.getCheckoutDate(), entry.getDueDate()))));

        return history;
    }
}
