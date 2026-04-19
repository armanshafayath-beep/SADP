import java.util.ArrayList;
import java.util.List;

// =====================================================================
//  MEMENTO PATTERN (Caretaker) — Manages multiple mementos without
//  knowing their internal structure.
// =====================================================================
public class MementoCaretaker {
    private List<LibraryMemento> snapshots = new ArrayList<>();

    public void saveSnapshot(LibraryMemento memento) {
        snapshots.add(memento);
    }

    public LibraryMemento getSnapshot(int index) {
        if (index >= 0 && index < snapshots.size()) return snapshots.get(index);
        return null;
    }

    public List<LibraryMemento> getSnapshots() { return snapshots; }

    public int size() { return snapshots.size(); }
}
