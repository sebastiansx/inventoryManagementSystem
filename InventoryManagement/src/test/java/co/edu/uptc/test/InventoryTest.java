package test.java.co.edu.uptc.test;

import co.edu.uptc.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

public class InventoryTest {
    private Inventory inventory;
    private Product product;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory(new HashMap<>());
        product = new Product("001", "Camisa", "Camisa blanca", 10000.0, Category.ROPA);
    }

    @Test
    public void testAddNewProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        inventory.addNewProduct(product, 5);
        assertEquals(5, inventory.getStock("001"));
    }

    @Test
    public void testAddNewProductAlreadyExists() throws ProductAlreadyExistsException {
        inventory.addNewProduct(product, 5);
        assertThrows(ProductAlreadyExistsException.class, () -> {
            inventory.addNewProduct(product, 3);
        });
    }

    @Test
    public void testRemoveProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        inventory.addNewProduct(product, 5);
        inventory.removeProduct("001");
        assertThrows(ProductNotFoundException.class, () -> {
            inventory.getStock("001");
        });
    }

    @Test
    public void testAddStock() throws ProductAlreadyExistsException, ProductNotFoundException {
        inventory.addNewProduct(product, 5);
        inventory.addStock("001", 3);
        assertEquals(8, inventory.getStock("001"));
    }

    @Test
    public void testRemoveStock() throws Exception {
        inventory.addNewProduct(product, 5);
        inventory.removeStock("001", 2);
        assertEquals(3, inventory.getStock("001"));
    }

    @Test
    public void testRemoveStockInsufficient() throws Exception {
        inventory.addNewProduct(product, 2);
        assertThrows(InsufficientStockException.class, () -> {
            inventory.removeStock("001", 5);
        });
    }

    @Test
    public void testGetInventorySummary() throws ProductAlreadyExistsException {
        inventory.addNewProduct(product, 5);
        String summary = inventory.getInventorySummary();
        assertTrue(summary.contains("Camisa"));
    }
} 