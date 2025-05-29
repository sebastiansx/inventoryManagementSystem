package co.edu.uptc.model;

public class Person {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;

    public Person() {
    }

    public Person(String name, String id, String phone, String email) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String showProfile(){
        return "ID: " + id + "\nNombre: " + name + "\nDirección: " + address + "\nTeléfono: " + phone + "\nEmail: " + email;
    }
}
