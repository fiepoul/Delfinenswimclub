import static org.junit.jupiter.api.Assertions.*;

class LogInServiceTest {
    public static void main(String[] args) {
        LogInService logInService = new LogInService();

        User user = logInService.login("Alwayshappy", "1234");
        if (user != null) {
            System.out.println("Login korrekt: " + user);
        } else {
            System.out.println("Login fejl.");
        }

        //test med forkerte oplysninger
        user = logInService.login("greathorse", "idontknow");
        if (user == null) {
            System.out.println("login fejlede som forventet");
        }
    }
}