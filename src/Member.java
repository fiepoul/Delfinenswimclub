import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Member implements Serializable {
    private static final long serialVersionUID = 100L;

    private int memberId;
    private String name;
    private LocalDate birthDate;
    private Address address;
    private String phoneNumber;
    private String mail;
    private boolean isActive; //aktiv eller passiv
    private boolean isCompetitive; //motion eller konkurrence
    private LocalDate registrationDate;
    private boolean paymentComplete;


    public Member(String name, LocalDate birthDate, Address address, String phoneNumber, String mail, boolean isActive, boolean isCompetitive) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.isActive = isActive;
        this.isCompetitive = isCompetitive;
        this.registrationDate = LocalDate.now(); // sættes til dato for indmeldelse
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        if (birthDate == null) {
            return 0; // hvis fødselsdato ikke er oprettet sættes alder til 0
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public boolean isJunior() {
        return getAge() < 18;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getAddress() {
        return address.toString();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isCompetitive() {
        return isCompetitive;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isPaymentComplete() {
        return paymentComplete;
    }

    public void setPaymentComplete(boolean paymentComplete) {
        this.paymentComplete = paymentComplete;
    }

    public int calculateMembershipFee() {
        MemberFee feeCalculator = new MemberFee(name, birthDate, address, phoneNumber, mail, isActive, isCompetitive);
        return feeCalculator.calculateMembershipFee();
    }

    @Override
    public String toString() {
        String category = isJunior() ? "Junior" : "Senior";
        String activeOrPassive = isActive() ? "Aktiv" : "Passiv";
        String competitiveOrHobby = isCompetitive() ? "Konkurrencesvømmer" : "Motionist";
        return "Medlem{" +
                "medlemsnummer: " + memberId +
                ", navn: '" + name + '\'' +
                ", fødselsdag: " + birthDate +
                ", alder: " + getAge() +
                ", addresse: '" + address + '\'' +
                ", telefonnummer: '" + phoneNumber + '\'' +
                ", e-mail: '" + mail + '\'' +
                ", status: " + activeOrPassive +
                ", kategori: " + category +
                ", type: " + competitiveOrHobby +
                ", registreringsdato: " + registrationDate +
                '}';
    }

    public String toCsvString() {
        String category = isJunior() ? "Junior" : "Senior";
        String activeOrPassive = isActive() ? "Aktiv" : "Passiv";
        String competitiveOrHobby = isCompetitive() ? "Konkurrencesvømmer" : "Motionist";
        return memberId + "," + name + "," + birthDate + "," +
                address.getStreetName() + "," + address.getHouseNumber() + "," +
                address.getZipCode() + "," + address.getCity() + "," +
                phoneNumber + "," + mail + "," + category + "," + activeOrPassive + "," +
                competitiveOrHobby + "," + registrationDate + "," + paymentComplete;
    } //todo: skriv lidt flottere boolean

    public static Member fromCsvString(String csvString) {
        String[] parts = csvString.split(",");

        int memberId = Integer.parseInt(parts[0]);
        String name = parts[1];
        LocalDate birthDate = LocalDate.parse(parts[2]);
        Address address = new Address(parts[3], parts[4], parts[5], parts[6]);
        String phoneNumber = parts[7];
        String mail = parts[8];
        boolean isJunior = Boolean.parseBoolean(parts[9]);
        boolean isActive = Boolean.parseBoolean(parts[10]);
        boolean isCompetitive = Boolean.parseBoolean(parts[11]);
        LocalDate registrationDate = LocalDate.parse(parts[12]);
        boolean paymentComplete = Boolean.parseBoolean(parts[13]);

        Member member = new Member(name, birthDate, address, phoneNumber, mail, isActive, isCompetitive);
        member.setMemberId(memberId);
        member.setRegistrationDate(registrationDate);
        member.setPaymentComplete(paymentComplete);

        return member;
    }

}
