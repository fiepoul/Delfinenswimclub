import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Memberdatabase {
    private static final String MEMBERS_FILE = "members.ser";
    private static final String NEXT_ID_FILE = "nextId.ser";

    public void saveMembers(List<Member> members) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MEMBERS_FILE))) {
            out.writeObject(members);
        }
    }

    public List<Member> loadMembers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(MEMBERS_FILE))) {
            return (List<Member>) in.readObject();
        } catch (FileNotFoundException e) {
            // Hvis filen ikke findes, returner en tom liste
            return new ArrayList<>();
        }
    }

    public void saveNextId(int nextId) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(NEXT_ID_FILE))) {
            out.writeInt(nextId);
        }
    }

    public int loadNextId() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(NEXT_ID_FILE))) {
            return in.readInt();
        } catch (FileNotFoundException e) {
            // Hvis filen ikke findes, start fra 1
            return 1;
        }
    }
}
