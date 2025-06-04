package co.edu.uptc.model;

public class StockItem {
    private Product product;
    private int quantity;

    public StockItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public StockItem(Product product){
        this(product,0);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("No se puede agregar cantidad negativa");
        this.quantity += amount;
    }

    public void removeQuantity(int amount) throws InsufficientStockException {
        if (amount > quantity) {
            throw new InsufficientStockException(
                    "No hay suficiente stock disponible. Cantidad solicitada: " + amount + ", disponible: " + quantity);
        }
        this.quantity -= amount;
    }

    @Override
    public String toString() {
        return "Producto: " + product.getName() + " | Cantidad: " + quantity;
    }

}
