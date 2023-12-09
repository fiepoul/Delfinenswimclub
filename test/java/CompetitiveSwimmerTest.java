import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CompetitiveSwimmerTest {

   @Test
    void testBestResult() {
        Address address = new Address("vejvej", "4", "2100", "byby");
        CompetitiveSwimmer competitiveSwimmer = new CompetitiveSwimmer("camilla", LocalDate.of(1995, 2, 9), address, "40404040", "fi@fi.dk", true);

        competitiveSwimmer.addTrainingResult(new Result(LocalDate.of(2021, 1, 1), "00:55.00", Discipline.BUTTERFLY));
        competitiveSwimmer.addTrainingResult(new Result(LocalDate.of(2021, 6, 1), "00:54.00", Discipline.BUTTERFLY));
        competitiveSwimmer.addTrainingResult(new Result(LocalDate.of(2020, 8, 2), "00:53.00", Discipline.BUTTERFLY)); // Samme tid som ved stævne 2, ældre dato

        competitiveSwimmer.addCompetitionResult(new ResultCompetition(LocalDate.of(2021, 5, 1), "00:55.00", Discipline.BUTTERFLY, "Stævne 1", 1));
        competitiveSwimmer.addCompetitionResult(new ResultCompetition(LocalDate.of(2021, 6, 1), "00:53.00", Discipline.BUTTERFLY, "Stævne 2", 2));

        Result bestResult = competitiveSwimmer.getBestResult(Discipline.BUTTERFLY);

        assertTrue(bestResult instanceof ResultCompetition);
        ResultCompetition bestCompetitionResult = (ResultCompetition) bestResult;
        assertEquals("00:53.00", bestCompetitionResult.getTime());
        assertEquals(LocalDate.of(2021, 6, 1), bestCompetitionResult.getDate());
        assertEquals("Stævne 2", bestCompetitionResult.getCompetitionName());
       System.out.println(bestCompetitionResult);
       System.out.println(bestResult);
    }
}