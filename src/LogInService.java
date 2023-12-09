public class LogInService {

    private final User chairmanUser = new User("Alwayshappy", "1234", Role.FORMAND);

    private final User treasurerUser = new User("Mostlyhappy", "5678", Role.KASSERER);
    private final User trainerUser = new User("Somewhathappy", "9012", Role.TRÆNER);

    public boolean isUsernameValid(String username) {
        return chairmanUser.getUsername().equals(username) || treasurerUser.getUsername().equals(username) || trainerUser.getUsername().equals(username);
    }
    public User login(String username, String password) {

        if (chairmanUser.getUsername().equals(username) && chairmanUser.getPassword().equals(password)) {
            return chairmanUser; // Succesfuld login
        }

        if (treasurerUser.getUsername().equals(username) && treasurerUser.getPassword().equals(password)) {
            return treasurerUser;
        }

        if (trainerUser.getUsername().equals(username) && trainerUser.getPassword().equals(password)) {
            return trainerUser;
        }
        return null;
    }
}
