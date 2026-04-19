// =====================================================================
//  OBSERVER PATTERN — Notifies registered listeners when the
//  library data changes so the GUI can refresh automatically.
// =====================================================================
public interface LibraryObserver {
    void onDataChanged(String message);
}
