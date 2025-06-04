package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a client in the system, who can have a cart, make purchases, and manage a wallet.
 */
public class Client extends Person {

   private Map<String, Integer> cart;
   private List<Sale> purchases;
   private Sale currentSale;
   private Store store;
   private double wallet;

   /**
    * Constructs a new Client with empty cart and purchase history.
    */
   public Client() {
    super();
    cart = new HashMap<>();
    purchases = new ArrayList<>();
   }

   /**
    * Adds a product and quantity to the cart.
    * @param productId the product ID
    * @param quantity the quantity to add
    */
   public void addToCart(String productId, int quantity) {
    cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
   }

   /**
    * Removes a product from the cart.
    * @param productId the product ID
    */
   public void removeFromCart(String productId) {
    cart.remove(productId);
   }

   /**
    * Removes a specific quantity of a product from the cart.
    * @param productId the product ID
    * @param quantity the quantity to remove
    */
   public void removeFromCart(String productId, int quantity) {
    int currentQty = cart.getOrDefault(productId, 0);
    if (currentQty <= quantity) {
        cart.remove(productId);
    } else {
        cart.put(productId, currentQty - quantity);
    }
   }

   /**
    * Clears all items from the cart.
    */
   public void clearCart() {
    cart.clear();
   }

   /**
    * Adds a completed sale to the purchase history.
    * @param sale the sale to add
    */
   public void addPurchase(Sale sale) {
       purchases.add(sale);
   }

   /**
    * Returns the purchase history of the client.
    * @return a list of sales
    */
   public List<Sale> getPurchaseHistory() {
       return purchases;
   }

   /**
    * Returns the total value of all purchases made by the client.
    * @return the total purchases value
    */
   public double getTotalPurchases() {
       return purchases.stream()
               .mapToDouble(Sale::getTotal)
               .sum();
   }

   /**
    * Gets the current sale in progress.
    * @return the current sale
    */
   public Sale getCurrentSale() {
       return currentSale;
   }

   /**
    * Sets the current sale in progress.
    * @param sale the sale to set
    */
   public void setCurrentSale(Sale sale) {
       this.currentSale = sale;
   }

   /**
    * Gets the store associated with this client.
    * @return the store
    */
   public Store getStore() {
       return store;
   }

   /**
    * Sets the store associated with this client.
    * @param store the store to set
    */
   public void setStore(Store store) {
       this.store = store;
   }

   /**
    * Gets the cart of the client.
    * @return a map of product IDs to quantities
    */
   public Map<String, Integer> getCart() {
       return cart;
   }

   /**
    * Gets the current wallet balance.
    * @return the wallet balance
    */
   public double getWallet() {
       return wallet;
   }

   /**
    * Sets the wallet balance.
    * @param wallet the new wallet balance
    */
   public void setWallet(double wallet) {
       this.wallet = wallet;
   }

   /**
    * Adds money to the wallet.
    * @param amount the amount to add
    */
   public void addToWallet(double amount) {
       this.wallet += amount;
   }

   /**
    * Subtracts money from the wallet if sufficient funds are available.
    * @param amount the amount to subtract
    * @return true if the amount was subtracted, false otherwise
    */
   public boolean subtractFromWallet(double amount) {
       if (this.wallet >= amount) {
           this.wallet -= amount;
           return true;
       }
       return false;
   }

   /**
    * Returns a string representation of the client's profile.
    * @return formatted profile string
    */
   @Override
   public String showProfile() {
       return "Cliente: " + getName() + " | Email: " + getEmail() + " | Teléfono: " + getPhone();
   }
}
