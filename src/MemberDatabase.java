import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MemberDatabase {
    private static final String MEMBERS_FILE = "members.csv";
    private static final String NEXT_ID_FILE = "nextId.ser";
    private static final String CURRENT_YEAR_FILE = "currentYear.ser";

    private static final String AVAILABLE_IDS_FILE = "availableIds.ser";
    private Queue<Integer> availableMemberIds;
    private List<Member> members;
    private int currentYear;
    private int nextMemberId;

    public MemberDatabase() {
        this.members = loadMembers();
        this.nextMemberId = loadNextId();
        this.currentYear = loadCurrentYear();
        this.availableMemberIds = loadAvailableMemberIds();
    }

    public void saveMember(Member member) {
        members.add(member);
        saveMembers();
    }

    public void saveMembers() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(MEMBERS_FILE))) {
            for (Member member : members) {
                if (member != null) {
                    out.println(member.toCsvString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Member> loadMembers() {
        List<Member> loadedMembers = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                loadedMembers.add(Member.fromCsvString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedMembers;
    }

    public void saveNextId() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(NEXT_ID_FILE))) {
            out.writeInt(nextMemberId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCurrentYear() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CURRENT_YEAR_FILE))) {
            out.writeInt(currentYear);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadCurrentYear() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CURRENT_YEAR_FILE))) {
            return in.readInt();
        } catch (IOException e) {
            return LocalDate.now().getYear(); // Returner det aktuelle år, hvis filen ikke findes
        }
    }

    public void saveAvailableMemberIds() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(AVAILABLE_IDS_FILE))) {
            out.writeObject(new LinkedList<>(availableMemberIds));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Queue<Integer> loadAvailableMemberIds() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(AVAILABLE_IDS_FILE))) {
            return (Queue<Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new LinkedList<>();
        }
    }

    public void addAvailableMemberId(int memberId) {
        if (availableMemberIds == null) {
            availableMemberIds = new LinkedList<>();
        }
        availableMemberIds.offer(memberId);
        saveAvailableMemberIds();
    }

    public boolean hasAvailableMemberId() {
        return !availableMemberIds.isEmpty();
    }

    public int getAvailableMemberId() {
        int availableId = availableMemberIds.poll();
        saveAvailableMemberIds();
        return availableId;
    }


    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
        saveCurrentYear();
    }

    public int getNextMemberId() {
        int currentId = nextMemberId;
        nextMemberId++;
        saveNextId();
        return currentId;
    }

    public void setNextMemberId(int nextMemberId) {
        this.nextMemberId = nextMemberId;
    }

    public int loadNextId() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(NEXT_ID_FILE))) {
            return in.readInt();
        } catch (IOException e) {
            return 1; // Starter fra 1, hvis filen ikke findes
        }
    }

    public void resetNextMemberId() {
        this.nextMemberId = 1; // Sæt til 1 eller den ønskede startværdi
        saveNextId();
    }

    public void deleteMember(int memberId) {
        members.removeIf(m -> m.getMemberId() == memberId);
        saveMembers();
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }
}