import java.time.LocalDate;

public class PassiveMemberFee extends MemberFee {
    private static final int PASSIVE_MEMBERSHIP_FEE = 500;
    public PassiveMemberFee(String name, LocalDate birthDate, Address address, String phoneNumber, String mail) {
        super(name, birthDate, address, phoneNumber, mail, false, false);
    }

    @Override
    public int calculateMembershipFee() {
        return PASSIVE_MEMBERSHIP_FEE; // Samme gebyr for alle passive medlemmer ligegyldig alder
    }
}
