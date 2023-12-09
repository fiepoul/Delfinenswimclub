import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwimTeamDatabase {
    private String SWIM_TEAM_FILE = "swimTeam.csv";
    private String SWIM_RESULT_FILE = "swimResult.csv";
    private List<CompetitiveSwimmer> swimmers;

    public SwimTeamDatabase(String file) {
        SWIM_TEAM_FILE = "swimTeam.csv";
        SWIM_RESULT_FILE = "swimResult.csv";
        this.swimmers = new ArrayList<>();
        loadSwimmers();
        loadBestResults();
    }

    public List<CompetitiveSwimmer> loadSwimmers() {
        List<CompetitiveSwimmer> loadedSwimmers = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(SWIM_TEAM_FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                loadedSwimmers.add(CompetitiveSwimmer.fromCsvString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return swimmers = loadedSwimmers;
    }

    public void addSwimmer(CompetitiveSwimmer swimmer) {
        swimmers.add(swimmer);
        saveSwimmers(); // Gemmer hele listen
    }

    public List<CompetitiveSwimmer> getAllSwimmers() {
        return new ArrayList<>(swimmers);
    }

    public void saveSwimmers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SWIM_TEAM_FILE))) {
            for (CompetitiveSwimmer swimmer : swimmers) {
                writer.write(swimmer.toCsvString()); // Gemmer grundlæggende medlemsdata
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBestResults() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SWIM_RESULT_FILE))) {
            for (CompetitiveSwimmer swimmer : swimmers) {
                for (Discipline discipline : Discipline.values()) {
                    Result bestTrainingResult = swimmer.getBestResult(discipline);
                    if (bestTrainingResult != null) {
                        String resultLine = swimmer.getMemberId() + "," + bestTrainingResult.toCsvString();
                        writer.write(resultLine);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Fejl ved skrivning af resultater til fil: " + e.getMessage());
        }
    }

    public void loadBestResults() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SWIM_RESULT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                try {
                    int memberId = Integer.parseInt(parts[0]);
                    Result result = Result.fromCsvString(String.join(",", Arrays.copyOfRange(parts, 1, parts.length)));

                    CompetitiveSwimmer swimmer = findSwimmerById(memberId);
                    if (swimmer != null) {
                        swimmer.addTrainingResult(result);
                    }
                } catch (IllegalArgumentException e) {
                }
            }
        } catch (IOException e) {
            System.err.println("Fejl ved læsning af resultater fra fil: " + e.getMessage());
        }
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void recordSwimmerResult(int swimmerId, Result result) {
        CompetitiveSwimmer swimmer = findSwimmerById(swimmerId);
        if (swimmer != null) {
            if (result instanceof ResultCompetition) {
                swimmer.addCompetitionResult((ResultCompetition) result);
            } else {
                swimmer.addTrainingResult(result);
            }
            saveBestResults(); // Gemmer ændringerne
        }
    }

    public CompetitiveSwimmer findSwimmerById(int swimmerId) {
        return swimmers.stream()
                .filter(swimmer -> swimmer.getMemberId() == swimmerId)
                .findFirst()
                .orElse(null);
    }

}
