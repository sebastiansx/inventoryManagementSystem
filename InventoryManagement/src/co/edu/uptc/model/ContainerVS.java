package co.edu.uptc.model;

import java.util.Map;
import java.util.HashMap;

public class ContainerVS {
    private Map<String, Product> products;

    public ContainerVS() {
        this.products = new HashMap<>();
    }

    public void addItem(String id, Product product) {
        products.put(id, product);
    }

    public void removeItem(String id) {
        products.remove(id);
    }
} 