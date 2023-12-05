import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FinancialDatabase {
    private String FINANCIAL_DETAILS_FILE = "financialDetails.csv"; //todo: find ud af hvordan du skifter til test. konstruktør

    public FinancialDatabase(String filename) {
        FINANCIAL_DETAILS_FILE = filename;
    }

    public void saveFinancialDetails(List<Member> members) {
        try (BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(FINANCIAL_DETAILS_FILE), StandardCharsets.UTF_8))) {
            writer.write("Medlemsnummer,Navn,E-mail,Kontingent,Betalingsstatus");
            writer.newLine();
            for (Member member : members) {
                String paymentStatus = member.isPaymentComplete() ? "Betalt" : "Ubetalt";
                String memberData = member.getMemberId() + "," + member.getName() + "," + member.getMail() + "," + member.calculateMembershipFee() + "," + paymentStatus;
                writer.write(memberData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fejl ved skrivning til fil: " + e.getMessage());
        }
    }
}
