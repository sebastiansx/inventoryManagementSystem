package co.edu.uptc.model;

/**
 * Represents a stock entry in the inventory system.
 * It contains a product and the number of units available.
 */
public class StockItem {
    private Product product;
    private int quantity;

    /**
     * Constructs a StockItem with a given product and initial quantity.
     *
     * @param product  the product associated with this stock
     * @param quantity the initial quantity available
     */
    public StockItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Constructs a StockItem with a given product and quantity of 0.
     *
     * @param product the product associated with this stock
     */
    public StockItem(Product product){
        this(product,0);
    }

    /**
     * Returns the product associated with this stock item.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product for this stock item.
     *
     * @param product the product to assign
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Returns the quantity available for this product.
     *
     * @return the available quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity available for this product.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Increases the quantity of this stock item by a given amount.
     *
     * @param amount the quantity to add
     * @throws IllegalArgumentException if the amount is negative
     */
    public void addQuantity(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("No se puede agregar cantidad negativa");
        this.quantity += amount;
    }

    /**
     * Decreases the quantity of this stock item by a given amount.
     *
     * @param amount the quantity to remove
     * @throws InsufficientStockException if the requested amount exceeds available quantity
     */
    public void removeQuantity(int amount) throws InsufficientStockException {
        if (amount > quantity) {
            throw new InsufficientStockException(
                    "No hay suficiente stock disponible. Cantidad solicitada: " + amount + ", disponible: " + quantity);
        }
        this.quantity -= amount;
    }

    /**
     * Returns a string representation of this stock item, showing product name and quantity.
     *
     * @return a formatted string with product name and available quantity
     */
    @Override
    public String toString() {
        return "Producto: " + product.getName() + " | Cantidad: " + quantity;
    }
}
