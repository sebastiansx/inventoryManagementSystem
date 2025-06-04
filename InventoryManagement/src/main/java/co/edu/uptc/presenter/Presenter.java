package co.edu.uptc.presenter;

import co.edu.uptc.model.*;
import co.edu.uptc.view.View;

/**
 * Handles the main application logic, user interaction, and communication between the view and the model.
 */
public class Presenter {
    private Store store;
    private View view;

    /**
     * Initializes the Presenter, Store, and View, and starts the application.
     */
    public Presenter() {
        store = new Store();
        view = new View();
        preloadDefaultClients();
        start();
    }

    /**
     * Preloads some default clients with id as username and a password.
     */
    private void preloadDefaultClients() {
        // Example clients
        store.addUser(new User("Juan Perez", "1001", "123456789", "juan@mail.com", "1001", "pass123", "client"));
        store.addUser(new User("Maria Gomez", "1002", "987654321", "maria@mail.com", "1002", "maria456", "client"));
        store.addUser(new User("Carlos Ruiz", "1003", "555555555", "carlos@mail.com", "1003", "carlos789", "client"));
    }

    /**
     * Main loop to display the main menu and handle user selection.
     */
    private void start() {
        while (true) {
            String welcome = view.getString(
                "=== BIENVENIDO ===\n1. Ingresar como Administrador\n2. Ingresar como Cliente\n3. Salir\nSeleccione una opción:");
            int option;
            try {
                option = Integer.parseInt(welcome);
            } catch (NumberFormatException e) {
                view.showMessage("Opción inválida, intente nuevamente.");
                continue;
            }
            switch (option) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    clientLogin();
                    break;
                case 3:
                    view.showMessage("Gracias por usar la aplicación.");
                    return;
                default:
                    view.showMessage("Opción inválida, intente nuevamente.");
            }
        }
    }

    /**
     * Displays the administrator menu and handles admin actions.
     */
    private void adminMenu() {
        boolean exit = false;
        while (!exit) {
            String adminMenu = view.getString(
                "=== MENÚ ADMINISTRADOR ===\n1. Agregar nuevo producto\n2. Eliminar producto\n3. Agregar stock a producto\n4. Remover stock de producto\n5. Ver resumen de inventario\n6. Volver al menú principal\nSeleccione una opción:");
            int option;
            try {
                option = Integer.parseInt(adminMenu);
            } catch (NumberFormatException e) {
                view.showMessage("Opción inválida, intente nuevamente.");
                continue;
            }
            try {
                switch (option) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        removeProduct();
                        break;
                    case 3:
                        addStock();
                        break;
                    case 4:
                        removeStock();
                        break;
                    case 5:
                        showInventory();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        view.showMessage("Opción inválida, intente nuevamente.");
                }
            } catch (ProductAlreadyExistsException e) {
                view.showErrorMessage("Error el producto ya existe: " + e.getMessage());
            } catch (ProductNotFoundException e) {
                view.showErrorMessage("Error el producto no existe: " + e.getMessage());
            } catch (InsufficientStockException e) {
                view.showErrorMessage("Error el stock es insuficiente: " + e.getMessage());
            }
        }
    }

    /**
     * Handles client login and, if successful, shows the client menu.
     */
    private void clientLogin() {
        String id = view.getString("Ingrese su cédula o ID:");
        String password = view.getString("Ingrese su contraseña:");
        boolean authenticated = store.authenticateUser(id, password);
        if (authenticated) {
            User user = store.getUserByUsername(id);
            // Create a Client object from User data for the menu
            Client client = new Client();
            client.setName(user.getName());
            client.setId(user.getId());
            client.setWallet(0); // Or preload wallet if you want
            client.setStore(store);
            clientMenu(client);
        } else {
            view.showMessage("Credenciales incorrectas. Intente nuevamente.");
        }
    }

    /**
     * Displays the client menu and handles client actions for an authenticated client.
     */
    private void clientMenu(Client client) {
        boolean exit = false;
        while (!exit) {
            String menu = view.getString(
                "=== MENÚ CLIENTE ===\n" +
                "1. Ver productos disponibles\n" +
                "2. Agregar producto al carrito\n" +
                "3. Remover producto del carrito\n" +
                "4. Ver carrito y total\n" +
                "5. Realizar compra\n" +
                "6. Ver historial de compras\n" +
                "7. Recargar cartera\n" +
                "8. Volver al menú principal\n" +
                "Seleccione una opción:");
            int option;
            try {
                option = Integer.parseInt(menu);
            } catch (NumberFormatException e) {
                view.showMessage("Opción inválida, intente nuevamente.");
                continue;
            }
            try {
                switch (option) {
                    case 1:
                        showInventory();
                        break;
                    case 2:
                        addToCart(client);
                        break;
                    case 3:
                        removeFromCart(client);
                        break;
                    case 4:
                        view.showMessage(store.showCart(client));
                        break;
                    case 5:
                        processPurchase(client);
                        break;
                    case 6:
                        view.showMessage(store.showPurchaseHistory(client));
                        break;
                    case 7:
                        rechargeWallet(client);
                        break;
                    case 8:
                        exit = true;
                        break;
                    default:
                        view.showMessage("Opción inválida, intente nuevamente.");
                }
            } catch (Exception e) {
                view.showErrorMessage("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Adds a new product to the inventory.
     * @throws ProductAlreadyExistsException if the product already exists
     */
    private void addProduct() throws ProductAlreadyExistsException {
        String id = view.getString("Ingrese ID del producto:");
        String name = view.getString("Ingrese nombre del producto:");
        String description = view.getString("Ingrese descripción:");
        double price = view.getDouble("Ingrese precio:");
        view.showMessage("Categorías disponibles:");
        for (Category cat : Category.values()) {
            view.showMessage("- " + cat);
        }
        String categoryStr = view.getString("Ingrese categoría:");
        Category category;
        try {
            category = Category.valueOf(categoryStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            view.showMessage("Categoría inválida, usando categoría por defecto: ROPA");
            category = Category.ROPA;
        }
        int quantity = view.getInt("Ingrese cantidad inicial:");
        Product product = new Product(id, name, description, price, category);
        store.addNewProduct(product, quantity);
        view.showMessage("Producto agregado exitosamente.");
    }

    /**
     * Removes a product from the inventory.
     * @throws ProductNotFoundException if the product is not found
     */
    private void removeProduct() throws ProductNotFoundException {
        String id = view.getString("Ingrese ID del producto a eliminar:");
        store.removeProduct(id);
        view.showMessage("Producto eliminado correctamente.");
    }

    /**
     * Adds stock to an existing product.
     * @throws ProductNotFoundException if the product is not found
     */
    private void addStock() throws ProductNotFoundException {
        String id = view.getString("Ingrese ID del producto para agregar stock:");
        int amount = view.getInt("Ingrese cantidad a agregar:");
        store.addStock(id, amount);
        view.showMessage("Stock agregado correctamente.");
    }

    /**
     * Removes stock from an existing product.
     * @throws ProductNotFoundException if the product is not found
     * @throws InsufficientStockException if there is not enough stock
     */
    private void removeStock() throws ProductNotFoundException, InsufficientStockException {
        String id = view.getString("Ingrese ID del producto para remover stock:");
        int amount = view.getInt("Ingrese cantidad a remover:");
        store.removeStock(id, amount);
        view.showMessage("Stock removido correctamente.");
    }

    /**
     * Shows a summary of the inventory.
     */
    private void showInventory() {
        String summary = store.getInventorySummary();
        if (summary.isEmpty()) {
            view.showMessage("El inventario está vacío.");
        } else {
            view.showMessage(summary);
        }
    }

    /**
     * Adds a product to the client's cart.
     * @param client the client
     * @throws ProductNotFoundException if the product is not found
     */
    private void addToCart(Client client) throws ProductNotFoundException {
        String productId = view.getString("Ingrese el ID del producto a agregar al carrito:");
        int quantity = view.getInt("Ingrese la cantidad:");
        int stock = store.getStock(productId);
        if (stock < quantity) {
            view.showMessage("No hay suficiente stock disponible. Stock actual: " + stock);
            return;
        }
        try {
            store.addToCart(client, productId, quantity);
            view.showMessage("Producto agregado al carrito.");
        } catch (IllegalArgumentException e) {
            view.showMessage(e.getMessage());
        }
    }

    /**
     * Removes a product from the client's cart.
     * @param client the client
     */
    private void removeFromCart(Client client) {
        String productId = view.getString("Ingrese el ID del producto a remover del carrito:");
        int quantity = view.getInt("Ingrese la cantidad a remover:");
        store.removeFromCart(client, productId, quantity);
        view.showMessage("Producto removido del carrito.");
    }

    /**
     * Processes the purchase for the client, deducts the wallet, and shows the receipt.
     * @param client the client
     * @throws ProductNotFoundException if a product is not found
     * @throws InsufficientStockException if there is not enough stock
     */
    private void processPurchase(Client client) throws ProductNotFoundException, InsufficientStockException {
        if (store.getCart(client).isEmpty()) {
            view.showMessage("El carrito está vacío. No se puede procesar la compra.");
            return;
        }
        double total = store.calculateClientCartTotal(client);
        if (store.getWallet(client) < total) {
            view.showMessage("No tiene suficiente dinero en la cartera para realizar la compra. Total: $" + total + ", Cartera: $" + store.getWallet(client));
            return;
        }
        store.subtractFromWallet(client, total);
        Sale sale = store.processSale(client);
        view.showMessage("Compra realizada con éxito. Se descontó $" + total + " de su cartera.\n\n" + sale.getReceiptInfo());
        if (store.getWallet(client) < 1000) {
            view.showMessage("Advertencia: Su saldo en la cartera es bajo ($" + store.getWallet(client) + ").");
        }
    }

    /**
     * Allows the client to recharge their wallet.
     * @param client the client
     */
    private void rechargeWallet(Client client) {
        double amount = view.getDouble("¿Cuánto dinero desea agregar a su cartera?:");
        if (amount <= 0) {
            view.showMessage("Debe ingresar un monto positivo.");
            return;
        }
        store.rechargeWallet(client, amount);
        view.showMessage("Cartera recargada exitosamente. Saldo actual: $" + store.getWallet(client));
    }
}



