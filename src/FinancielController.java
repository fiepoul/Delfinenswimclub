import java.util.List;

public class FinancielController {
    private MemberController memberController;
    private FinancialDatabase financialDatabase;

    public FinancielController(MemberController memberController, FinancialDatabase financialDatabase) {
        this.memberController = memberController;
        this.financialDatabase = financialDatabase;
    }

    public int calculateTotalMembershipFees() {
        int totalFees = 0;
        List<Member> members = memberController.getMembers();
        for (Member member : members) {
            totalFees += member.calculateMembershipFee();
        }
        return totalFees;
    }

    public void displayMembersInArrears() {
        List<Member> members = memberController.getMembers();
        System.out.println("Medlemmer i restance:");
        for (Member member : members) {
            if (!member.isPaymentComplete()) {
                System.out.println("Medlem: " + member.getName() + " (ID: " + member.getMemberId() + ") - Restbetaling: " + member.calculateMembershipFee());
            }
        }
    }

    public boolean updatePaymentStatus(int memberId, boolean status) {
        Member member = memberController.findMemberById(memberId);
        if (member != null) {
            member.setPaymentComplete(status);
            financialDatabase.saveFinancialDetails(memberController.getMembers());
            return true;
        } else {
            return false;
        }
    }

    public void saveAllMembers() {
        memberController.saveAllMembers();
    }

    public void saveFinancialDetails() {
        List<Member> members = memberController.getMembers();
        financialDatabase.saveFinancialDetails(members);
    }
}
