import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FinancialDatabase {
    private static final String FINANCIAL_DETAILS_FILE = "financialDetails.csv";

    public void saveFinancialDetails(List<Member> members) {
        try (BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(FINANCIAL_DETAILS_FILE), StandardCharsets.UTF_8))) {
            writer.write("Medlemsnummer,Navn,Kontingent,Betalingsstatus");
            writer.newLine();
            for (Member member : members) {
                String paymentStatus = member.isPaymentComplete() ? "Betalt" : "Ubetalt";
                String memberData = member.getMemberId() + "," + member.getName() + "," + member.calculateMembershipFee() + "," + paymentStatus;
                writer.write(memberData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fejl ved skrivning til fil: " + e.getMessage());
        }
    }
}
