import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberFeeTest {

    @Test
    public void testMembershipCalculation() {
        Address address = new Address("vejvej", "4", "3100", "byby");
        LocalDate localDate = LocalDate.ofEpochDay(1995-07-07);
        MemberFee memberFeeTest = new MemberFee("ditte", localDate, address, "30303030", "fi@fi.dk", false);
        int actual = memberFeeTest.calculateMembershipFee();
        int expected = 500;

        assertEquals(expected, actual);
    }

}