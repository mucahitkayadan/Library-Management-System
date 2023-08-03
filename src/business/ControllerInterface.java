package business;

import java.util.Collection;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public Collection<LibraryMember> allLibraryMembers();
	public Collection<Book> allBooks();
	public void checkBook(String memberId, String bookIsbn) throws LibrarySystemException;
	public Book getBookByISBN(String isbn);
	public void saveBook(Book book);
	public LibraryMember findMemberById(String memberId);
	public List<CheckoutHistory> getCheckoutHistory();
	
}
