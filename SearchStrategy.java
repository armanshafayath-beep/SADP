import java.util.List;

// =====================================================================
//  STRATEGY PATTERN — Encapsulates different search algorithms
//  so the client can switch between them at runtime.
// =====================================================================
public interface SearchStrategy {
    List<Book> search(List<Book> books, String query);
    String getStrategyName();
}
