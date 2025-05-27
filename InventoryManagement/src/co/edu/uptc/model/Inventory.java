package co.edu.uptc.model;

import java.util.Map;
import java.util.HashMap;

public class Inventory {
    private Map<Product, Integer> products;

    public Inventory() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product, Integer quantity) {
        products.put(product, quantity);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void updateQuantity(Product product, Integer quantity) {
        if (products.containsKey(product)) {
            products.put(product, quantity);
        }
    }

    public Integer getQuantity(Product product) {
        return products.getOrDefault(product, 0);
    }
}
