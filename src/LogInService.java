public class LogInService {
    public User login(String username, String password) {
        User exampleUser = new User("Alwayshappy", "1234", Role.FORMAND);

        if (exampleUser.getUsername().equals(username) && exampleUser.getPassword().equals(password)) {
            return exampleUser; // Succesfuld login
        }
        return null; // Login fejlede
    }
}
