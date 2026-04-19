import java.io.Serializable;
import java.util.Date;

// --- CORE CLASS: Issue Record ---
public class IssueRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    int issueId;
    int memberId;
    int bookId;
    Date issueDate;
    IssueStatus status;

    public IssueRecord(int issueId, int memberId, int bookId, Date issueDate, IssueStatus status) {
        this.issueId = issueId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.status = status;
    }
}
