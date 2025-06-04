package co.edu.uptc.model;

/**
 * Represents a product that can be stored and sold in the system.
 * Contains details such as ID, name, description, price, and category.
 */
public class Product {
    private String id;
    private String name;
    private String description;
    private double price;
    private Category category;

    /**
     * Constructs a new Product with all required attributes.
     *
     * @param id          the unique identifier of the product
     * @param name        the name of the product
     * @param description a brief description of the product
     * @param price       the selling price of the product
     * @param category    the category of the product
     */
    public Product(String id, String name, String description, double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the product ID.
     *
     * @param id the new ID to assign
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the product name.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the new name to assign
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description.
     *
     * @return the product's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the new description to assign
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product price.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the new price to assign
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the product category.
     *
     * @return the product's category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the new category to assign
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Updates the product's price if the new value is greater than zero.
     *
     * @param newPrice the new price to set
     * @throws IllegalArgumentException if the price is less than or equal to zero
     */
    public void updatePrice(double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        this.price = newPrice;
    }

    /**
     * Returns a string representation of the product with all its attributes.
     *
     * @return formatted string with product details
     */
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", category="
                + category + "]";
    }
}
