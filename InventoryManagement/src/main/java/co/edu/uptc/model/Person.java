package co.edu.uptc.model;

/**
 * Abstract base class representing a person with common attributes such as id, name, address, phone, and email.
 */
public abstract class Person {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;

    /**
     * Default constructor.
     */
    public Person() {
    }

    /**
     * Constructs a person with the specified attributes.
     * @param name the person's name
     * @param id the person's ID
     * @param phone the person's phone number
     * @param email the person's email address
     */
    public Person(String name, String id, String phone, String email) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Abstract method to show the profile of the person.
     * @return a formatted string representing the profile
     */
    public abstract String showProfile();

    /**
     * Gets the person's ID.
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the person's ID.
     * @param id the new ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the person's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the person's name.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the person's address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the person's address.
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the person's phone number.
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the person's phone number.
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the person's email address.
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the person's email address.
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
