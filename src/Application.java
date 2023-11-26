public class Application {
    public static void main(String[] args) {
        MemberDatabase memberDatabase = new MemberDatabase();
        MemberController memberController = new MemberController(memberDatabase);
        ChairmanUI chairmanUI = new ChairmanUI(memberController);
        chairmanUI.start();
    }
}
