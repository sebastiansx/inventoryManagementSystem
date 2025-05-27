package co.edu.uptc.model;

public class Store {
    private String name;
    private String address;
    private String city;
    private String phone;
    private String inventory;

    public Store() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getClientHistory(String clientId) {
        // Implementation pending
        return "";
    }

    public void registerSale(String saleId) {
        // Implementation pending
    }
}
