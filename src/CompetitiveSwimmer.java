import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CompetitiveSwimmer extends Member {
    private Map<Discipline, Result> trainingResults;
    private Map<Discipline, ResultCompetition> competitionResults;

    public CompetitiveSwimmer(String name, LocalDate birthDate, Address address, String phoneNumber, String mail, boolean isActive) {
        super(name, birthDate, address, phoneNumber, mail, isActive);
        this.trainingResults = new HashMap<>();
        this.competitionResults = new HashMap<>();
    }


    public void addTrainingResult(Result result) {
        Discipline discipline = result.getDiscipline(); // Få disciplinen fra result objektet
        if (!trainingResults.containsKey(discipline) || isBetterResult(trainingResults.get(discipline), result)) {
            trainingResults.put(discipline, result);
        }
    }

    public void addCompetitionResult(ResultCompetition result) {
        Discipline discipline = result.getDiscipline(); // Få disciplinen fra ResultCompetition objektet
        if (!competitionResults.containsKey(discipline) || isBetterCompetitionResult(competitionResults.get(discipline), result)) {
            competitionResults.put(discipline, result);
        }
    }

    private boolean isBetterResult(Result existingResult, Result newResult) {
        // Antager at lavere tid er bedre
        return newResult.getTimeInSeconds() < existingResult.getTimeInSeconds();
    }

    private boolean isBetterCompetitionResult(ResultCompetition existingResult, ResultCompetition newResult) {
        // Sammenlign først tiderne. Lavere tid er bedre.
        if (newResult.getTimeInSeconds() < existingResult.getTimeInSeconds()) {
            return true;
        }

        // Hvis tiderne er ens, så brug datoen som tie-breaker. Nyere dato er bedre.
        if (newResult.getTimeInSeconds() == existingResult.getTimeInSeconds()) {
            return newResult.getDate().isAfter(existingResult.getDate());
        }

        // Hvis den nye tid er højere, er det ikke et bedre resultat.
        return false;
    }

    public Result getBestResult(Discipline discipline) {
        Result bestTrainingResult = trainingResults.get(discipline);
        ResultCompetition bestCompetitionResult = competitionResults.get(discipline);

        if (bestTrainingResult == null && bestCompetitionResult == null) {
            return null;
        } else if (bestTrainingResult == null) {
            return bestCompetitionResult;
        } else if (bestCompetitionResult == null) {
            return bestTrainingResult;
        } else {
            if (bestTrainingResult instanceof ResultCompetition && bestCompetitionResult instanceof ResultCompetition) {
                return isBetterCompetitionResult((ResultCompetition)bestTrainingResult, bestCompetitionResult)
                        ? bestTrainingResult
                        : bestCompetitionResult;
            } else {
                return isBetterResult(bestTrainingResult, bestCompetitionResult)
                        ? bestTrainingResult
                        : bestCompetitionResult;
            }
        }
    }

    public ResultCompetition getBestCompetitionResult(Discipline discipline) {
        return competitionResults.get(discipline);
    }

    public boolean isJunior() {
        return getAge() < 18;
    }

    public static CompetitiveSwimmer fromCsvString(String csvString) {
        if (csvString == null || csvString.trim().isEmpty()) {
            return null;
        }
        String[] parts = csvString.split(",");
        if (parts.length < 13) {//maaske
            return null;
        }
        try {
            LocalDate.parse(parts[2]);
        } catch (DateTimeParseException e) {
            return null;
        }
        int memberId = Integer.parseInt(parts[0]);
        String name = parts[1];
        LocalDate birthDate = LocalDate.parse(parts[2]);
        Address address = new Address(parts[3], parts[4], parts[5], parts[6]);
        String phoneNumber = parts[7];
        String mail = parts[8];
        boolean isJunior = Boolean.parseBoolean(parts[9]);
        boolean isActive = Boolean.parseBoolean(parts[10]);
        LocalDate registrationDate = LocalDate.parse(parts[11]);
        boolean paymentComplete = Boolean.parseBoolean(parts[12]);

        CompetitiveSwimmer swimmer = new CompetitiveSwimmer(name, birthDate, address, phoneNumber, mail, isActive);
        swimmer.setMemberId(memberId);
        swimmer.setRegistrationDate(registrationDate);
        swimmer.setPaymentComplete(paymentComplete);

        return swimmer;
    }
}
