package co.edu.uptc.model;

/**
 * Represents a user in the system, extending Person, with authentication and role management.
 */
public class User extends Person {
    private String username;
    private String password;
    private String role;

    /**
     * Constructs a new User with all required attributes.
     * @param name the user's name
     * @param id the user's ID
     * @param phone the user's phone number
     * @param email the user's email address
     * @param username the username for login
     * @param password the password for login
     * @param role the user's role (e.g., admin, client)
     */
    public User(String name, String id, String phone, String email, String username, String password, String role) {
        super(name, id, phone, email);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's role.
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * @param role the new role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Authenticates the user with the provided username and password.
     * @param inputUser the username to check
     * @param inputPassword the password to check
     * @return true if credentials match, false otherwise
     */
    public boolean authenticate(String inputUser, String inputPassword) {
        return username.equals(inputUser) && password.equals(inputPassword);
    }

    /**
     * Checks if the user has admin privileges.
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }

    /**
     * Returns a string representation of the user's profile.
     * @return formatted profile string
     */
    @Override
    public String showProfile() {
        return "Usuario: " + getName() + " | Rol: " + role;
    }
}
