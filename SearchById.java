import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// --- STRATEGY: Search books by ID ---
public class SearchById implements SearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        try {
            int id = Integer.parseInt(query.trim());
            return books.stream()
                .filter(b -> b.id == id)
                .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String getStrategyName() {
        return "Search by ID";
    }
}
