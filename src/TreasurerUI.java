import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TreasurerUI {
    private Scanner scanner;
    MemberController memberController;

    public TreasurerUI(MemberController memberController) {
        this.scanner = new Scanner(System.in);
        this.memberController = memberController;
    }

    public void start() {
            System.out.println("VELKOMMEN TIL KASSERENS MANAGEMENT SYSTEM");
            boolean running = true;

            while (running) {
                System.out.println("\nMenu: ");
                System.out.println("1: Se medlemsbetalingstatus");
                System.out.println("2: Opdater medlemsbetaling");
                System.out.println("3: Gem og afslut");

                int choice = promptForInt("Vælg en af mulighederne (1-3): ");
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showPaymentStatus();
                    case 2 -> updatePaymentStatus();
                    case 3 -> saveAndExit();
                    default -> System.out.println("Ugyldigt valg. Prøv igen.");
                }
            }
    }

        private void showPaymentStatus() {
            List<Member> members = memberController.getMembers();
            if (members.isEmpty()) {
                System.out.println("ingen medlemmer på listen");
            } else {
                System.out.println("Medlemsbetalingstatus: ");
            } for (Member member : members) {
                String status = member.isPaymentComplete() ? "Betalt" : "Ubetalt";
                System.out.println("Medlem: " + member.getName() + " (ID: " + member.getMemberId() + ") - Status: " + status);
            }
        }

    private void updatePaymentStatus() {
        System.out.println("Indtast ID på medlemmet, hvis betalingsstatus skal opdateres:");
        int memberId = promptForInt("Indtast medlemsnummer: ");
        scanner.nextLine(); // Ryd bufferen

        System.out.println("Er betalingen gennemført? (ja/nej):");
        boolean isPaymentComplete = scanner.nextLine().trim().equalsIgnoreCase("ja");

        memberController.updatePaymentStatus(memberId, isPaymentComplete);
        System.out.println("Betalingsstatus opdateret.");
    }

    private void saveAndExit(){
        try {
            memberController.saveAllMembers();
            System.out.println("Alle ændringer er blevet gemt. Afslutter programmet.");
        } catch (Exception e) {
            System.err.println("Fejl ved gemning af data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int promptForInt(String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input, prøv venligst igen: ");
                scanner.nextLine(); // Ryd bufferen
            }
        }
    }
}
