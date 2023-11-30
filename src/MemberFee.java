import java.time.LocalDate;

public class MemberFee extends Member {
    private static final int ACTIVE_FEE_UNDER_18 = 1000; //årlig takst
    private static final int ACTIVE_FEE_OVER_18 = 1600;
    private static final int ACTIVE_FEE_OVER_60 = 1200; //penisonist rabat 1600-25%
    private boolean isPensionist;

    public MemberFee(String name, LocalDate birthDate, Address address, String phoneNumber, String mail, boolean isActive, boolean isCompetitive) {
        super(name, birthDate, address, phoneNumber, mail, isActive, isCompetitive);
        this.isPensionist = getAge() >= 60;
    }

    public boolean isPensionist() {
        return isPensionist;
    }

    public void setPensionist() {
        this.isPensionist = getAge() >= 60;
    }

    public int calculateMembershipFee() {
        boolean type = isJunior();

        if (isPensionist()) {
            return ACTIVE_FEE_OVER_60;
        } else if (isJunior()) {
            return ACTIVE_FEE_UNDER_18;
        } else {
            return ACTIVE_FEE_OVER_18;
        }
    }


}
