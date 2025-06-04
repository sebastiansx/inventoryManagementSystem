package co.edu.uptc.model;

public class Product {
    private String id;
    private String name;
    private String description;
    private double price;
    private Category category;
    

    public Product(String id, String name, String description, double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    
   public void updatePrice(double newPrice) {
    if (newPrice <= 0) {
        throw new IllegalArgumentException("El precio debe ser mayor que cero.");
    }
    this.price = newPrice;
}

   @Override
   public String toString() {
    return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", category="
            + category + "]";
   }




}