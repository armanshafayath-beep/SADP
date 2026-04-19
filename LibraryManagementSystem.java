import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// =====================================================================
//  GUI IMPLEMENTATION — Integrates all 8 design patterns:
//    1. Factory     → LibraryFactory
//    2. Singleton   → LibraryManager
//    3. Observer    → LibraryObserver (this class implements it)
//    4. Adapter     → LegacyDateAdapter
//    5. Facade      → LibraryService
//    6. Strategy    → SearchStrategy + BookSearchContext
//    7. Command     → Command + CommandInvoker
//    8. Memento     → LibraryMemento + MementoCaretaker
// =====================================================================
public class LibraryManagementSystem extends JFrame implements LibraryObserver {

    // --- FACADE ---
    private LibraryService facade = new LibraryService();

    // --- SINGLETON ---
    private LibraryManager manager = LibraryManager.getInstance();

    // --- COMMAND ---
    private CommandInvoker invoker = new CommandInvoker();

    // --- STRATEGY ---
    private BookSearchContext searchContext = new BookSearchContext(new SearchByTitle());

    // --- MEMENTO ---
    private MementoCaretaker caretaker = new MementoCaretaker();

    // --- GUI Components ---
    private DefaultTableModel memberModel, bookModel, issueModel, searchResultModel;
    private JComboBox<Integer> cmbMemberIds = new JComboBox<>(), cmbBookIds = new JComboBox<>();
    private JTextArea logArea;
    private JLabel statusLabel;
    private DefaultListModel<String> historyListModel = new DefaultListModel<>();

    // --- Color palette ---
    private static final Color BG_DARK = new Color(30, 30, 46);
    private static final Color BG_PANEL = new Color(40, 42, 58);
    private static final Color BG_INPUT = new Color(55, 58, 80);
    private static final Color ACCENT = new Color(137, 180, 250);
    private static final Color ACCENT_GREEN = new Color(166, 227, 161);
    private static final Color ACCENT_RED = new Color(243, 139, 168);
    private static final Color ACCENT_YELLOW = new Color(249, 226, 175);
    private static final Color TEXT_PRIMARY = new Color(205, 214, 244);
    private static final Color TEXT_SECONDARY = new Color(147, 153, 178);

    public LibraryManagementSystem() {
        manager.addObserver(this);  // OBSERVER
        setupUI();
        onDataChanged("System initialized");
    }

    // --- OBSERVER callback ---
    @Override
    public void onDataChanged(String message) {
        refreshTables();
        refreshCombos();
        log(message);
    }

    private void log(String message) {
        if (logArea != null) {
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            logArea.append("[" + time + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
        if (statusLabel != null) {
            statusLabel.setText("  " + message);
        }
    }

    private void setupUI() {
        setTitle("Pattern-Oriented Library Management System");
        setSize(1100, 780);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_PANEL);
        tabs.setForeground(TEXT_PRIMARY);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.add("Members", createMemberPanel());
        tabs.add("Books", createBookPanel());
        tabs.add("Issues", createIssuePanel());
        tabs.add("Search (Strategy)", createSearchPanel());
        tabs.add("Snapshots (Memento)", createMementoPanel());
        tabs.add("History (Command)", createCommandHistoryPanel());

        // Status bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(BG_DARK);
        statusBar.setBorder(new EmptyBorder(4, 8, 4, 8));
        statusLabel = new JLabel("  Ready");
        statusLabel.setForeground(ACCENT_GREEN);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.add(statusLabel, BorderLayout.WEST);

        JLabel patternLabel = new JLabel("Patterns: Adapter | Singleton | Facade | Factory | Strategy | Command | Observer | Memento  ");
        patternLabel.setForeground(TEXT_SECONDARY);
        patternLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        statusBar.add(patternLabel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }

    // ==================== Styled Helpers ====================

    private JPanel styledPanel() {
        JPanel p = new JPanel();
        p.setBackground(BG_PANEL);
        return p;
    }

    private JTextField styledTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setBackground(BG_INPUT);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 73, 100), 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        tf.setToolTipText(placeholder);
        return tf;
    }

    private JButton styledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(BG_DARK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel styledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return lbl;
    }

    private void styleTable(JTable table) {
        table.setBackground(BG_INPUT);
        table.setForeground(TEXT_PRIMARY);
        table.setGridColor(new Color(70, 73, 100));
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(BG_DARK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_DARK);
        header.setForeground(ACCENT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    // ==================== MEMBER PANEL ====================

    private JPanel createMemberPanel() {
        JPanel p = styledPanel();
        p.setLayout(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));

        memberModel = new DefaultTableModel(new String[]{"ID", "Name", "Type", "Joined"}, 0);
        JTable table = new JTable(memberModel);
        styleTable(table);

        JTextField txtId = styledTextField("Member ID");
        JTextField txtName = styledTextField("Member Name");
        JComboBox<MembershipType> cmbType = new JComboBox<>(MembershipType.values());
        cmbType.setBackground(BG_INPUT); cmbType.setForeground(TEXT_PRIMARY);
        JTextField txtDate = styledTextField("Date (yyyy-MM-dd)");
        txtDate.setText("2025-01-01");

        JButton btnAdd = styledButton("+ Add Member (Command)", ACCENT_GREEN);
        JButton btnUndo = styledButton("Undo Last", ACCENT_RED);

        // Uses COMMAND pattern for add
        btnAdd.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                MembershipType type = (MembershipType) cmbType.getSelectedItem();
                Date date = LegacyDateAdapter.stringToDate(txtDate.getText()); // ADAPTER
                Member m = LibraryFactory.createMember(id, name, type, date);  // FACTORY
                Command cmd = new AddMemberCommand(manager, m);                // COMMAND
                invoker.executeCommand(cmd);
                updateHistoryList();
                txtId.setText(""); txtName.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Validation", JOptionPane.ERROR_MESSAGE);
            }
        });

        // COMMAND undo
        btnUndo.addActionListener(e -> {
            if (invoker.canUndo()) {
                String desc = invoker.undo();
                log("UNDO: " + desc);
                updateHistoryList();
            } else {
                JOptionPane.showMessageDialog(this, "Nothing to undo!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel form = styledPanel();
        form.setLayout(new GridLayout(6, 2, 8, 6));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(styledLabel("ID:")); form.add(txtId);
        form.add(styledLabel("Name:")); form.add(txtName);
        form.add(styledLabel("Type:")); form.add(cmbType);
        form.add(styledLabel("Date (Adapter):")); form.add(txtDate);
        form.add(btnAdd); form.add(btnUndo);

        // Observer log area
        logArea = new JTextArea(4, 30);
        logArea.setEditable(false);
        logArea.setBackground(BG_DARK);
        logArea.setForeground(ACCENT_GREEN);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ACCENT, 1), "Observer Log",
            0, 0, new Font("Segoe UI", Font.BOLD, 11), ACCENT));

        JPanel topPanel = styledPanel();
        topPanel.setLayout(new BorderLayout(10, 10));
        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(logScroll, BorderLayout.SOUTH);

        p.add(topPanel, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    // ==================== BOOK PANEL ====================

    private JPanel createBookPanel() {
        JPanel p = styledPanel();
        p.setLayout(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));

        bookModel = new DefaultTableModel(new String[]{"ID", "Title", "Category"}, 0);
        JTable table = new JTable(bookModel);
        styleTable(table);

        JTextField txtId = styledTextField("Book ID");
        JTextField txtTitle = styledTextField("Book Title");
        JComboBox<BookCategory> cmbCat = new JComboBox<>(BookCategory.values());
        cmbCat.setBackground(BG_INPUT); cmbCat.setForeground(TEXT_PRIMARY);

        JButton btnAdd = styledButton("+ Add Book (Command)", ACCENT_GREEN);
        JButton btnUndo = styledButton("Undo Last", ACCENT_RED);

        // Uses COMMAND pattern for add
        btnAdd.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String title = txtTitle.getText().trim();
                BookCategory cat = (BookCategory) cmbCat.getSelectedItem();
                Book book = LibraryFactory.createBook(id, title, cat);      // FACTORY
                Command cmd = new AddBookCommand(manager, book);            // COMMAND
                invoker.executeCommand(cmd);
                updateHistoryList();
                txtId.setText(""); txtTitle.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Validation", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUndo.addActionListener(e -> {
            if (invoker.canUndo()) {
                String desc = invoker.undo();
                log("UNDO: " + desc);
                updateHistoryList();
            } else {
                JOptionPane.showMessageDialog(this, "Nothing to undo!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel form = styledPanel();
        form.setLayout(new GridLayout(5, 2, 8, 6));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(styledLabel("ID:")); form.add(txtId);
        form.add(styledLabel("Title:")); form.add(txtTitle);
        form.add(styledLabel("Category:")); form.add(cmbCat);
        form.add(btnAdd); form.add(btnUndo);

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    // ==================== ISSUE PANEL ====================

    private JPanel createIssuePanel() {
        JPanel p = styledPanel();
        p.setLayout(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));

        issueModel = new DefaultTableModel(new String[]{"IssueID", "MemberID", "BookID", "Date", "Status"}, 0);
        JTable table = new JTable(issueModel);
        styleTable(table);

        JTextField txtId = styledTextField("Issue ID");
        JTextField txtDate = styledTextField("Date (yyyy-MM-dd)");
        txtDate.setText("2025-01-01");

        JButton btnIssue = styledButton("Issue Book (Command)", ACCENT);
        JButton btnUndo = styledButton("Undo Last", ACCENT_RED);

        // Uses COMMAND + FACADE + ADAPTER + FACTORY
        btnIssue.addActionListener(e -> {
            try {
                int iId = Integer.parseInt(txtId.getText().trim());
                int mId = (int) cmbMemberIds.getSelectedItem();
                int bId = (int) cmbBookIds.getSelectedItem();
                Date date = LegacyDateAdapter.stringToDate(txtDate.getText());  // ADAPTER
                IssueRecord record = LibraryFactory.createIssue(iId, mId, bId, date, IssueStatus.ISSUED); // FACTORY
                Command cmd = new IssueBookCommand(manager, record);            // COMMAND
                invoker.executeCommand(cmd);
                updateHistoryList();
                txtId.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Issue Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUndo.addActionListener(e -> {
            if (invoker.canUndo()) {
                String desc = invoker.undo();
                log("UNDO: " + desc);
                updateHistoryList();
            } else {
                JOptionPane.showMessageDialog(this, "Nothing to undo!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel form = styledPanel();
        form.setLayout(new GridLayout(5, 2, 8, 6));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(styledLabel("Issue ID:")); form.add(txtId);
        form.add(styledLabel("Member ID:")); form.add(cmbMemberIds);
        form.add(styledLabel("Book ID:")); form.add(cmbBookIds);
        form.add(styledLabel("Date (Adapter):")); form.add(txtDate);
        form.add(btnIssue); form.add(btnUndo);

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    // ==================== SEARCH PANEL (STRATEGY) ====================

    private JPanel createSearchPanel() {
        JPanel p = styledPanel();
        p.setLayout(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));

        searchResultModel = new DefaultTableModel(new String[]{"ID", "Title", "Category"}, 0);
        JTable resultTable = new JTable(searchResultModel);
        styleTable(resultTable);

        JTextField txtQuery = styledTextField("Enter search query...");

        // Strategy selection
        SearchStrategy[] strategies = { new SearchByTitle(), new SearchByCategory(), new SearchById() };
        JComboBox<String> cmbStrategy = new JComboBox<>();
        cmbStrategy.setBackground(BG_INPUT); cmbStrategy.setForeground(TEXT_PRIMARY);
        for (SearchStrategy s : strategies) cmbStrategy.addItem(s.getStrategyName());

        cmbStrategy.addActionListener(e -> {
            searchContext.setStrategy(strategies[cmbStrategy.getSelectedIndex()]);
            log("Strategy changed to: " + strategies[cmbStrategy.getSelectedIndex()].getStrategyName());
        });

        JButton btnSearch = styledButton("Search", ACCENT);
        JButton btnShowAll = styledButton("Show All", ACCENT_YELLOW);

        btnSearch.addActionListener(e -> {
            String query = txtQuery.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a search query", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            List<Book> results = searchContext.executeSearch(manager.getBooks(), query);  // STRATEGY
            searchResultModel.setRowCount(0);
            for (Book b : results) searchResultModel.addRow(new Object[]{b.id, b.title, b.category});
            log("Strategy [" + searchContext.getStrategy().getStrategyName() + "] found " + results.size() + " results for: " + query);
        });

        btnShowAll.addActionListener(e -> {
            searchResultModel.setRowCount(0);
            for (Book b : manager.getBooks()) searchResultModel.addRow(new Object[]{b.id, b.title, b.category});
            log("Showing all " + manager.getBooks().size() + " books");
        });

        JPanel form = styledPanel();
        form.setLayout(new GridLayout(3, 2, 8, 6));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(styledLabel("Strategy:")); form.add(cmbStrategy);
        form.add(styledLabel("Query:")); form.add(txtQuery);
        form.add(btnSearch); form.add(btnShowAll);

        JLabel info = new JLabel("<html><b>Strategy Pattern:</b> Switch the search algorithm at runtime "
            + "— by Title, by Category, or by ID. Each strategy encapsulates a different search behavior.</html>");
        info.setForeground(TEXT_SECONDARY);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        info.setBorder(new EmptyBorder(5, 10, 5, 10));

        JPanel topPanel = styledPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(info, BorderLayout.SOUTH);

        p.add(topPanel, BorderLayout.NORTH);
        p.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        return p;
    }

    // ==================== MEMENTO PANEL ====================

    private JPanel createMementoPanel() {
        JPanel p = styledPanel();
        p.setLayout(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));

        DefaultListModel<String> snapshotListModel = new DefaultListModel<>();
        JList<String> snapshotList = new JList<>(snapshotListModel);
        snapshotList.setBackground(BG_INPUT);
        snapshotList.setForeground(TEXT_PRIMARY);
        snapshotList.setSelectionBackground(ACCENT);
        snapshotList.setSelectionForeground(BG_DARK);
        snapshotList.setFont(new Font("Consolas", Font.PLAIN, 13));

        JButton btnSave = styledButton("Save Snapshot", ACCENT_GREEN);
        JButton btnRestore = styledButton("Restore Selected", ACCENT_YELLOW);

        btnSave.addActionListener(e -> {
            LibraryMemento memento = manager.saveState();     // MEMENTO (Originator)
            caretaker.saveSnapshot(memento);                  // MEMENTO (Caretaker)
            snapshotListModel.addElement(memento.toString());
            log("Snapshot saved: " + memento);
        });

        btnRestore.addActionListener(e -> {
            int index = snapshotList.getSelectedIndex();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Select a snapshot to restore", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            LibraryMemento memento = caretaker.getSnapshot(index);  // MEMENTO (Caretaker)
            if (memento != null) {
                manager.restoreState(memento);                      // MEMENTO (Originator)
                log("Restored snapshot: " + memento);
            }
        });

        JPanel btnPanel = styledPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnPanel.add(btnSave);
        btnPanel.add(btnRestore);

        JLabel info = new JLabel("<html><b>Memento Pattern:</b> Save the entire library state as a snapshot. "
            + "Restore any previous state without exposing internal data structures. "
            + "The Caretaker stores mementos; the Originator (LibraryManager) creates and restores them.</html>");
        info.setForeground(TEXT_SECONDARY);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        info.setBorder(new EmptyBorder(5, 10, 10, 10));

        JPanel topPanel = styledPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(btnPanel, BorderLayout.NORTH);
        topPanel.add(info, BorderLayout.SOUTH);

        p.add(topPanel, BorderLayout.NORTH);
        p.add(new JScrollPane(snapshotList), BorderLayout.CENTER);
        return p;
    }

    // ==================== COMMAND HISTORY PANEL ====================

    private JPanel createCommandHistoryPanel() {
        JPanel p = styledPanel();
        p.setLayout(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));

        JList<String> historyList = new JList<>(historyListModel);
        historyList.setBackground(BG_INPUT);
        historyList.setForeground(TEXT_PRIMARY);
        historyList.setSelectionBackground(ACCENT);
        historyList.setSelectionForeground(BG_DARK);
        historyList.setFont(new Font("Consolas", Font.PLAIN, 13));

        JButton btnUndoLast = styledButton("Undo Last Command", ACCENT_RED);
        btnUndoLast.addActionListener(e -> {
            if (invoker.canUndo()) {
                String desc = invoker.undo();
                log("UNDO: " + desc);
                updateHistoryList();
            } else {
                JOptionPane.showMessageDialog(this, "No commands to undo!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JLabel info = new JLabel("<html><b>Command Pattern:</b> Every Add/Issue operation is wrapped in a Command object. "
            + "Commands are stored in a stack by the Invoker. You can undo the last operation at any time. "
            + "Each command encapsulates execute() and undo() logic.</html>");
        info.setForeground(TEXT_SECONDARY);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        info.setBorder(new EmptyBorder(5, 10, 10, 10));

        JPanel topPanel = styledPanel();
        topPanel.setLayout(new BorderLayout());
        JPanel btnPanel = styledPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnPanel.add(btnUndoLast);
        topPanel.add(btnPanel, BorderLayout.NORTH);
        topPanel.add(info, BorderLayout.SOUTH);

        p.add(topPanel, BorderLayout.NORTH);
        p.add(new JScrollPane(historyList), BorderLayout.CENTER);
        return p;
    }

    // ==================== Data Refresh ====================

    private void updateHistoryList() {
        historyListModel.clear();
        int i = 1;
        for (Command cmd : invoker.getHistory()) {
            historyListModel.addElement(i + ". " + cmd.getDescription());
            i++;
        }
    }

    private void refreshTables() {
        if (memberModel != null) {
            memberModel.setRowCount(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Member m : manager.getMembers())
                memberModel.addRow(new Object[]{m.id, m.name, m.type, sdf.format(m.joinDate)});
        }

        if (bookModel != null) {
            bookModel.setRowCount(0);
            for (Book b : manager.getBooks())
                bookModel.addRow(new Object[]{b.id, b.title, b.category});
        }

        if (issueModel != null) {
            issueModel.setRowCount(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (IssueRecord i : manager.getIssues())
                issueModel.addRow(new Object[]{i.issueId, i.memberId, i.bookId, sdf.format(i.issueDate), i.status});
        }
    }

    private void refreshCombos() {
        cmbMemberIds.removeAllItems();
        for (Member m : manager.getMembers()) cmbMemberIds.addItem(m.id);
        cmbBookIds.removeAllItems();
        for (Book b : manager.getBooks()) cmbBookIds.addItem(b.id);
    }

    // ==================== Main Entry Point ====================

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LibraryManagementSystem().setVisible(true));
    }
}