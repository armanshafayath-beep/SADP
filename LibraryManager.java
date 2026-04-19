import java.io.*;
import java.util.ArrayList;
import java.util.List;

// =====================================================================
//  SINGLETON PATTERN — Ensures only one instance of LibraryManager
//  exists throughout the application.
//  Also acts as the Originator for the MEMENTO PATTERN.
// =====================================================================
public class LibraryManager {
    private static LibraryManager instance;
    private List<Member> members = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<IssueRecord> issues = new ArrayList<>();
    private List<LibraryObserver> observers = new ArrayList<>();

    private LibraryManager() {
        loadAll();
    }

    public static LibraryManager getInstance() {
        if (instance == null) instance = new LibraryManager();
        return instance;
    }

    // --- Observer management ---
    public void addObserver(LibraryObserver o) { observers.add(o); }
    public void removeObserver(LibraryObserver o) { observers.remove(o); }

    private void notifyObservers(String message) {
        for (LibraryObserver o : observers) o.onDataChanged(message);
    }

    // --- Getters ---
    public List<Member> getMembers() { return members; }
    public List<Book> getBooks() { return books; }
    public List<IssueRecord> getIssues() { return issues; }

    // --- Add operations ---
    public void addMember(Member m) {
        members.add(m);
        save("members.dat", members);
        notifyObservers("Member added: " + m.name);
    }

    public void addBook(Book b) {
        books.add(b);
        save("books.dat", books);
        notifyObservers("Book added: " + b.title);
    }

    public void addIssue(IssueRecord i) {
        issues.add(i);
        save("issues.dat", issues);
        notifyObservers("Issue recorded: #" + i.issueId);
    }

    // --- Remove operations ---
    public void removeMember(int index) {
        String name = members.get(index).name;
        members.remove(index);
        save("members.dat", members);
        notifyObservers("Member removed: " + name);
    }

    public void removeBook(int index) {
        String title = books.get(index).title;
        books.remove(index);
        save("books.dat", books);
        notifyObservers("Book removed: " + title);
    }

    public void removeIssue(int index) {
        int id = issues.get(index).issueId;
        issues.remove(index);
        save("issues.dat", issues);
        notifyObservers("Issue removed: #" + id);
    }

    // --- Persistence ---
    private void save(String file, Object data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(data);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void loadAll() {
        members = (ArrayList<Member>) load("members.dat");
        books = (ArrayList<Book>) load("books.dat");
        issues = (ArrayList<IssueRecord>) load("issues.dat");
    }

    private Object load(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return in.readObject();
        } catch (Exception e) { return new ArrayList<>(); }
    }

    // --- MEMENTO: snapshot & restore full state ---
    public LibraryMemento saveState() {
        return new LibraryMemento(
            new ArrayList<>(members),
            new ArrayList<>(books),
            new ArrayList<>(issues)
        );
    }

    public void restoreState(LibraryMemento memento) {
        this.members = new ArrayList<>(memento.getMembers());
        this.books = new ArrayList<>(memento.getBooks());
        this.issues = new ArrayList<>(memento.getIssues());
        save("members.dat", members);
        save("books.dat", books);
        save("issues.dat", issues);
        notifyObservers("State restored from snapshot");
    }
}
