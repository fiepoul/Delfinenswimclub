public class Trainer {
    private String name;
    private String team; //junior eller senior
    private String pay; //måske ingen betaling

    public Trainer(String name, String pay) {
        this.name = name;
        this.team = "";
        this.pay = pay;
    }

    public String getName() {
        return name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setPay(double newPay) {
        this.pay = pay;
    }

    public String getTeam() {
        return team;
    }

    public static Trainer fromCsvString(String csvString) {
        String[] parts = csvString.split(",");
        String name = parts[0];
        String pay = parts[1];
        return new Trainer(name, pay);
    }

    public String toCsvString() {
        return name + "," + pay;
    }

    public String getPay() {
        return pay;
    }
}
