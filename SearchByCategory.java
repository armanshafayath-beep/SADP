import java.util.List;
import java.util.stream.Collectors;

// --- STRATEGY: Search books by category ---
public class SearchByCategory implements SearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        return books.stream()
            .filter(b -> b.category.name().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public String getStrategyName() {
        return "Search by Category";
    }
}
