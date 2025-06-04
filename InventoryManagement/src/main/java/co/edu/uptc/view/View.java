package co.edu.uptc.view;

import javax.swing.JOptionPane;

public class View {

    String result;
    JOptionPane jp;

    /////////////////////////////// CONSTRUCTOR ///////////////////////////////

    public View() {
        jp = new JOptionPane();
        result = "";
    }

    /////////////////////////////// METHODS ///////////////////////////////

    public int getInt(String message)  {
        result = JOptionPane.showInputDialog(message);
        return Integer.parseInt(result);
    }

    public String getString(String message) {
        result = JOptionPane.showInputDialog(message);
        return result;
    }

    public double getDouble(String message) {
        result = JOptionPane.showInputDialog(message);
        return Double.parseDouble(result);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}