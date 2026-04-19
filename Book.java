import java.io.Serializable;

// --- CORE CLASS: Book ---
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String title;
    BookCategory category;

    public Book(int id, String title, BookCategory category) {
        this.id = id;
        this.title = title;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ": " + title;
    }
}
