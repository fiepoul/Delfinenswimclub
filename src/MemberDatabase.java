import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDatabase {
    private static final String MEMBERS_FILE = "members.ser";
    private static final String NEXT_ID_FILE = "nextId.ser";
    private List<Member> members;
    private int nextMemberId;

    public MemberDatabase() {
        this.members = loadMembers();
        this.nextMemberId = loadNextId();
    }

    public void saveMember(Member member) {
        members.add(member);
        saveMembers();
    }

    public void saveMembers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MEMBERS_FILE))) {
            out.writeObject(members);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Member> loadMembers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(MEMBERS_FILE))) {
            return (List<Member>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void saveNextId() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(NEXT_ID_FILE))) {
            out.writeInt(nextMemberId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadNextId() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(NEXT_ID_FILE))) {
            return in.readInt();
        } catch (IOException e) {
            return 1; // Start fra 1, hvis filen ikke findes
        }
    }

    public void deleteMember(int memberId) {
        members.removeIf(m -> m.getMemberId() == memberId);
        saveMembers();
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    public int getNextMemberId() {
        return nextMemberId++;
    }

    public void setNextMemberId(int nextMemberId) {
        this.nextMemberId = nextMemberId;
    }
}