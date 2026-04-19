import java.io.Serializable;
import java.util.Date;

// --- CORE CLASS: Member ---
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    MembershipType type;
    Date joinDate;

    public Member(int id, String name, MembershipType type, Date joinDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}
