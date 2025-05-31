package co.edu.uptc.model;
import java.util.List;

public interface ManageableInventory {
    void addNewProduct(Product product, int quantity);
    void removeProduct(String productId);
    List<StockItem> listInventory();
    
}
