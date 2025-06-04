package co.edu.uptc.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class Sale {
    private String id;
    private Client client;
    private LocalDate date;
    private Map<Product, Integer> items;
    private double total;

    public Sale(String id, Client client) {
        this.id = id;
        this.client = client;
        this.date = LocalDate.now();
        this.items = new HashMap<>();
        this.total = 0.0;
    }

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
        calculateTotal();
    }

    public void removeItem(Product product) {
        items.remove(product);
        calculateTotal();
    }

    private void calculateTotal() {
        this.total = items.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

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

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Product, Integer> items) {
        this.items = items;
        calculateTotal();
    }

    public double getTotal() {
        return total;
    }
}
