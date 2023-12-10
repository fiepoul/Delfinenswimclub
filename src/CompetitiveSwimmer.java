import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public Map<Discipline, Result> getTrainingResults() {
        return trainingResults;
    }

    public Map<Discipline, ResultCompetition> getCompetitionResults() {
        return competitionResults;
    }

    public void addTrainingResult(Result result) {
        Discipline discipline = result.getDiscipline(); // Får disciplinen fra result objektet
        if (!trainingResults.containsKey(discipline) || isBetterResult(trainingResults.get(discipline), result)) {
            trainingResults.put(discipline, result);
        }
    }

    public void addCompetitionResult(ResultCompetition result) {
        Discipline discipline = result.getDiscipline(); // Får disciplinen fra ResultCompetition objektet
        if (!competitionResults.containsKey(discipline) || isBetterResult(competitionResults.get(discipline), result)) {
            competitionResults.put(discipline, result);
        }
    }

    private boolean isBetterResult(Result existingResult, Result newResult) {
        // Hvis tiderne er ens, så er datoen tie-breaker. Nyere dato er bedre.
        if (newResult.getTimeInSeconds() == existingResult.getTimeInSeconds()) {
            return newResult.getDate().isAfter(existingResult.getDate());
        }

        // lavere tid er bedre
        return newResult.getTimeInSeconds() < existingResult.getTimeInSeconds();
    }

    private Result getBestResultCheck(Result existingResult, Result newResult) {
        // Sammenlign først tiderne. Lavere tid er bedre.
        if (newResult.getTimeInSeconds() < existingResult.getTimeInSeconds()) {
            return existingResult;
        }

        // Hvis tiderne er ens, så er datoen tie-breaker. Nyere dato er bedre.
        if (newResult.getTimeInSeconds() == existingResult.getTimeInSeconds()) {
            return newResult.getDate().isAfter(existingResult.getDate()) ? newResult : existingResult;
        }

        // Hvis den nye tid er højere, er det ikke et bedre resultat.
        return existingResult;
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
            return getBestResultCheck(bestCompetitionResult, bestTrainingResult);
        }
    }

    public ResultCompetition getBestCompetitionResult(Discipline discipline) {
        return competitionResults.get(discipline);
    }

    public static CompetitiveSwimmer fromCsvString(String csvString) {
        if (csvString == null || csvString.trim().isEmpty()) {
            return null;
        }
        String[] parts = csvString.split(",");
        if (parts.length < 13) {
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
        boolean isJunior = Boolean.parseBoolean(parts[9]); //todo slet?
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
