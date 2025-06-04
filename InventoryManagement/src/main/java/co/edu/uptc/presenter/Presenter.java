package main.java.co.edu.uptc.presenter;

public class Presenter {
    /*--- MENÚ ADMINISTRADOR --- 

Agregar producto nuevo 

Eliminar producto 

Aumentar stock 

Disminuir stock (manual, si es necesario) 

Ver inventario 

Volver 

 

 

 

--- MENÚ PRINCIPAL --- 

Ingresar como Administrador 

Ingresar como Cliente 

Salir 

 

--- MENÚ CLIENTE --- 

Ver productos disponibles 

Agregar producto al carrito 

Ver carrito 

Comprar 

Ver historial de compras 

Volver 








public void agregarProducto() {
    String id = view.requestInformation("ID:");
    String nombre = view.requestInformation("Nombre:");
    String descripcion = view.requestInformation("Descripción:");
    double precio = view.requestDouble("Precio:");
    String categoria = view.requestInformation("Categoría:");
    int cantidad = view.requestNumber("Cantidad:");

    try {
        store.agregarProductoNuevo(id, nombre, descripcion, precio, categoria, cantidad);
        view.showMessages("✅ Producto agregado correctamente.");
    } catch (ProductAlreadyExistsException e) {
        view.showMessages("❌ " + e.getMessage());
    } catch (IllegalArgumentException e) {
        view.showMessages("⚠️ Categoría inválida.");
    }
}
  

public void eliminarProducto() {
    String id = view.requestInformation("Ingrese el ID del producto a eliminar:");
    try {
        store.getInventory().removeProduct(id);
        view.showMessages("Producto eliminado correctamente.");
    } catch (ProductNotFoundException e) {
        view.showMessages("X" + e.getMessage());
    }
}
public void aumentarStock() {
    String id = view.requestInformation("ID del producto:");
    int cantidad = view.requestNumber("Cantidad a agregar:");
    
    try {
        store.getInventory().addStock(id, cantidad);
        view.showMessages("Stock actualizado correctamente.");
    } catch (ProductNotFoundException e) {
        view.showMessages("X" + e.getMessage());
    } catch (IllegalArgumentException e) {
        view.showMessages("Cantidad inválida. Debe ser mayor que cero.");
    }
}

public void disminuirStock() {
    String id = view.requestInformation("ID del producto:");
    int cantidad = view.requestNumber("Cantidad a retirar:");

    try {
        store.getInventory().removeStock(id, cantidad);
        view.showMessages(" Stock disminuido correctamente.");
    } catch (ProductNotFoundException | InsufficientStockException e) {
        view.showMessages("X " + e.getMessage());
    } catch (IllegalArgumentException e) {
        view.showMessages("Cantidad inválida. Debe ser mayor que cero.");
    }
}

public void consultarStock() {
    String id = view.requestInformation("ID del producto a consultar:");
    try {
        int disponible = store.getInventory().getStock(id);
        view.showMessages(" Stock disponible: " + disponible + " unidades.");
    } catch (ProductNotFoundException e) {
        view.showMessages("X " + e.getMessage());
    }
}

public void mostrarInventario() {
    List<StockItem> inventario = store.getInventory().listInventory();

    if (inventario.isEmpty()) {
        view.showMessages(" El inventario está vacío.");
    } else {
        StringBuilder sb = new StringBuilder();
        for (StockItem item : inventario) {
            Product p = item.getProduct();
            sb.append("ID: ").append(p.getId())
              .append(" | Nombre: ").append(p.getName())
              .append(" | Precio: $").append(p.getPrice())
              .append(" | Categoría: ").append(p.getCategory())
              .append(" | Cantidad: ").append(item.getQuantity())
              .append("\n");
        }
        view.showMessages(sb.toString());
    }
}
public void verProductosDisponibles() {
    String resumen = store.getInventory().getInventorySummary();
    view.showMessages(resumen);
}

  */

}
