package main.java.co.edu.uptc.model;

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
        this.inventory = new Inventory(new HashMap<>());
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
        }
    }
}
