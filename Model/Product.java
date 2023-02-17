package thomas.inventoryappc482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> ASPart = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** Setters */
    /** @return product id */
    public int getId() {
        return id;
    }

    /** @param id sets ID of product*/
    public void setId(int id) {
        this.id = id;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @return product name */
    public String getName() {
        return name;
    }

    /** @param name sets the name of each product */
    public void setName(String name) {
        this.name = name;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @return product price */
    public double getPrice() {
        return price;
    }

    /** @param price sets price of each product */
    public void setPrice(double price) {
        this.price = price;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @return level of stock of each product */
    public int getStock() {
        return stock;
    }

    /** @param stock sets the product inventory level */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @return min inventory level */
    public int getMin() {
        return min;
    }

    /** @param min sets the min level for the product inventory */
    public void  setMin(int min) {
        this.min = min;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @return max inventory level */
    public int getMax() {
        return max;
    }

    /** @param max sets the max level for the product inventory */
    public void setMax(int max) {
        this.max = max;
    }

    /** --------------------------------------------------------------------------------------------------------------*/


    /** @param part Associated part handling
     *  Will add specified parts to the associate parts list for the desired product */
    public void addASPart(Part part) {
        ASPart.add(part);
    }

    /** Deletes specified associate parts from list */
    public boolean removeASPart(Part selectASPart) {
        return ASPart.remove(selectASPart);
    }

    /** @return the list of associated parts for the product */
    public ObservableList<Part> getAllASPart() {
        return ASPart;
    }
}
