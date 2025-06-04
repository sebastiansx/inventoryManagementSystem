package co.edu.uptc.test;

import co.edu.uptc.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Inventory class.
 */
public class InventoryTest {
    private Inventory inventory;
    private Product product;

    /**
     * Sets up the test environment before each test, creating an Inventory instance and a product.
     */
    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        product = new Product("001", "Camisa", "Camisa blanca", 10000.0, Category.ROPA);
    }

    /**
     * Verifies that a new product can be added and that the stock is as expected.
     */
    @Test
    public void testAddNewProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        inventory.addNewProduct(product, 5);
        assertEquals(5, inventory.getStock("001"));
    }

    /**
     * Verifies that a product that already exists in the inventory cannot be added again.
     */
    @Test
    public void testAddNewProductAlreadyExists() throws ProductAlreadyExistsException {
        inventory.addNewProduct(product, 5);
        assertThrows(ProductAlreadyExistsException.class, () -> {
            inventory.addNewProduct(product, 3);
        });
    }

    /**
     * Verifies that a product can be removed and that its stock cannot be queried afterwards.
     */
    @Test
    public void testRemoveProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        inventory.addNewProduct(product, 5);
        inventory.removeProduct("001");
        assertThrows(ProductNotFoundException.class, () -> {
            inventory.getStock("001");
        });
    }

    /**
     * Verifies that stock can be added to an existing product.
     */
    @Test
    public void testAddStock() throws ProductAlreadyExistsException, ProductNotFoundException {
        inventory.addNewProduct(product, 5);
        inventory.addStock("001", 3);
        assertEquals(8, inventory.getStock("001"));
    }

    /**
     * Verifies that stock can be removed from an existing product.
     */
    @Test
    public void testRemoveStock() throws Exception {
        inventory.addNewProduct(product, 5);
        inventory.removeStock("001", 2);
        assertEquals(3, inventory.getStock("001"));
    }

    /**
     * Verifies that an exception is thrown if more stock is removed than available.
     */
    @Test
    public void testRemoveStockInsufficient() throws Exception {
        inventory.addNewProduct(product, 2);
        assertThrows(InsufficientStockException.class, () -> {
            inventory.removeStock("001", 5);
        });
    }

    /**
     * Verifies that the inventory summary contains information about the added product.
     */
    @Test
    public void testGetInventorySummary() throws ProductAlreadyExistsException {
        inventory.addNewProduct(product, 5);
        String summary = inventory.getInventorySummary();
        assertTrue(summary.contains("Camisa"));
    }
} 