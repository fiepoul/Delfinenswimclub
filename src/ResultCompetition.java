import java.time.LocalDate;

public class ResultCompetition extends Result {
    private String competitionName;
    private int rank;
    public ResultCompetition(LocalDate date, String time, Discipline discipline, String competitionName, int rank) {
        super(date, time, discipline);
        this.competitionName = competitionName;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "ResultCompetition{" +
                "date=" + getDate() +
                ", time='" + getTime() + '\'' +
                ", discipline=" + getDiscipline() +
                ", competitionName='" + competitionName + '\'' +
                ", rank=" + rank +
                '}';
    }

    public static ResultCompetition fromCsvString(String csvString) {
        String[] parts = csvString.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Ugyldig format for ResultCompetition CSV-streng: " + csvString);
        }

        Discipline discipline = Discipline.valueOf(parts[0]);
        LocalDate date = LocalDate.parse(parts[1]);
        String time = parts[2];
        String competitionName = parts[3];
        int rank = Integer.parseInt(parts[4]);

        return new ResultCompetition(date, time, discipline, competitionName, rank);
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public int getRank() {
        return rank;
    }
}
