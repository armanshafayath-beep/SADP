import java.util.Stack;

// --- COMMAND INVOKER: Stores command history for undo ---
public class CommandInvoker {
    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }

    public String undo() {
        if (history.isEmpty()) return null;
        Command cmd = history.pop();
        cmd.undo();
        return cmd.getDescription();
    }

    public Stack<Command> getHistory() {
        return history;
    }
}
