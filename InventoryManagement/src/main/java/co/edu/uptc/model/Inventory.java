package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Inventory class implements the ManageableInventory interface to manage
 * a collection of StockItem objects mapped by product ID.
 * It allows adding, removing, and updating stock quantities.
 */
public class Inventory implements ManageableInventory {

    private Map<String, StockItem> stockMap;

    /**
     * Default constructor for the Inventory class.
     * Initializes the internal stockMap as an empty HashMap.
     */
    public Inventory() {
        this.stockMap = new HashMap<>();
    }

    /**
     * Adds a new product to the inventory with the specified quantity.
     *
     * @param product  the Product to be added
     * @param quantity the initial quantity of the product
     * @throws ProductAlreadyExistsException if a product with the same ID already
     *                                       exists
     */
    @Override
    public void addNewProduct(Product product, int quantity) throws ProductAlreadyExistsException {
        String id = product.getId();
        if (stockMap.containsKey(id)) {
            throw new ProductAlreadyExistsException("El producto con ID " + id + " ya existe en el Inventario.");
        }
        stockMap.put(id, new StockItem(product, quantity));
    }

    /**
     * Returns a list of all StockItems in the inventory.
     *
     * @return a List of StockItem
     */
    @Override
    public List<StockItem> listInventory() {
        return new ArrayList<>(stockMap.values());
    }

    /**
     * Removes a product from the inventory by its ID.
     *
     * @param productId the ID of the product to be removed
     * @throws ProductNotFoundException if the product does not exist
     */
    @Override
    public void removeProduct(String productId) throws ProductNotFoundException {
        if (!stockMap.containsKey(productId)) {
            throw new ProductNotFoundException("El producto con ID " + productId + " no existe en el inventario.");
        }
        stockMap.remove(productId);
    }

    /**
     * Adds stock to an existing product.
     *
     * @param productId the ID of the product
     * @param amount    the amount of stock to add
     * @throws ProductNotFoundException if the product does not exist
     */
    public void addStock(String productId, int amount) throws ProductNotFoundException {
        StockItem stockItem = stockMap.get(productId);
        if (stockItem == null) {
            throw new ProductNotFoundException("No se encontró el producto con ID " + productId + " en el inventario.");
        }
        stockItem.addQuantity(amount);
    }

    /**
     * Removes stock from an existing product.
     *
     * @param productId the ID of the product
     * @param amount    the amount of stock to remove
     * @throws ProductNotFoundException   if the product does not exist
     * @throws InsufficientStockException if there is not enough stock to remove
     */
    public void removeStock(String productId, int amount)
            throws ProductNotFoundException, InsufficientStockException {
        StockItem item = stockMap.get(productId);
        if (item == null) {
            throw new ProductNotFoundException("No se encontró el producto con ID " + productId + " en el inventario.");
        }
        item.removeQuantity(amount);
    }

    /**
     * Retrieves a StockItem from the inventory by product ID.
     *
     * @param productId the ID of the product
     * @return the StockItem corresponding to the ID
     * @throws ProductNotFoundException if the product is not found
     */
    public StockItem getStockItem(String productId) throws ProductNotFoundException {
        StockItem item = stockMap.get(productId);
        if (item == null) {
            throw new ProductNotFoundException("No se encontró el producto con ID " + productId + " en el inventario.");
        }
        return item;
    }

    /**
     * Gets the quantity in stock for a given product.
     *
     * @param idProduct the ID of the product
     * @return the quantity in stock
     * @throws ProductNotFoundException if the product is not found
     */
    public int getStock(String idProduct) throws ProductNotFoundException {
        StockItem stockItem = stockMap.get(idProduct);
        if (stockItem == null) {
            throw new ProductNotFoundException("No se encontró el producto con ID " + idProduct + " en el inventario.");
        }
        return stockItem.getQuantity();
    }

    /**
     * Returns a formatted string summarizing the inventory.
     *
     * @return a string with product details and quantities
     */
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
