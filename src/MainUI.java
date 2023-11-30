import java.util.Scanner;

public class MainUI {
    private Scanner scanner;
    private LogInService logInService;
    private MemberController memberController;

    public MainUI(MemberController memberController) {
        this.scanner = new Scanner(System.in);
        this.logInService = new LogInService();
        this.memberController = memberController;
    }

    public void start() {
        System.out.println("Velkommen til medlemsregistrering");

        while (true) {
            System.out.println("Indtast brugernavn: ");
            String username = scanner.nextLine();
            System.out.println("Indtast kodeord: ");
            String password = scanner.nextLine();

            User loggedInUser = logInService.login(username, password);

            if (loggedInUser != null) {
                showMainMenu(loggedInUser);
                break;
            } else {
                System.out.println("Fejl i brugernavn eller kodeord. Prøv igen: ");
            }
        }
    }

    private void showMainMenu(User user) {
        if (user.getRole() == Role.FORMAND) {
            ChairmanUI chairmanUI = new ChairmanUI(memberController);
            chairmanUI.start();
        } else if (user.getRole() == Role.KASSERER) {
            TreasurerUI treasurerUI = new TreasurerUI(memberController);
            treasurerUI.start();
        } else {
            System.out.println("Ugyldig brugerrolle.");
        }
    }
}
