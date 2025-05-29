package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends Person {

   private Map<String, Integer> shopingCart;
   private List<Sale> sales;//lista de Compras
   private Sale sale;
   private Store store;

   public Client() {
    super();
    shopingCart = new HashMap<>();
    sales = new ArrayList<>();
    sale = new Sale();
    store = new Store();
   }

   public void addToShopingCart(String productId, int quantity) {
    shopingCart.put(productId, shopingCart.getOrDefault(productId, 0) + quantity);
   }

   public void removeFromShopingCart(String productId) {
    shopingCart.remove(productId);
   }

   public void removeFromShopingCart(String productId, int quantity) {
    shopingCart.put(productId, shopingCart.getOrDefault(productId, 0) - quantity);
   }

   public void clearShopingCart() {
    shopingCart.clear();
   }

   public void addSale(Sale sale) {
       if (sale != null) {
           sales.add(sale);
       }
   }

   public List<Sale> getSales() {
       return new ArrayList<>(sales);
   }

   public double getTotalSales() {
       double total = 0;
       for (Sale sale : sales) {
         //  total += sale.getTotal();// metodo de Sale
       }
       return total;
   }

   public Sale getCurrentSale() {
       return sale;
   }

   public void setCurrentSale(Sale sale) {
       this.sale = sale;
   }

   public Store getStore() {
       return store;
   }

   public void setStore(Store store) {
       this.store = store;
   }


   

   

   
}
