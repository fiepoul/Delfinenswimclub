import java.time.LocalDate;

public class Result {
    private LocalDate date;
    private String time; // Antager at resultatet er en tid, f.eks. "00:59.45"

    private Discipline discipline;

    public Result(Discipline discipline, LocalDate date, String time) {
        this.discipline = discipline;
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeWithDate() {
        return time + ", " + date;
    }

    public String getTime() {
        return time;
    }

    public void setTime() {
        this.time = time;
    }

    public Discipline getDiscipline() {return discipline; }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public double getTimeInSeconds() {
        String[] parts = time.split("[:.]");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        int milliseconds = Integer.parseInt(parts[2]);
        return minutes * 60 + seconds + milliseconds / 100.0;
    }

    @Override
    public String toString() {
        return "Result{" + "discipline=" + discipline +
                ", date=" + date +
                ", time='" + time + '\'' +
                '}';
    }

    public String toCsvString() {
        return discipline.name() + "," + date + "," + time;
    }

    public static Result fromCsvString(String csvString) {
        String[] parts = csvString.split(",");

        Discipline discipline = Discipline.valueOf(parts[0]);
        LocalDate date = LocalDate.parse(parts[1]);
        String time = parts[2];

        return new Result(discipline, date, time);
    }


}
