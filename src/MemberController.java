import java.time.LocalDate;
import java.util.List;

public class MemberController {
    private MemberDatabase memberDatabase;

    public MemberController(MemberDatabase memberDatabase) {
        this.memberDatabase = memberDatabase;
    }

    public void addMember(Member member) {
        int memberId = memberDatabase.hasAvailableMemberId() ? memberDatabase.getAvailableMemberId() : generateMemberNumber();
        member.setMemberId(memberId);
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
        memberDatabase.addAvailableMemberId(memberId);
    }

    public void saveAllMembers() {
        try {
            memberDatabase.saveMembers();
            memberDatabase.saveNextId();
        } catch (Exception e) {
            System.err.println("Fejl under gemning af medlemmer: " + e.getMessage());
        }
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

    public Member findMemberById(int memberId) {
        return memberDatabase.getMembers().stream()
                .filter(m -> m.getMemberId() == memberId)
                .findFirst()
                .orElse(null);
    }

    private void applyUpdate(Member member, String infoType, String newValue) {
        switch (infoType) {
            case "navn" -> member.setName(newValue);
            case "adresse" -> {
                String[] addressParts = newValue.split(";");
                if (addressParts.length == 4) {
                    Address newAddress = new Address(addressParts[0], addressParts[1], addressParts[2], addressParts[3]);
                    member.setAddress(newAddress);
                } else {
                    System.out.println("Ugyldig adresseformat.");
                }
            }
            case "telefonnummer" -> member.setPhoneNumber(newValue);
            case "e-mail" -> member.setMail(newValue);
            default -> System.out.println("Ugyldig informationstype: " + infoType);
        }
    }

}