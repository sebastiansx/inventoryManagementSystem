package co.edu.uptc.model;

public class Store {
    private String name;
    private String address;
    private String city;
    private String phone;
    private Inventory inventory;

    public Store() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClientHistory(String clientId) {
        // Implementation pending
        return "";
    }

    public void registerSale(String saleId) {
        // Implementation pending
    }

    public void addProductN(String id, String name, String description, double price, String categoryN, int amount)
            throws ProductAlreadyExistsException, IllegalArgumentException {
        Category category = Category.valueOf(categoryN.toUpperCase());
        Product productN = new Product(id, name, description, price, category);
        inventory.addNewProduct(productN, amount);

    }

    public void preloadProducts() {
        try {
            inventory.addNewProduct(new Product("001", "Camisa", "Camisa de algodón blanca", 45000.0, Category.ROPA),
                    10);
            inventory.addNewProduct(new Product("002", "Pan", "Pan artesanal integral", 3500.0, Category.COMIDA), 25);
            inventory.addNewProduct(
                    new Product("003", "Ibuprofeno", "Tabletas 200mg para dolor", 7000.0, Category.MEDICAMENTO), 15);
            inventory.addNewProduct(
                    new Product("004", "Shampoo", "Shampoo hidratante de coco 400ml", 15500.0, Category.HIGIENE), 20);
            inventory.addNewProduct(new Product("005", "Audífonos", "Bluetooth con cancelación de ruido", 120000.0,
                    Category.ELECTRONICA), 8);
            inventory.addNewProduct(
                    new Product("006", "Vaso térmico", "Vaso metálico con tapa 500ml", 28000.0, Category.HOGAR), 12);
            inventory.addNewProduct(
                    new Product("007", "Pelota", "Pelota de fútbol tamaño 5", 40000.0, Category.JUGUETES), 18);
            inventory.addNewProduct(
                    new Product("008", "Cuaderno", "Cuaderno cosido 100 hojas", 3800.0, Category.LIBRERIA), 30);
            inventory.addNewProduct(
                    new Product("009", "Galletas", "Galletas de avena sin azúcar 6 unidades", 4900.0, Category.COMIDA),
                    22);
            inventory.addNewProduct(
                    new Product("010", "Pijama", "Pijama para mujer en algodón", 52000.0, Category.ROPA), 14);
        } catch (ProductAlreadyExistsException e) {
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
