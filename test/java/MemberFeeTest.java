import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberFeeTest {

        @Test
        public void testActiveMembershipFee() {
            Address address = new Address("vejvej", "4", "2100", "byby");
            MemberFee activeMember = new MemberFee("John Doe", LocalDate.of(2000, 1, 1),address, "40404040", "gi@hotmali.com", true, false);
            int fee = activeMember.calculateMembershipFee();
            assertEquals(1600, fee);
        }

        @Test
        public void testPassiveMembershipFee() {
            Address address = new Address("vejvej", "4", "2100", "byby");
            PassiveMemberFee passiveMember = new PassiveMemberFee("Jane Doe", LocalDate.of(1980, 1, 1), address, "40403030", "gi@gi.dk");
            int fee = passiveMember.calculateMembershipFee();
            assertEquals(500, fee);
        }

}