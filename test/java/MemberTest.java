import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testToString() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        LocalDate registrationDate = LocalDate.now();
        Member member = new Member("nina poulsen", birthDate,"marstrandsgade 10, 8000 aarhus","30339098", "gittejørgensen@hotmail.com", true, false);
        member.setRegistrationDate(registrationDate);

        String expected = "Member{" +
                "memberID=: " + member.getMemberId() +
                ", name='" + "nina poulsen" + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + "marstrandsgade 10, 8000 aarhus" + '\'' +
                ", phoneNumber='" + "30339098" + '\'' +
                ", mail='" + "gittejørgensen@hotmail.com" + '\'' +
                ", isActive=" + true +
                ", isCompetitive=" + false +
                ", registrationDate=" + registrationDate +
                '}';

        System.out.println(member);

        assertEquals(expected, member.toString());

    }
}