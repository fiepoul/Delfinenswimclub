import java.util.*;
import java.util.stream.Collectors;

public class SwimTeamController {
    private SwimTeamDatabase swimTeamDatabase;

    public SwimTeamController(SwimTeamDatabase swimTeamDatabase) {
        this.swimTeamDatabase = swimTeamDatabase;
    }

    public List<CompetitiveSwimmer> getAllSwimmers() {
        return swimTeamDatabase.getAllSwimmers();
    }

    public void addSwimmer(CompetitiveSwimmer swimmer) {
        swimTeamDatabase.addSwimmer(swimmer);
    }

    public List<CompetitiveSwimmer> getJuniorSwimmers() {
        return getAllSwimmers().stream()
                .filter(swimmer -> swimmer.getAge() < 18)
                .collect(Collectors.toList());
    }

    public List<CompetitiveSwimmer> getSeniorSwimmers() {
        return getAllSwimmers().stream()
                .filter(swimmer -> swimmer.getAge() >= 18)
                .collect(Collectors.toList());
    }

    public void recordSwimmerResult(int swimmerId, Result result) {
        CompetitiveSwimmer swimmer = findSwimmerById(swimmerId);
        if (swimmer != null) {
            if (result instanceof ResultCompetition) {
                swimmer.addCompetitionResult((ResultCompetition) result);
                swimTeamDatabase.saveBestResults();
            } else {
                swimmer.addTrainingResult(result);
                swimTeamDatabase.saveBestResults();
            }
        }
    }

    public CompetitiveSwimmer findSwimmerById(int swimmerId) {
        return getAllSwimmers().stream()
                .filter(swimmer -> swimmer.getMemberId() == swimmerId)
                .findFirst()
                .orElse(null);
    }

    public List<CompetitiveSwimmer> getTopSwimmersByAgeGroup(List<CompetitiveSwimmer> ageGroupSwimmers, Discipline discipline) {
        return ageGroupSwimmers.stream()
                .filter(swimmer -> swimmer.getBestResult(discipline) != null)
                .sorted(Comparator.comparingDouble(swimmer -> swimmer.getBestResult(discipline).getTimeInSeconds()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public Map<String, List<CompetitiveSwimmer>> getSwimTeamsJuniorOrSenior(List<CompetitiveSwimmer> swimmers) {
        Map<String, List<CompetitiveSwimmer>> swimTeams = new HashMap<>();
        List<CompetitiveSwimmer> juniors = swimmers.stream()
                .filter(swimmer -> swimmer.getAge() < 18)
                .collect(Collectors.toList());
        List<CompetitiveSwimmer> seniors = swimmers.stream()
                .filter(swimmer -> swimmer.getAge() >= 18)
                .collect(Collectors.toList());

        swimTeams.put("Junior", juniors);
        swimTeams.put("Senior", seniors);

        return swimTeams;
    }

    public List<CompetitiveSwimmer> getTopJuniorSwimmers(Discipline discipline) {
        List<CompetitiveSwimmer> juniors = getJuniorSwimmers();
        return getTopSwimmersByAgeGroup(juniors, discipline);
    }

    public List<CompetitiveSwimmer> getTopSeniorSwimmers(Discipline discipline) {
        List<CompetitiveSwimmer> seniors = getSeniorSwimmers();
        return getTopSwimmersByAgeGroup(seniors, discipline);
    }

    public void addTrainer(Trainer trainer) {
        swimTeamDatabase.addTrainer(trainer);
        swimTeamDatabase.saveTrainers();
    }

    private Optional<Trainer> findTrainerByName(String trainerName) {
        return swimTeamDatabase.getAllTrainers().stream()
                .filter(trainer -> trainer.getName().equalsIgnoreCase(trainerName))
                .findFirst();
    }

    public void assignTrainerToTeam(String trainerName, String team) {
        findTrainerByName(trainerName)
                .ifPresent(trainer -> trainer.setTeam(team));
    }

    public void updateTrainerPay(String trainerName, double newPay) {
        findTrainerByName(trainerName)
                .ifPresent(trainer -> trainer.setPay(newPay));
    }

    public Trainer getTrainerByName(String trainerName) {
        return findTrainerByName(trainerName).orElse(null);
    }

    public List<Trainer> getTrainersByTeam(String team) {
        return swimTeamDatabase.getAllTrainers().stream()
                .filter(trainer -> trainer.getTeam().equalsIgnoreCase(team))
                .collect(Collectors.toList());
    }

}
