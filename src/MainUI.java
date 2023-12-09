import java.util.Scanner;

public class MainUI {
    private final Scanner scanner;
    private final LogInService logInService;
    private final MemberController memberController;
    private final FinancielController financielController;
    private final SwimTeamController swimTeamController;

    public MainUI(MemberController memberController, FinancielController financielController, SwimTeamController swimTeamController) {
        this.scanner = new Scanner(System.in);
        this.logInService = new LogInService();
        this.memberController = memberController;
        this.financielController = financielController;
        this.swimTeamController = swimTeamController;
    }

    public void start() {
        System.out.println("Velkommen til medlemsregistrering");

        while (true) {
            System.out.println("Indtast brugernavn: ");
            String username = scanner.nextLine();

            if (!logInService.isUsernameValid(username)) {
                System.out.println("Fejl i brugernavn. Tjek store og små bogstaver. Prøv igen: ");
                continue; // tilbage til starten af løkken
            }

            System.out.println("Indtast kodeord: ");
            String password = scanner.nextLine();

            User loggedInUser = logInService.login(username, password);

            if (loggedInUser != null) {
                showMainMenu(loggedInUser);
                break;
            } else {
                System.out.println("Fejl i kodeord. Prøv igen: ");
            }
        }
    }

    private void showMainMenu(User user) {
        if (user.getRole() == Role.FORMAND) {
            ChairmanUI chairmanUI = new ChairmanUI(memberController);
            chairmanUI.start();
        } else if (user.getRole() == Role.KASSERER) {
            TreasurerUI treasurerUI = new TreasurerUI(financielController);
            treasurerUI.start();
        } else if (user.getRole() == Role.TRÆNER) {
            TrainerUi trainerUi = new TrainerUi(swimTeamController);
            trainerUi.start();
        } else {
            System.out.println("Ugyldig brugerrolle.");
        }
    }
}
