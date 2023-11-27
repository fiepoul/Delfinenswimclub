import java.time.LocalDate;
import java.util.List;

public class MemberController {
    private MemberDatabase memberDatabase;

    public MemberController(MemberDatabase memberDatabase) {
        this.memberDatabase = memberDatabase;
    }

    public void addMember(Member member) {
        member.setMemberId(generateMemberNumber());
        memberDatabase.saveMember(member);
    }

    public int generateMemberNumber() {
        int year = LocalDate.now().getYear();
        if (year != memberDatabase.getCurrentYear()) {
            memberDatabase.setCurrentYear(year);
            memberDatabase.setNextMemberId(1);
        }
        return Integer.parseInt(year + String.format("%04d", memberDatabase.getNextMemberId()));
    }

    public List<Member> getMembers() {
        return memberDatabase.getMembers();
    }

    public void deleteMember(int memberId) {
        memberDatabase.deleteMember(memberId);
    }

    public void saveAllMembers() {
        memberDatabase.saveMembers();
        memberDatabase.saveNextId();
    }

    public void updateMember(int memberId, String infoType, String newValue) {
        Member member = findMemberById(memberId);
        if (member != null) {
            applyUpdate(member, infoType, newValue);
            memberDatabase.saveMembers(); // Gemmer ændringerne i databasen
        } else {
            System.out.println("Ingen medlem fundet med ID: " + memberId);
        }
    }

    private Member findMemberById(int memberId) {
        return memberDatabase.getMembers().stream()
                .filter(m -> m.getMemberId() == memberId)
                .findFirst()
                .orElse(null);
    }

    private void applyUpdate(Member member, String infoType, String newValue) {
        switch (infoType) {
            case "navn" -> member.setName(newValue);
            case "adresse" -> member.setAddress(newValue);
            case "telefonnummer" -> member.setPhoneNumber(newValue);
            case "e-mail" -> member.setMail(newValue);
            default -> System.out.println("Ugyldig informationstype: " + infoType);
        }
    }

}