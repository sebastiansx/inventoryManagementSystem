package co.edu.uptc.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a sale transaction, including the client, items, date, and total amount.
 */
public class Sale {
    private String id;
    private Client client;
    private LocalDate date;
    private Map<Product, Integer> items;
    private double total;

    /**
     * Constructs a new Sale for a given client and generates the current date.
     * @param id the unique sale identifier
     * @param client the client making the purchase
     */
    public Sale(String id, Client client) {
        this.id = id;
        this.client = client;
        this.date = LocalDate.now();
        this.items = new HashMap<>();
        this.total = 0.0;
    }

    /**
     * Adds a product and its quantity to the sale.
     * @param product the product to add
     * @param quantity the quantity purchased
     */
    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
        calculateTotal();
    }

    /**
     * Removes a product from the sale.
     * @param product the product to remove
     */
    public void removeItem(Product product) {
        items.remove(product);
        calculateTotal();
    }

    /**
     * Calculates the total amount for the sale based on items and their prices.
     */
    private void calculateTotal() {
        this.total = items.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

    /**
     * Returns a formatted receipt string for the sale.
     * @return the receipt information
     */
    public String getReceiptInfo() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("ID: ").append(id).append("\n");
        receipt.append("Fecha: ").append(date).append("\n");
        receipt.append("Cliente: ").append(client.getName()).append("\n");
        receipt.append("Productos:\n");
        
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            double subtotal = product.getPrice() * quantity;
            
            receipt.append(product.getName())
                  .append(" x").append(quantity)
                  .append(" - Precio unitario: ").append(String.format("%.2f", product.getPrice()))
                  .append(" - Subtotal: ").append(String.format("%.2f", subtotal))
                  .append("\n");
        }
        
        receipt.append("Total: ").append(String.format("%.2f", total));
        return receipt.toString();
    }

    /**
     * Gets the sale ID.
     * @return the sale ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the sale ID.
     * @param id the new sale ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the client associated with the sale.
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client for the sale.
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Gets the date of the sale.
     * @return the sale date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the sale.
     * @param date the new sale date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the items in the sale.
     * @return a map of products to quantities
     */
    public Map<Product, Integer> getItems() {
        return items;
    }

    /**
     * Sets the items in the sale and recalculates the total.
     * @param items the new items map
     */
    public void setItems(Map<Product, Integer> items) {
        this.items = items;
        calculateTotal();
    }

    /**
     * Gets the total amount of the sale.
     * @return the total
     */
    public double getTotal() {
        return total;
    }
}
