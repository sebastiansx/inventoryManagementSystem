package co.edu.uptc.test;

import co.edu.uptc.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {
    private Store store;
    private User adminUser;
    private User normalUser;

    @BeforeEach
    public void setUp() {
        store = new Store();
        adminUser = new User("Admin", "100", "123456", "admin@mail.com", "admin", "adminpass", "admin");
        normalUser = new User("User", "101", "654321", "user@mail.com", "user", "userpass", "user");
        store.addUser(adminUser);
        store.addUser(normalUser);
    }

    @Test
    public void testAddUserAndAuthenticate() {
        assertTrue(store.authenticateUser("admin", "adminpass"));
        assertTrue(store.authenticateUser("user", "userpass"));
        assertFalse(store.authenticateUser("user", "wrongpass"));
        assertFalse(store.authenticateUser("nouser", "nopass"));
    }

    @Test
    public void testGetTotalSales() throws Exception {
        Client client = new Client();
        client.setName("Cliente1");
        client.setId("200");
        store.preloadProducts();
        // Simular una venta
        Inventory inventory = store.getInventory();
        Product product = inventory.getStockItem("001").getProduct();
        Sale sale = new Sale("S001", client);
        sale.addItem(product, 2);
        store.getSales().add(sale);
        assertEquals(sale.getTotal(), store.getTotalSales());
    }

    @Test
    public void testGetClientHistory() throws Exception {
        Client client = new Client();
        client.setName("Cliente1");
        client.setId("200");
        store.preloadProducts();
        Inventory inventory = store.getInventory();
        Product product = inventory.getStockItem("001").getProduct();
        Sale sale = new Sale("S001", client);
        sale.addItem(product, 2);
        store.getSales().add(sale);
        String history = store.getClientHistory("200");
        assertTrue(history.contains("Cliente1"));
        assertTrue(history.contains("Camisa"));
    }
} 