package co.edu.uptc.model;

import java.util.List;

/**
 * Interface that defines the core operations for managing inventory.
 * Provides methods to add, remove, and list products and their stock.
 */
public interface ManageableInventory {

    /**
     * Adds a new product to the inventory with a specified quantity.
     *
     * @param product  the product to add
     * @param quantity the initial stock quantity
     * @throws ProductAlreadyExistsException if a product with the same ID already exists
     */
    void addNewProduct(Product product, int quantity) throws ProductAlreadyExistsException;

    /**
     * Removes a product from the inventory using its ID.
     *
     * @param productId the ID of the product to remove
     * @throws ProductNotFoundException if the product does not exist in the inventory
     */
    void removeProduct(String productId) throws ProductNotFoundException;

    /**
     * Returns a list of all stock items currently in the inventory.
     *
     * @return a list of StockItem objects
     */
    List<StockItem> listInventory();
}
