package main.java.co.edu.uptc.model;
import java.util.List;

public interface ManageableInventory {
    void addNewProduct(Product product, int quantity) throws ProductAlreadyExistsException;
    void removeProduct(String productId) throws ProductNotFoundException;
    List<StockItem> listInventory();
}
