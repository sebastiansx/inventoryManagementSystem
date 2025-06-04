package co.edu.uptc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a store that manages inventory, users, sales, and client operations.
 */
public class Store {
    private String name;
    private String address;
    private String city;
    private String phone;
    private Inventory inventory;
    private Map<String, User> users;
    private List<Sale> sales;

    /**
     * Constructs a new Store and preloads products.
     */
    public Store() {
         this.inventory = new Inventory(); 
         preloadProducts();
        this.users = new HashMap<>();
        this.sales = new ArrayList<>();
    }

    /**
     * Gets the store's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the store's name.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the store's address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the store's address.
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the store's city.
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the store's city.
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the store's phone number.
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the store's phone number.
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Adds a user to the store's user map.
     * @param user the user to add
     */
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Authenticates a user by username and password.
     * @param username the username
     * @param password the password
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.authenticate(username, password);
    }

    /**
     * Calculates the total value of the client's cart.
     * @param client the client
     * @return the total value
     * @throws ProductNotFoundException if a product is not found
     */
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

    /**
     * Processes a sale for the client, updating inventory and purchase history.
     * @param client the client
     * @return the completed sale
     * @throws InsufficientStockException if there is not enough stock
     * @throws ProductNotFoundException if a product is not found
     */
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

    /**
     * Generates a unique sale ID.
     * @return the sale ID
     */
    private String generateSaleId() {
        return "SALE-" + System.currentTimeMillis();
    }

    /**
     * Gets the purchase history for a client by ID.
     * @param clientId the client's ID
     * @return a string with the client's purchase history
     */
    public String getClientHistory(String clientId) {
        StringBuilder history = new StringBuilder();
        for (Sale sale : sales) {
            if (sale.getClient().getId().equals(clientId)) {
                history.append(sale.getReceiptInfo()).append("\n\n");
            }
        }
        return history.toString();
    }

    /**
     * Gets the store's inventory.
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the store's inventory.
     * @param inventory the new inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the list of sales made by the store.
     * @return the list of sales
     */
    public List<Sale> getSales() {
        return sales;
    }

    /**
     * Gets the total value of all sales made by the store.
     * @return the total sales value
     */
    public double getTotalSales() {
        return sales.stream()
                .mapToDouble(Sale::getTotal)
                .sum();
    }
    

    

    /**
     * Preloads a set of products into the inventory for demonstration purposes.
     */
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
    /**
     * Adds a product and quantity to the client's cart, validating wallet balance.
     * @param client the client
     * @param productId the product ID
     * @param quantity the quantity to add
     * @throws ProductNotFoundException if the product is not found
     * @throws IllegalArgumentException if the wallet balance is insufficient
     */
    public void addToCart(Client client, String productId, int quantity) throws ProductNotFoundException {
        double currentTotal = calculateClientCartTotal(client);
        Product product = inventory.getStockItem(productId).getProduct();
        double addSubtotal = product.getPrice() * quantity;
        if (currentTotal + addSubtotal > client.getWallet()) {
            throw new IllegalArgumentException("No tiene suficiente dinero en la cartera para agregar este producto al carrito.");
        }
        client.addToCart(productId, quantity);
    }

    /**
     * Removes a specific quantity of a product from the client's cart.
     * @param client the client
     * @param productId the product ID
     * @param quantity the quantity to remove
     */
    public void removeFromCart(Client client, String productId, int quantity) {
        client.removeFromCart(productId, quantity);
    }

    /**
     * Clears the client's cart.
     * @param client the client
     */
    public void clearCart(Client client) {
        client.clearCart();
    }

    /**
     * Gets the cart of the client.
     * @param client the client
     * @return a map of product IDs to quantities
     */
    public Map<String, Integer> getCart(Client client) {
        return client.getCart();
    }

    /**
     * Gets the purchase history of the client.
     * @param client the client
     * @return a list of sales
     */
    public List<Sale> getPurchaseHistory(Client client) {
        return client.getPurchaseHistory();
    }

    /**
     * Creates a new client with the specified attributes.
     * @param name the client's name
     * @param id the client's ID
     * @param wallet the initial wallet balance
     * @return the created client
     */
    public Client createClient(String name, String id, double wallet) {
        Client client = new Client();
        client.setName(name);
        client.setId(id);
        client.setStore(this);
        client.setWallet(wallet);
        return client;
    }

    /**
     * Shows the contents of the client's cart as a formatted string.
     * @param client the client
     * @return a string with cart details
     * @throws ProductNotFoundException if a product is not found
     */
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

    /**
     * Shows the purchase history of the client as a formatted string.
     * @param client the client
     * @return a string with purchase history
     */
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

    // Métodos puente para cartera
    /**
     * Adds money to the client's wallet.
     * @param client the client
     * @param amount the amount to add
     */
    public void rechargeWallet(Client client, double amount) {
        client.addToWallet(amount);
    }

    /**
     * Subtracts money from the client's wallet if possible.
     * @param client the client
     * @param amount the amount to subtract
     * @return true if the amount was subtracted, false otherwise
     */
    public boolean subtractFromWallet(Client client, double amount) {
        return client.subtractFromWallet(amount);
    }

    /**
     * Gets the wallet balance of the client.
     * @param client the client
     * @return the wallet balance
     */
    public double getWallet(Client client) {
        return client.getWallet();
    }
}
