package co.edu.uptc.model;

/**
 * Exception thrown when a product with a specific ID is not found in the inventory.
 */
public class ProductNotFoundException extends Exception {

    /**
     * Constructs a new ProductNotFoundException with a detailed message.
     *
     * @param message the detail message explaining the error
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
