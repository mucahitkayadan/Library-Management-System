package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static java.time.LocalDate.now;

final public class CheckoutRecordEntry implements Serializable {

    private static final long serialVersionUID = 6110690276685962829L;

    private final BookCopy copy;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;

    public CheckoutRecordEntry(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
        this.copy = Objects.requireNonNull(copy);
        this.checkoutDate = Objects.requireNonNull(checkoutDate);
        this.dueDate = Objects.requireNonNull(dueDate);
    }

    public CheckoutRecordEntry(BookCopy copy, LocalDate dueDate) {
        this(copy, LocalDate.now(), dueDate);
    }

    public BookCopy getCopy() {
        return copy;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isOverdue() {
        return now().isAfter(dueDate) && !copy.isAvailable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckoutRecordEntry that = (CheckoutRecordEntry) o;

        if (!Objects.equals(checkoutDate, that.checkoutDate)) return false;
        return Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        int result = checkoutDate != null ? checkoutDate.hashCode() : 0;
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CheckoutRecordEntry{" +
                "copy=" + copy +
                ", checkoutDate=" + checkoutDate +
                ", dueDate=" + dueDate +
                ", isOverdue=" + isOverdue() +
                '}';
    }
}
