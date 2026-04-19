// =====================================================================
//  COMMAND PATTERN — Encapsulates operations as objects, enabling
//  undo functionality for each action.
// =====================================================================
public interface Command {
    void execute();
    void undo();
    String getDescription();
}
