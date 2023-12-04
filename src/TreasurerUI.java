import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TreasurerUI {
    private Scanner scanner;
    private FinancielController financielController;

    public TreasurerUI(FinancielController financielController) {
        this.scanner = new Scanner(System.in);
        this.financielController = financielController;
    }

    public void start() {
            System.out.println("VELKOMMEN TIL KASSERENS MANAGEMENT SYSTEM");
            boolean running = true;

            while (running) {
                System.out.println("\nMenu: ");
                System.out.println("1: Vis medlemmer i restance");
                System.out.println("2: Opdater medlemsbetaling");
                System.out.println("3: Se samlet kontingentbetaling");
                System.out.println("4: Gem og afslut");

                int choice = promptForInt("Vælg en af mulighederne (1-4): ");
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showPaymentStatus();
                    case 2 -> updatePaymentStatus();
                    case 3 -> showTotalMembershipFees();
                    case 4 -> saveAndExit();
                    default -> System.out.println("Ugyldigt valg. Prøv igen.");
                }
            }
    }

        private void showPaymentStatus() {
            financielController.displayMembersInArrears();
        }

    private void updatePaymentStatus() {
        while (true) {
            System.out.println("Indtast ID på medlemmet, hvis betalingsstatus skal opdateres:");
            int memberId = promptForInt("Indtast medlemsnummer: ");
            scanner.nextLine(); // Ryd bufferen

            System.out.println("Er betalingen gennemført? (ja/nej):");
            boolean isPaymentComplete = scanner.nextLine().trim().equalsIgnoreCase("ja");

            boolean succes = financielController.updatePaymentStatus(memberId, isPaymentComplete);
            if (succes) {
                financielController.saveFinancialDetails();
                System.out.println("Betalingsstatus opdateret.");
            } else {
                System.out.println("Intet medlem fundet med medlemsnummer: " + memberId);
            }

            if (promptForReturnToMenu()) {
                break;
            }
        }
    }

        private boolean promptForReturnToMenu() {
            System.out.println("Ønsker du at vende tilbage til hovedmenuen? (ja/nej):");
            return scanner.nextLine().trim().equalsIgnoreCase("ja");
        }

    private void showTotalMembershipFees() {
        int totalFees = financielController.calculateTotalMembershipFees();
        System.out.println("Samlet kontingentindbetaling: " + totalFees);
    }

    private void saveAndExit(){
        try {
            financielController.saveAllMembers();
            System.out.println("Alle ændringer er blevet gemt. Afslutter programmet.");
        } catch (Exception e) {
            System.err.println("Fejl ved gemning af data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int promptForInt(String message) {
        System.out.println(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Ugyldigt input, prøv venligst igen: ");
            scanner.next(); // Ryd forkert input
        }
        return scanner.nextInt();
    }
}
