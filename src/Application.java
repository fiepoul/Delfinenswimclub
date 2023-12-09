public class Application {
    public static void main(String[] args) {
        MemberDatabase memberDatabase = new MemberDatabase();
        SwimTeamDatabase swimTeamDatabase = new SwimTeamDatabase("swimTeam.csv");
        FinancialDatabase financialDatabase = new FinancialDatabase("financialDetails.csv");
        SwimTeamController swimTeamController = new SwimTeamController(swimTeamDatabase);
        MemberController memberController = new MemberController(memberDatabase, swimTeamController);
        FinancielController financielController = new FinancielController(memberController, financialDatabase);
        MainUI mainUI = new MainUI(memberController, financielController, swimTeamController);
        mainUI.start();
    }
}
