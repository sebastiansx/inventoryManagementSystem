package co.edu.uptc.presenter;


import co.edu.uptc.model.*;
import co.edu.uptc.view.*;

public class Presenter {

    private Store store;
    private View view;

    public Presenter() {
        store = new Store();
        view = new View();
    }

    // Método principal para iniciar la app y mostrar menú principal
    public void start() {
        while (true) {
            view.showMessage("=== BIENVENIDO ===");
            view.showMessage("1. Ingresar como Administrador");
            view.showMessage("2. Ingresar como Cliente");
            view.showMessage("3. Salir");
            int option = view.getInt("Seleccione una opción:");

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
            view.showMessage("=== MENÚ ADMINISTRADOR ===");
            view.showMessage("1. Agregar nuevo producto");
            view.showMessage("2. Eliminar producto");
            view.showMessage("3. Agregar stock a producto");
            view.showMessage("4. Remover stock de producto");
            view.showMessage("5. Ver resumen de inventario");
            view.showMessage("6. Volver al menú principal");
            int option = view.getInt("Seleccione una opción:");

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
            } catch (Exception e) {
                view.showErrorMessage("Error: " + e.getMessage());
            }
        }
    }

    // Menú para cliente - DEJAMOS ESPACIO RESERVADO PARA FACTURACIÓN
    private void clientMenu() {
        view.showMessage("=== MENÚ CLIENTE ===");
        view.showMessage("Funcionalidad de cliente aún no implementada.");
        view.showMessage("Espacio reservado para facturación y otras operaciones.");
        // Aquí podrías llamar métodos relacionados con carrito, compra, historial, etc.
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

    // Puedes agregar más métodos para manejo de StockItem o Product según necesites
}
