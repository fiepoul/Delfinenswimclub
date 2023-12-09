import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
                .filter(swimmer -> swimmer.getBestCompetitionResult(discipline) != null)
                .sorted(Comparator.comparingDouble(swimmer -> swimmer.getBestCompetitionResult(discipline).getTimeInSeconds()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<CompetitiveSwimmer> getTopJuniorSwimmers(Discipline discipline) {
        List<CompetitiveSwimmer> juniors = getJuniorSwimmers();
        return getTopSwimmersByAgeGroup(juniors, discipline);
    }

    public List<CompetitiveSwimmer> getTopSeniorSwimmers(Discipline discipline) {
        List<CompetitiveSwimmer> seniors = getSeniorSwimmers();
        return getTopSwimmersByAgeGroup(seniors, discipline);
    }

}
