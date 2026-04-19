import java.util.Date;

// =====================================================================
//  FACADE PATTERN — Provides a simplified, unified interface to
//  the complex subsystems (Manager, Factory, Adapter).
// =====================================================================
public class LibraryService {
    private LibraryManager manager = LibraryManager.getInstance();

    public void registerNewMember(int id, String name, MembershipType type, String dateStr) throws Exception {
        Date date = LegacyDateAdapter.stringToDate(dateStr);
        manager.addMember(LibraryFactory.createMember(id, name, type, date));
    }

    public void addNewBook(int id, String title, BookCategory cat) {
        manager.addBook(LibraryFactory.createBook(id, title, cat));
    }

    public void issueBook(int iId, int mId, int bId, String dateStr, IssueStatus s) throws Exception {
        Date date = LegacyDateAdapter.stringToDate(dateStr);
        manager.addIssue(LibraryFactory.createIssue(iId, mId, bId, date, s));
    }
}
