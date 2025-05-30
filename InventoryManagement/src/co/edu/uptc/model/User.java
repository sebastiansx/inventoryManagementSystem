package co.edu.uptc.model;

public class User extends Person {
    private String username;
    private String password;
    private String role;

    public User(String name, String id, String phone, String email, String username, String password, String role) {
        super(name, id, phone, email);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean authenticate(String inputUser, String inputPassword) {
        return this.username.equals(inputUser) && this.password.equals(inputPassword);
    }

    @Override
    public String showProfile(){
        return super.showProfile() + "\nUsuario: " + username + "\nRol: " + role;
    }
}
