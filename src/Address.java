import java.io.Serializable;

public class Address implements Serializable {
    private String streetName;
    private String houseNumber;
    private String zipCode;
    private String city;

    public Address(String streetName, String houseNumber, String zipCode, String city) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName() {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber() {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode() {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity() {
        this.city = city;
    }

    @Override
    public String toString() {
        return streetName + " " + houseNumber + ", " + zipCode + " " + city;
    }
}