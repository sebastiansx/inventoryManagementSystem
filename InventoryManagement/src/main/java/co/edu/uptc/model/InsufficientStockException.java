package co.edu.uptc.model;

/**
 * Exception thrown when there is not enough stock available
 * to fulfill a request for a given product.
 */
public class InsufficientStockException extends Exception {

    /**
     * Constructs a new InsufficientStockException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public InsufficientStockException(String message) {
        super(message);
    }
}
