package co.edu.uptc.model;

/**
 * Exception thrown when trying to add a product that already exists in the inventory.
 */
public class ProductAlreadyExistsException extends Exception {

    /**
     * Constructs a new ProductAlreadyExistsException with a detailed message.
     *
     * @param message the detail message explaining the error
     */
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
