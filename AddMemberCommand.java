import java.util.List;

// --- COMMAND: Add a Member (with undo support) ---
public class AddMemberCommand implements Command {
    private LibraryManager manager;
    private Member member;

    public AddMemberCommand(LibraryManager manager, Member member) {
        this.manager = manager;
        this.member = member;
    }

    @Override
    public void execute() {
        manager.addMember(member);
    }

    @Override
    public void undo() {
        List<Member> members = manager.getMembers();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).id == member.id) {
                manager.removeMember(i);
                return;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Add Member: " + member.name;
    }
}
