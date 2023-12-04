public class LogInService {

    private User chairmanUser = new User("Alwayshappy", "1234", Role.FORMAND);

    private User treasurerUser = new User("Mostlyhappy", "5678", Role.KASSERER);

    public boolean isUsernameValid(String username) {
        return chairmanUser.getUsername().equals(username) || treasurerUser.getUsername().equals(username);
    }
    public User login(String username, String password) {

        if (chairmanUser.getUsername().equals(username) && chairmanUser.getPassword().equals(password)) {
            return chairmanUser; // Succesfuld login
        }

        if (treasurerUser.getUsername().equals(username) && treasurerUser.getPassword().equals(password)) {
            return treasurerUser;
        }
        return null;
    }
}
