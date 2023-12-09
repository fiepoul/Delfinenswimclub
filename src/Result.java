import java.time.LocalDate;

public class Result {
    private LocalDate date;
    private String time; // Antager at resultatet er en tid, f.eks. "00:59.45"

    private Discipline discipline;

    public Result(LocalDate date, String time, Discipline discipline) {
        this.date = date;
        this.time = time;
        this.discipline = discipline;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Discipline getDiscipline() {return discipline; }

    public double getTimeInSeconds() {
        String[] parts = time.split("[:.]");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        int milliseconds = Integer.parseInt(parts[2]);
        return minutes * 60 + seconds + milliseconds / 100.0;
    }

    public String toCsvString() {
        return discipline.name() + "," + date + "," + time;
    }

    public static Result fromCsvString(String csvString) {
        String[] parts = csvString.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Ugyldig format for Result CSV-streng: " + csvString);
        }

        Discipline discipline = Discipline.valueOf(parts[0]);
        LocalDate date = LocalDate.parse(parts[1]);
        String time = parts[2];

        return new Result(date, time, discipline);
    }


}
