import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ChairmanUITest {

    @Test
    void testGenerateMemberNumber() {
        ChairmanUI chairman = new ChairmanUI();
        int currentYear = LocalDate.now().getYear();
        int memberNumber = chairman.generateMemberNumber();

        assertEquals(Integer.parseInt(currentYear + "0001"), memberNumber, "Første medlemsnummer i året burde være korrekt.");

        System.out.println("Genereret medlemsnummer: " + memberNumber);
    }
}