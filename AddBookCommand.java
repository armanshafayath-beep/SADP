import java.util.List;

// --- COMMAND: Add a Book (with undo support) ---
public class AddBookCommand implements Command {
    private LibraryManager manager;
    private Book book;

    public AddBookCommand(LibraryManager manager, Book book) {
        this.manager = manager;
        this.book = book;
    }

    @Override
    public void execute() {
        manager.addBook(book);
    }

    @Override
    public void undo() {
        List<Book> books = manager.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).id == book.id) {
                manager.removeBook(i);
                return;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Add Book: " + book.title;
    }
}
