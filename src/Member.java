import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    private int memberId;
    private String name;
    private LocalDate birthDate;
    private String address; //gadenavn, evt slettes, evt tilføj zipcode, husnummer, by
    private String phoneNumber;
    private String mail;
    private boolean isActive; //aktiv eller passiv
    private boolean isCompetitive; //motion eller konkurrence
    private LocalDate registrationDate;


    public Member(String name, LocalDate birthDate, String address, String phoneNumber, String mail, boolean isActive, boolean isCompetitive) {
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
        return address;
    }

    public void setAddress(String address) {
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

    @Override
    public String toString() {
        String category = isJunior() ? "Junior" : "Senior";
        return "Member{" +
                "memberID=: " + memberId +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", isActive=" + isActive +
                ", isCompetitive=" + isCompetitive +
                ", registrationDate=" + registrationDate +
                '}';
    }

}
