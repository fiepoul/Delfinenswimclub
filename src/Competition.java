import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Competition {
    private String name;
    private LocalDate date;
    private String location;
    private List<CompetitionEvent> events;

    public Competition(String name, LocalDate date, String location) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.events = new ArrayList<>();
    }

    public void addEvent(CompetitionEvent event) {
        events.add(event);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public List<CompetitionEvent> getEvents() {
        return events;
    }
}
