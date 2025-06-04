package main.java.co.edu.uptc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends Person {

   private Map<String, Integer> cart;
   private List<Sale> purchases;
   private Sale currentSale;
   private Store store;

   public Client() {
    super();
    cart = new HashMap<>();
    purchases = new ArrayList<>();
   }

   public void addToCart(String productId, int quantity) {
    cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
   }

   public void removeFromCart(String productId) {
    cart.remove(productId);
   }

   public void removeFromCart(String productId, int quantity) {
    int currentQty = cart.getOrDefault(productId, 0);
    if (currentQty <= quantity) {
        cart.remove(productId);
    } else {
        cart.put(productId, currentQty - quantity);
    }
   }

   public void clearCart() {
    cart.clear();
   }

   public void addPurchase(Sale sale) {
       purchases.add(sale);
   }

   public List<Sale> getPurchaseHistory() {
       return purchases;
   }

   public double getTotalPurchases() {
       return purchases.stream()
               .mapToDouble(Sale::getTotal)
               .sum();
   }

   public Sale getCurrentSale() {
       return currentSale;
   }

   public void setCurrentSale(Sale sale) {
       this.currentSale = sale;
   }

   public Store getStore() {
       return store;
   }

   public void setStore(Store store) {
       this.store = store;
   }

   public Map<String, Integer> getCart() {
       return cart;
   }

   @Override
   public String mostrarPerfil() {
       return "Cliente: " + getName() + " | Email: " + getEmail() + " | Teléfono: " + getPhone();
   }
}
