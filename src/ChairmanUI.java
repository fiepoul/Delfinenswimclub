import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ChairmanUI {
    private Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private MemberController memberController;

    public ChairmanUI(MemberController memberController) {
        this.scanner = new Scanner(System.in);
        this.memberController = memberController;
    }

    public void start() {
            System.out.println("VELKOMMEN TIL FORMANDENS MANAGEMENT SYSTEM");
            boolean running = true;

            while (running) {
                System.out.println("Menu:");
                System.out.println("1: Tilføj nyt medlem");
                System.out.println("2: Opdater medlemsoplysninger");
                System.out.println("3. Slet medlem");
                System.out.println("4: Vis medlemsliste");
                System.out.println("5: Afslut og gem");

                int choice = promptForInt("Vælg en af mulighederne (1-5): ");
                scanner.nextLine();

                switch (choice) { // todo: i stedet for at menuen kommer op igen, så lav et mere simpelt alternativ.
                    case 1 -> addNewMember();
                    case 2 -> updateMember();
                    case 3 -> deleteMember();
                    case 4 -> showMembers();
                    case 5 -> exitManagement();
                    default -> System.out.println("Ugyldigt valg. Prøv igen: ");
                }
            }
    }

    private void addNewMember() {
        System.out.println("Indtast navn: ");
        String name = scanner.nextLine();
        LocalDate birthDate = null;
        while (birthDate == null) {
            System.out.println("Indtast fødselsdato (dd.MM.yyyy):");
            String birthDateString = scanner.nextLine();
            try {
                birthDate = LocalDate.parse(birthDateString, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldigt datoformat. Prøv igen.");
            }
        }

        System.out.println("Indtast vejnavn:");
        String streetName = scanner.nextLine();
        System.out.println("Indtast husnummer:");
        String houseNumber = scanner.nextLine();
        System.out.println("Indtast postnummer:");
        String zipCode = scanner.nextLine();
        System.out.println("Indtast by:");
        String city = scanner.nextLine();
        Address address = new Address(streetName, houseNumber, zipCode, city);

        System.out.println("Indtast telefonnummer: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Indtast email:");
        String email = scanner.nextLine();
        System.out.println("Er medlemmet aktivt? (ja/nej):");
        boolean isActive = scanner.nextLine().trim().equalsIgnoreCase("ja");
        System.out.println("Er medlemmet en konkurrencesvømmer? (ja/nej):");
        String input = scanner.nextLine().trim();
        Member newMember;

        if (input.equalsIgnoreCase("ja")) {
            newMember = new CompetitiveSwimmer(name, birthDate, address, phoneNumber, email, isActive);
        } else {
            newMember = new Member(name, birthDate, address, phoneNumber, email, isActive);
        }
        memberController.addMember(newMember);

        System.out.println("Nyt medlem tilføjet: " + newMember);

        if (!promptForReturnToMenu()) {
            addNewMember();
        }
    }

    private void updateMember() {
            System.out.println("Nuværende medlemsliste:");
            for (Member member : memberController.getMembers()) {
                System.out.println("ID: " + member.getMemberId() + ", Navn: " + member.getName());
            }

            int memberId = promptForInt("indtast medlemsnummer: ");
            scanner.nextLine();

            String newValue = null;
            boolean correctInfoType = false;
            while (!correctInfoType) {
            System.out.println("Indtast typen af information, der skal opdateres:");
            System.out.println("Mulige valg: navn, adresse, telefonnummer, e-mail");
            String infoType = scanner.nextLine().toLowerCase();

            if (infoType.equals("adresse")) {
                System.out.println("Indtast vejnavn:");
                String streetName = scanner.nextLine();
                System.out.println("Indtast husnummer:");
                String houseNumber = scanner.nextLine();
                System.out.println("Indtast postnummer:");
                String zipCode = scanner.nextLine();
                System.out.println("Indtast by:");
                String city = scanner.nextLine();

                newValue = streetName + ";" + houseNumber + ";" + zipCode + ";" + city;
                correctInfoType = true;
            } else if (infoType.equals("navn") || infoType.equals("telefonnummer") || infoType.equals("e-mail")) {
                System.out.println("Indtast ny værdi:");
                newValue = scanner.nextLine();
                correctInfoType = true;
            } else {
                System.out.println("Ugyldig infotype: " + infoType);
            }

            memberController.updateMember(memberId, infoType, newValue);
            System.out.println("Medlemsoplysningerne er blevet opdateret for medlemsnummer: " + memberId);
            if (!promptForReturnToMenu()) {
                updateMember();
            }
        }
    }

    private int promptForInt(String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input, prøv venligst igen: ");
                scanner.nextLine();
            }
        }
    }

    private boolean promptForReturnToMenu() {
        System.out.println("Ønsker du at vende tilbage til hovedmenuen? (ja/nej):");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("ja");
    }

    private void deleteMember() {
        int memberId = promptForInt("Indtast medlemsnummer: ");
        memberController.deleteMember(memberId);
        System.out.println("Medlem med nummer: " + memberId + ", er slettet.");
        if (!promptForReturnToMenu()) {
            deleteMember();
        }
    }

    private void showMembers() {
        List<Member> members = memberController.getMembers();
        if (members.isEmpty()) {
            System.out.println("Ingen medlemmer at vise.");
        } else {
            for (Member member : members) {
                System.out.println(member);
            }
        }
        if (!promptForReturnToMenu()) {
            showMembers();
        }
    }

    private void exitManagement() {
        try {
            memberController.saveAllMembers();
            System.out.println("Data gemt. Afslutter programmet.");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Fejl ved gemning af data: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
