import java.util.List;

// --- COMMAND: Issue a Book (with undo support) ---
public class IssueBookCommand implements Command {
    private LibraryManager manager;
    private IssueRecord record;

    public IssueBookCommand(LibraryManager manager, IssueRecord record) {
        this.manager = manager;
        this.record = record;
    }

    @Override
    public void execute() {
        manager.addIssue(record);
    }

    @Override
    public void undo() {
        List<IssueRecord> issues = manager.getIssues();
        for (int i = 0; i < issues.size(); i++) {
            if (issues.get(i).issueId == record.issueId) {
                manager.removeIssue(i);
                return;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Issue Book: #" + record.issueId;
    }
}
