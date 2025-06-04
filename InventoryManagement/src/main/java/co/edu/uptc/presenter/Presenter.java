package co.edu.uptc.presenter;


import co.edu.uptc.model.Store;
import co.edu.uptc.model.ProductAlreadyExistsException;
import co.edu.uptc.model.ProductNotFoundException;
import co.edu.uptc.model.InsufficientStockException;
import co.edu.uptc.view.View;
import co.edu.uptc.model.Product;
import co.edu.uptc.model.Category;
import co.edu.uptc.model.Client;
import co.edu.uptc.model.Sale;

public class Presenter {
    private Store store;
    private View view;

    public Presenter() {
        store = new Store();
        view = new View();
        start();
    }

    // Método principal para iniciar la app y mostrar menú principal
    private void start() {
        while (true) {
            String welcome = view.getString("=== BIENVENIDO ===\n1. Ingresar como Administrador\n2. Ingresar como Cliente\n3. Salir\nSeleccione una opción:");
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
                    clientMenu();
                    break;
                case 3:
                    view.showMessage("Gracias por usar la aplicación.");
                    return;
                default:
                    view.showMessage("Opción inválida, intente nuevamente.");
            }
        }
    }

    // Menú para administrador con manejo de productos e inventario
    private void adminMenu() {
        boolean exit = false;
        while (!exit) {
            String adminMenu = view.getString("=== MENÚ ADMINISTRADOR ===\n1. Agregar nuevo producto\n2. Eliminar producto\n3. Agregar stock a producto\n4. Remover stock de producto\n5. Ver resumen de inventario\n6. Volver al menú principal\nSeleccione una opción:");
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
                view.showErrorMessage("Error: " + e.getMessage());
            }catch(ProductNotFoundException e){
                view.showErrorMessage("Error: " + e.getMessage());
            }catch(InsufficientStockException e){
                view.showErrorMessage("Error: " + e.getMessage());
            }
        }
    }

    // Menú para cliente - ahora funcional
    private void clientMenu() {
        String clientName = view.getString("Ingrese su nombre:");
        String clientId = view.getString("Ingrese su cédula o ID:");
        Client client = store.createClient(clientName, clientId);
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
                "7. Volver al menú principal\n" +
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

    // Métodos para manejar productos e inventario:

    private void addProduct() throws ProductAlreadyExistsException {
        String id = view.getString("Ingrese ID del producto:");
        String name = view.getString("Ingrese nombre del producto:");
        String description = view.getString("Ingrese descripción:");
        double price = view.getDouble("Ingrese precio:");
        // Mostrar las categorías disponibles (enum)
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

    private void removeProduct() throws ProductNotFoundException {
        String id = view.getString("Ingrese ID del producto a eliminar:");
        store.removeProduct(id);
        view.showMessage("Producto eliminado correctamente.");
    }

    private void addStock() throws ProductNotFoundException {
        String id = view.getString("Ingrese ID del producto para agregar stock:");
        int amount = view.getInt("Ingrese cantidad a agregar:");
        store.addStock(id, amount);
        view.showMessage("Stock agregado correctamente.");
    }

    private void removeStock() throws ProductNotFoundException, InsufficientStockException {
        String id = view.getString("Ingrese ID del producto para remover stock:");
        int amount = view.getInt("Ingrese cantidad a remover:");
        store.removeStock(id, amount);
        view.showMessage("Stock removido correctamente.");
    }

    private void showInventory() {
        String summary = store.getInventorySummary();
        if (summary.isEmpty()) {
            view.showMessage("El inventario está vacío.");
        } else {
            view.showMessage(summary);
        }
    }

    // Métodos auxiliares para el menú de cliente
    private void addToCart(Client client) throws ProductNotFoundException {
        String productId = view.getString("Ingrese el ID del producto a agregar al carrito:");
        int quantity = view.getInt("Ingrese la cantidad:");
        int stock = store.getStock(productId);
        if (stock < quantity) {
            view.showMessage("No hay suficiente stock disponible. Stock actual: " + stock);
            return;
        }
        store.addToCart(client, productId, quantity);
        view.showMessage("Producto agregado al carrito.");
    }

    private void removeFromCart(Client client) {
        String productId = view.getString("Ingrese el ID del producto a remover del carrito:");
        int quantity = view.getInt("Ingrese la cantidad a remover:");
        store.removeFromCart(client, productId, quantity);
        view.showMessage("Producto removido del carrito.");
    }

    private void processPurchase(Client client) throws ProductNotFoundException, InsufficientStockException {
        if (store.getCart(client).isEmpty()) {
            view.showMessage("El carrito está vacío. No se puede procesar la compra.");
            return;
        }
        Sale sale = store.processSale(client);
        view.showMessage("Compra realizada con éxito.\n\n" + sale.getReceiptInfo());
    }

    // Puedes agregar más métodos para manejo de StockItem o Product según necesites
}
