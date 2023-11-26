import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class MemberIdTest {

    @Test
    void testGenerateMemberNumber() {
            MemberController controller = new MemberController(new MemberDatabase());
            int currentYear = LocalDate.now().getYear();

            // Genererer det første medlemsnummer i det aktuelle år
            int expectedFirstMemberNumber = Integer.parseInt(currentYear + "0001");
            assertEquals(expectedFirstMemberNumber, controller.generateMemberNumber());

            // Genererer det næste medlemsnummer i rækken
            int expectedSecondMemberNumber = Integer.parseInt(currentYear + "0002");
            assertEquals(expectedSecondMemberNumber, controller.generateMemberNumber());

        System.out.println(expectedFirstMemberNumber);
        System.out.println(expectedSecondMemberNumber);

    }
}