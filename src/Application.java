public class Application {
    public static void main(String[] args) {
        MemberDatabase memberDatabase = new MemberDatabase();
        MemberController memberController = new MemberController(memberDatabase);
        FinancialDatabase financialDatabase = new FinancialDatabase();
        FinancielController financielController = new FinancielController(memberController, financialDatabase);
        MainUI mainUI = new MainUI(memberController, financielController);
        mainUI.start();
    }
}
