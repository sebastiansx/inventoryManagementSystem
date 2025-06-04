package co.edu.uptc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private String name;
    private String address;
    private String city;
    private String phone;
    private Inventory inventory;
    private Map<String, User> users;
    private List<Sale> sales;

    public Store() {
         this.inventory = new Inventory(); 
         preloadProducts();
        this.users = new HashMap<>();
        this.sales = new ArrayList<>();
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

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.authenticate(username, password);
    }

    public double calculateClientCartTotal(Client client) throws ProductNotFoundException {
        double total = 0;
        Map<String, Integer> cart = client.getCart();
        
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            
            // Get the product from inventory
            int stock = inventory.getStock(productId);
            if (stock >= quantity) {
                Product product = inventory.getStockItem(productId).getProduct();
                total += product.getPrice() * quantity;
            }
        }
        return total;
    }

    public Sale processSale(Client client) throws InsufficientStockException, ProductNotFoundException {
        Map<String, Integer> cart = client.getCart();
        Sale sale = new Sale(generateSaleId(), client);
        
        // First verify we have enough stock for everything
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            
            if (inventory.getStock(productId) < quantity) {
                throw new InsufficientStockException("Not enough stock for product: " + productId);
            }
        }
        
        // Process each item
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            
            // Remove from inventory
            inventory.removeStock(productId, quantity);
            
            // Add to sale
            Product product = inventory.getStockItem(productId).getProduct();
            sale.addItem(product, quantity);
        }
        
        // Clear client's cart
        client.clearCart();
        
        // Add sale to store history
        sales.add(sale);
        
        // Add to client's purchase history
        client.addPurchase(sale);
        
        return sale;
    }

    private String generateSaleId() {
        return "SALE-" + System.currentTimeMillis();
    }

    public String getClientHistory(String clientId) {
        StringBuilder history = new StringBuilder();
        for (Sale sale : sales) {
            if (sale.getClient().getId().equals(clientId)) {
                history.append(sale.getReceiptInfo()).append("\n\n");
            }
        }
        return history.toString();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public double getTotalSales() {
        return sales.stream()
                .mapToDouble(Sale::getTotal)
                .sum();
    }
    

    

    public void preloadProducts() {
        try {
            inventory.addNewProduct(new Product("001", "Camisa", "Camisa de algodón blanca", 45000.0, Category.ROPA),
                    10);
            inventory.addNewProduct(new Product("002", "Pan", "Pan artesanal integral", 3500.0, Category.COMIDA), 25);
            inventory.addNewProduct(
                    new Product("003", "Ibuprofeno", "Tabletas 200mg para dolor", 7000.0, Category.MEDICAMENTO), 15);
            inventory.addNewProduct(
                    new Product("004", "Shampoo", "Shampoo hidratante de coco 400ml", 15500.0, Category.HIGIENE), 20);
            inventory.addNewProduct(new Product("005", "Audífonos", "Bluetooth con cancelación de ruido", 120000.0,
                    Category.ELECTRONICA), 8);
            inventory.addNewProduct(
                    new Product("006", "Vaso térmico", "Vaso metálico con tapa 500ml", 28000.0, Category.HOGAR), 12);
            inventory.addNewProduct(
                    new Product("007", "Pelota", "Pelota de fútbol tamaño 5", 40000.0, Category.JUGUETES), 18);
            inventory.addNewProduct(
                    new Product("008", "Cuaderno", "Cuaderno cosido 100 hojas", 3800.0, Category.LIBRERIA), 30);
            inventory.addNewProduct(
                    new Product("009", "Galletas", "Galletas de avena sin azúcar 6 unidades", 4900.0, Category.COMIDA),
                    22);
            inventory.addNewProduct(
                    new Product("010", "Pijama", "Pijama para mujer en algodón", 52000.0, Category.ROPA), 14);
        } catch (ProductAlreadyExistsException e) {
            throw new RuntimeException("Error al precargar productos", e);
        }
    }
/**
 * Adds a new product to the inventory with the specified quantity.
 *
 * @param product the product to be added
 * @param quantity the initial quantity of the product
 * @throws ProductAlreadyExistsException if the product already exists in the inventory
 */
public void addNewProduct(Product product, int quantity) throws ProductAlreadyExistsException {
    inventory.addNewProduct(product, quantity);
}

/**
 * Removes a product from the inventory by its product ID.
 *
 * @param productId the ID of the product to be removed
 * @throws ProductNotFoundException if the product is not found in the inventory
 */
public void removeProduct(String productId) throws ProductNotFoundException {
    inventory.removeProduct(productId);
}

/**
 * Adds stock to an existing product in the inventory.
 *
 * @param productId the ID of the product to add stock to
 * @param amount the amount of stock to add
 * @throws ProductNotFoundException if the product is not found in the inventory
 */
public void addStock(String productId, int amount) throws ProductNotFoundException {
    inventory.addStock(productId, amount);
}

/**
 * Removes stock from an existing product in the inventory.
 *
 * @param productId the ID of the product to remove stock from
 * @param amount the amount of stock to remove
 * @throws ProductNotFoundException if the product is not found in the inventory
 * @throws InsufficientStockException if there is not enough stock to remove the specified amount
 */
public void removeStock(String productId, int amount) throws ProductNotFoundException, InsufficientStockException {
    inventory.removeStock(productId, amount);
}

/**
 * Retrieves the current stock quantity of a product.
 *
 * @param productId the ID of the product to check stock for
 * @return the current stock quantity
 * @throws ProductNotFoundException if the product is not found in the inventory
 */
public int getStock(String productId) throws ProductNotFoundException {
    return inventory.getStock(productId);
}

/**
 * Returns a summary of all products and their stock levels in the inventory.
 *
 * @return a string representing the inventory summary
 */
public String getInventorySummary() {
    return inventory.getInventorySummary();
}

    // Métodos puente para carrito y compras de cliente
    public void addToCart(Client client, String productId, int quantity) {
        client.addToCart(productId, quantity);
    }

    public void removeFromCart(Client client, String productId, int quantity) {
        client.removeFromCart(productId, quantity);
    }

    public void clearCart(Client client) {
        client.clearCart();
    }

    public Map<String, Integer> getCart(Client client) {
        return client.getCart();
    }

    public List<Sale> getPurchaseHistory(Client client) {
        return client.getPurchaseHistory();
    }

    public Client createClient(String name, String id) {
        Client client = new Client();
        client.setName(name);
        client.setId(id);
        client.setStore(this);
        return client;
    }

    public String showCart(Client client) throws ProductNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CARRITO DE COMPRAS ===\n");
        double total = 0;
        for (Map.Entry<String, Integer> entry : getCart(client).entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventory.getStockItem(productId).getProduct();
            double subtotal = product.getPrice() * quantity;
            sb.append(product.getName()).append(" x").append(quantity)
              .append(" - Precio unitario: $").append(product.getPrice())
              .append(" - Subtotal: $").append(subtotal).append("\n");
            total += subtotal;
        }
        sb.append("Total: $").append(total);
        return sb.toString();
    }

    public String showPurchaseHistory(Client client) {
        StringBuilder sb = new StringBuilder();
        var history = getPurchaseHistory(client);
        if (history.isEmpty()) {
            return "No hay compras registradas para este cliente.";
        } else {
            sb.append("=== HISTORIAL DE COMPRAS ===\n");
            for (var sale : history) {
                sb.append(sale.getReceiptInfo()).append("\n\n");
            }
            return sb.toString();
        }
    }
}
