import java.util.Date;

// =====================================================================
//  FACTORY PATTERN — Creates domain objects without exposing
//  instantiation logic to the client.
// =====================================================================
public class LibraryFactory {

    public static Member createMember(int id, String name, MembershipType type, Date date) {
        return new Member(id, name, type, date);
    }

    public static Book createBook(int id, String title, BookCategory cat) {
        return new Book(id, title, cat);
    }

    public static IssueRecord createIssue(int iId, int mId, int bId, Date date, IssueStatus s) {
        return new IssueRecord(iId, mId, bId, date, s);
    }
}
