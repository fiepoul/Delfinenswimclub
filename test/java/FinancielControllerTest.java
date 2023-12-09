import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancielControllerTest {

    @Test
    public void calculateTotalAmout() {
        SwimTeamDatabase swimTeamDatabase = new SwimTeamDatabase("swimteamfile.csv");
        SwimTeamController swimTeamController = new SwimTeamController(swimTeamDatabase);
        MemberDatabase memberDatabase = new MemberDatabase();
        MemberController memberController = new MemberController(memberDatabase, swimTeamController);
        FinancialDatabase financialDatabase = new FinancialDatabase("financialDetails.csv");
        FinancielController financielController = new FinancielController(memberController, financialDatabase);

        int actual = financielController.calculateYearlyIncome();
        int expected = 2000;

        assertEquals(expected, actual);

    }

}