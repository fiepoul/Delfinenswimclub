public class Application {
    public static void main(String[] args) {
        MemberDatabase memberDatabase = new MemberDatabase();
        MemberController memberController = new MemberController(memberDatabase);
        MainUI mainUI = new MainUI(memberController);
        mainUI.start();
    }
}
