import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

public class CompetitionEvent {
    private Discipline discipline;
    private List<Result> results;

    public CompetitionEvent(Discipline discipline) {
        this.discipline = discipline;
        this.results = new ArrayList<>();
    }
    public void addResult(Result results) {

    }
}
