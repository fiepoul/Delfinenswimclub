import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TrainerUi {
    private Scanner scanner;
    private SwimTeamController swimTeamController;

    public TrainerUi(SwimTeamController swimTeamController) {
        this.scanner = new Scanner(System.in);
        this.swimTeamController = swimTeamController;
    }

    public void start() {
        System.out.println("VELKOMMEN TIL TRÆNERENS MANAGEMENT SYSTEM");
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Se top fem svømmere i hver disciplin");
            System.out.println("2: Registrer svømmeresultater");
            System.out.println("3: Vis svømmeresultater");
            System.out.println("4: Vis liste over konkurrencesvømmere");
            System.out.println("5: Afslut");
            int choice = promptForInt("Vælg en af mulighederne (1-5): ");

            switch (choice) {
                case 1 -> displayTopSwimmers();
                case 2 -> registerSwimmerResults();
                case 3 -> displaySwimmerResults();
                case 4 -> displayListAllSwimmers();
                case 5 -> System.exit(0);
                default -> System.out.println("Forkert forsøg. Prøv igen: ");
            }
        }
    }

    private int promptForInt(String message) {
        System.out.println(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Ugyldigt input, prøv venligst igen: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void displayTopSwimmers() {
        System.out.println("Vælg en disciplin:");
        for (Discipline d : Discipline.values()) {
            System.out.println(d.name());
        }
        scanner.nextLine();
        String disciplineChoice = scanner.nextLine().toUpperCase();
        try {
            Discipline discipline = Discipline.valueOf(disciplineChoice);

            System.out.println("Top fem junior svømmere i " + discipline + ":");
            List<CompetitiveSwimmer> topJuniors = swimTeamController.getTopJuniorSwimmers(discipline);
            if (topJuniors.isEmpty()) {
                System.out.println("ingen resultater");
            } else {
                topJuniors.forEach(swimmer -> System.out.println(swimmer.getName() + " - " + swimmer.getBestResult(discipline).getTime()));
            }
            System.out.println("Top fem senior svømmere i " + discipline + ":");
            List<CompetitiveSwimmer> topSeniors = swimTeamController.getTopSeniorSwimmers(discipline);
            if (topSeniors.isEmpty()) {
                System.out.println("ingen resultater");
            } else {
                topSeniors.forEach(swimmer -> System.out.println(swimmer.getName() + " - " + swimmer.getBestResult(discipline).getTime()));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig disciplin valgt.");
        }
    }

    private void displayListAllSwimmers() {
        System.out.println("Liste over svømmere:");
        List<CompetitiveSwimmer> swimmers = swimTeamController.getAllSwimmers();
        swimmers.forEach(swimmer ->
                System.out.println("Medlemsnummer: " + swimmer.getMemberId() + ", Navn: " + swimmer.getName())
        );
    }

    private void registerSwimmerResults() {
        System.out.println("Vælg en svømmer (indtast medlemsnummer):");
        int memberId = scanner.nextInt();
        CompetitiveSwimmer swimmer = swimTeamController.findSwimmerById(memberId);

        if (swimmer == null) {
            System.out.println("Ingen svømmer fundet med medlemsnummer: " + memberId);
            return;
        }
        scanner.nextLine();
        System.out.println("Vælg disciplin:");
        for (Discipline d : Discipline.values()) {
            System.out.println(d.name());
        }
        String disciplineChoice = scanner.nextLine().toUpperCase();
        Discipline discipline;
        try {
            discipline = Discipline.valueOf(disciplineChoice);
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig disciplin valgt.");
            return;
        }

        System.out.println("Indtast dato for resultat (format dd.MM.yyyy):");
        String dateString = scanner.nextLine();
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            date = LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ugyldigt datoformat.");
            return;
        }

        System.out.println("Indtast tid (format mm:ss.SS):");
        String time = scanner.nextLine();

        // TODO indtastning af konkurrencenavn og placering om det er en konkurrenceresult

        Result result = new Result(discipline, date, time);
        swimTeamController.recordSwimmerResult(memberId, result);

        System.out.println("Resultat registreret for svømmer med medlemsnummer " + memberId);
    }

    private void displaySwimmerResults() {
        System.out.println("Vælg en svømmer (indtast medlemsnummer):");
        int memberId = scanner.nextInt();
        scanner.nextLine(); // Ryd scannerens buffer

        CompetitiveSwimmer swimmer = swimTeamController.findSwimmerById(memberId);
        if (swimmer == null) {
            System.out.println("Ingen svømmer fundet med dette medlemsnummer.");
            return;
        }

        System.out.println("Resultater for svømmer: " + swimmer.getName() + "(" + swimmer.getMemberId() + ")");
        System.out.println("Træningsresultater:");
        for (Discipline discipline : Discipline.values()) {
            Result trainingResult = swimmer.getBestResult(discipline);
            if (trainingResult != null) {
                System.out.println(discipline.name() + " - Tid: " + trainingResult.getTime() + " på dato " + trainingResult.getDate());
            }
        }

        System.out.println("Konkurrenceresultater:");
        for (Discipline discipline : Discipline.values()) {
            ResultCompetition competitionResult = (ResultCompetition) swimmer.getBestCompetitionResult(discipline);
            if (competitionResult != null) {
                System.out.println(discipline.name() + " - Tid: " + competitionResult.getTime() + " på dato " + competitionResult.getDate() +
                        ", Stævne: " + competitionResult.getCompetitionName() + ", Placering: " + competitionResult.getRank());
            }
        }
    }



}
