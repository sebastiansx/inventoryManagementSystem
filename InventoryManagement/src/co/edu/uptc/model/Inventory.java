package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory implements ManageableInventory {

    private Map<String, StockItem> stockMap;

    public Inventory(Map<String, StockItem> stockMap) {
        this.stockMap = new HashMap<>();
    }

    @Override
    public void addNewProduct(Product product, int quantity) throws ProductAlreadyExistsException {
        String id = product.getId();
        if (stockMap.containsKey(id)) {
            throw new ProductAlreadyExistsException("El producto con ID " + id + " ya existe en el Inventario.");

        }
        stockMap.put(id, new StockItem(product, quantity));

    }

    @Override
    public List<StockItem> listInventory() {
        // TODO Auto-generated method stub

        return new ArrayList<>(stockMap.values());
    }

    @Override
    public void removeProduct(String productId) throws ProductNotFoundException {
        // TODO Auto-generated method stub
        if (!stockMap.containsKey(productId)) {
            throw new ProductNotFoundException("El producto con ID " + productId + " no existe en el inventario.");
        }
        stockMap.remove(productId);

    }

    public void addStock(String productId, int amount) throws ProductNotFoundException {
        StockItem stockItem = stockMap.get(productId);
        if (stockItem == null) {
            throw new ProductNotFoundException("No se encontró el producto con ID " + productId + " en el inventario.");
        }
        stockItem.addQuantity(amount);
    }

    public void removeStock(String productId, int amount)
        throws ProductNotFoundException, InsufficientStockException {

        StockItem item = stockMap.get(productId);
        if (item == null) {
            throw new ProductNotFoundException("No se encontró el producto con ID " + productId + " en el inventario.");

        }
        item.removeQuantity(amount);
    }

    public int getStock(String idProduct) throws ProductNotFoundException {
        StockItem stockItem = stockMap.get(idProduct);
        if (stockItem == null) {
            throw new ProductNotFoundException("No se encontró el producto con ID " + idProduct + " en el inventario.");
        }
        return stockItem.getQuantity();
    }

    public String getInventorySummary() {
        StringBuilder sb = new StringBuilder();
        for (StockItem stock : stockMap.values()) {
            Product p = stock.getProduct();
            sb.append("ID: ").append(p.getId())
                    .append(" | Nombre: ").append(p.getName())
                    .append(" | Precio: $").append(p.getPrice())
                    .append(" | Categoría: ").append(p.getCategory())
                    .append(" | Cantidad disponible: ").append(stock.getQuantity())
                    .append("\n");
        }
        return sb.toString();
    }

}
