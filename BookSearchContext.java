import java.util.List;

// --- STRATEGY CONTEXT: Holds current strategy and delegates search ---
public class BookSearchContext {
    private SearchStrategy strategy;

    public BookSearchContext(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public SearchStrategy getStrategy() {
        return strategy;
    }

    public List<Book> executeSearch(List<Book> books, String query) {
        return strategy.search(books, query);
    }
}
