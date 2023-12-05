import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancielControllerTest {

    @Test
    public void calculateTotalAmout() {
        MemberDatabase memberDatabase = new MemberDatabase();
        MemberController memberController = new MemberController(memberDatabase);
        FinancialDatabase financialDatabase = new FinancialDatabase("testmock.csv");
        FinancielController financielController = new FinancielController(memberController, financialDatabase);

        int actual = financielController.calculateTotalMembershipFees();
        int expected = 2000;

        assertEquals(expected, actual);

    }

}