package business;

import java.time.LocalDate;

final public class CheckoutHistory {
    private final BookCopy copy;
    private final LibraryMember member;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;

    public CheckoutHistory(BookCopy copy, LibraryMember member, LocalDate checkoutDate, LocalDate dueDate) {
        this.copy = copy;
        this.member = member;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public BookCopy getCopy() {
        return copy;
    }

    public LibraryMember getMember() {
        return member;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
