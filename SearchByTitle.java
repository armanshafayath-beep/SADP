import java.util.List;
import java.util.stream.Collectors;

// --- STRATEGY: Search books by title ---
public class SearchByTitle implements SearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        return books.stream()
            .filter(b -> b.title.toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public String getStrategyName() {
        return "Search by Title";
    }
}
