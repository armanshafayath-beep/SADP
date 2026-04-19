import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// =====================================================================
//  MEMENTO PATTERN — Captures and externalizes the internal state
//  of LibraryManager so it can be restored later (snapshot/restore).
// =====================================================================
public class LibraryMemento implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Member> members;
    private final List<Book> books;
    private final List<IssueRecord> issues;
    private final Date timestamp;

    public LibraryMemento(List<Member> members, List<Book> books, List<IssueRecord> issues) {
        this.members = new ArrayList<>(members);
        this.books = new ArrayList<>(books);
        this.issues = new ArrayList<>(issues);
        this.timestamp = new Date();
    }

    public List<Member> getMembers() { return members; }
    public List<Book> getBooks() { return books; }
    public List<IssueRecord> getIssues() { return issues; }
    public Date getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return "Snapshot @ " + sdf.format(timestamp)
            + " [Members:" + members.size()
            + " Books:" + books.size()
            + " Issues:" + issues.size() + "]";
    }
}
