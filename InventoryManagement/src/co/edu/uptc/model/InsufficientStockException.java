package co.edu.uptc.model;

public class InsufficientStockException extends Exception {

    public InsufficientStockException (String message){
        super(message);
    }
}
