import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Member implements Serializable {
    private static final long serialVersionUID = 100L;

    private static final int ACTIVE_FEE_UNDER_18 = 1000;
    private static final int ACTIVE_FEE_OVER_18 = 1600;
    private static final int ACTIVE_FEE_OVER_60 = 1200;
    private static final int PASSIVE_FEE_ALL_AGES = 500;
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


    public Member(String name, LocalDate birthDate, Address address, String phoneNumber, String mail, boolean isActive) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.isActive = isActive;
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public boolean isPaymentComplete() {
        return paymentComplete;
    }

    public void setPaymentComplete(boolean paymentComplete) {
        this.paymentComplete = paymentComplete;
    }

    public int calculateMembershipFee() {
        if (!isActive) {
            return PASSIVE_FEE_ALL_AGES;
        } else {
            int age = getAge();
            if (age < 18) {
                return ACTIVE_FEE_UNDER_18;
            } else if (age >= 60) {
                return ACTIVE_FEE_OVER_60;
            } else {
                return ACTIVE_FEE_OVER_18;
            }
        }
    }

    @Override
    public String toString() {
        String category = isJunior() ? "Junior" : "Senior";
        String activeOrPassive = isActive() ? "Aktiv" : "Passiv";
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
                ", registreringsdato: " + registrationDate +
                '}';
    }

    public String toCsvString() {
        String category = isJunior() ? "Junior" : "Senior";
        String activeOrPassive = isActive() ? "Aktiv" : "Passiv";
        return memberId + "," + name + "," + birthDate + "," +
                address.getStreetName() + "," + address.getHouseNumber() + "," +
                address.getZipCode() + "," + address.getCity() + "," +
                phoneNumber + "," + mail + "," + category + "," + activeOrPassive + "," +
                registrationDate + "," + paymentComplete;
    }

    public static Member fromCsvString(String csvString) {
        if (csvString == null || csvString.trim().isEmpty()) {
            return null;
        }
        String[] parts = csvString.split(",");
        int memberId = Integer.parseInt(parts[0]);
        String name = parts[1];
        LocalDate birthDate = LocalDate.parse(parts[2]);
        Address address = new Address(parts[3], parts[4], parts[5], parts[6]);
        String phoneNumber = parts[7];
        String mail = parts[8];
        boolean isJunior = parts[9].equalsIgnoreCase("Junior");
        boolean isActive = parts[10].equalsIgnoreCase("Aktiv");
        LocalDate registrationDate = LocalDate.parse(parts[11]);
        boolean paymentComplete = Boolean.parseBoolean(parts[12]);

        Member member = new Member(name, birthDate, address, phoneNumber, mail, isActive);
        member.setMemberId(memberId);
        member.setRegistrationDate(registrationDate);
        member.setPaymentComplete(paymentComplete);

        return member;
    }

}
