public class LogInService {
    public User login(String username, String password) {
        User chairmanUser = new User("Alwayshappy", "1234", Role.FORMAND);

        User treasurerUser = new User("Mostlyhappy", "5678", Role.KASSERER);

        if (chairmanUser.getUsername().equals(username) && chairmanUser.getPassword().equals(password)) {
            return chairmanUser; // Succesfuld login
        }

        if (treasurerUser.getUsername().equals(username) && treasurerUser.getPassword().equals(password)) {
            return treasurerUser;
        }
        return null;
    }
}
