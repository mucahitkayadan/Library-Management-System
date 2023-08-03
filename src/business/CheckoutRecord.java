package business;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final public class CheckoutRecord implements Serializable {

    private final LibraryMember member;
    private final List<CheckoutRecordEntry> entries;

    public CheckoutRecord(LibraryMember member, List<CheckoutRecordEntry> entries) {
        this.member = member;
        this.entries = entries;
    }

    public CheckoutRecord(LibraryMember member) {
        this(member, Collections.emptyList());
    }

    public LibraryMember getMember() {
        return member;
    }

    public List<CheckoutRecordEntry> getEntries() {
        return entries;
    }

    public void addEntry(CheckoutRecordEntry entry) {
        entries.add(entry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckoutRecord that = (CheckoutRecord) o;

        if (!Objects.equals(member, that.member)) return false;
        return Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {
        int result = member != null ? member.hashCode() : 0;
        result = 31 * result + (entries != null ? entries.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CheckoutRecord{" +
                "member=" + member +
                ", entries=" + entries +
                '}';
    }
}
