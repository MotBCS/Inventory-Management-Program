package thomas.inventoryappc482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Random;
/** --------------------------------------------------------------------------------------------------------------*/


/** The inventory class contains all the parts and products */
public class Inventory {
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static  ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** @param newPart adds part to all parts in the inventory */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** @param newProduct add product to all products in the inventory */
    public static void addProducts(Product newProduct){
        allProducts.add(newProduct);
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @return Generate random ID value for parts and product */
    public static int getRandomIdValue(){
        Random random = new Random();
        int max = 9999;
        int id = random.nextInt(max);
        return id;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @return  Finds part in 'allParts' using the parts ID */
    public static Part findPart(int partID){
        for (Part p : allParts){
            if (p.getId() == partID){
                return p;
            }
        }
        return null;
    }
    /** --------------------------------------------------------------------------------------------------------------*/
    /** @return Finds products in 'allProducts' using the product ID */
    public static Product findProduct(int productID) {
        for (Product pd : allProducts) {
            if (pd.getId() == productID) {
                return pd;
            }
        }
        return null;
    }
    /** ----SEARCH----------------------------------------------------------------------------------------------------------*/

    /** @return Finds Part in 'allParts' using part name, string input is converted to lowercase */
    public static ObservableList<Part> findPart(String partName) {
        ObservableList<Part> sortPartsByName = FXCollections.observableArrayList();
        for (Part p : allParts) {
            if (p.getName().toLowerCase().contains(partName.toLowerCase().trim())) {
                sortPartsByName.add(p);
            }
        }
        return sortPartsByName;
    }

    /** @return Finds Product in 'allProducts' using product name, string input is converted to lowercase */
    public static ObservableList<Product> findProduct(String productName) {
        ObservableList<Product> sortProductByName = FXCollections.observableArrayList();
        for (Product pd : allProducts) {
            if (pd.getName().toLowerCase().contains(productName.toLowerCase().trim())) {
                sortProductByName.add(pd);
            }
        }
        return sortProductByName;
    }
    /** ----UPDATE----------------------------------------------------------------------------------------------------------*/

    /** Refreshes part in 'allParts' list */
    public static void refreshPart(int position, Part selectPart){
        allParts.set(position, selectPart);
    }

    /** Refreshes products in 'allProducts' list */
    public static void refreshProduct(int position, Product selectProduct){
        allProducts.set(position, selectProduct);
    }

    /** -----DELETION---------------------------------------------------------------------------------------------------------*/

    /** @return Selected part is deleted , the boolean value will return true when the selected part is deleted from allParts list*/
    public static boolean deletePart(Part selectPart){
        return allParts.remove(selectPart);
    }

    /** @return Selected product is deleted , the boolean value will return true when the selected product is deleted from allProducts list*/
    public static boolean deleteProduct(Product selectProduct){
        return allProducts.remove(selectProduct);
    }


    /** ---RETURN CURRENT LIST (PRODUCTS AND PARTS)-----------------------------------------------------------------------------------------------------------*/
    /** @return list of all current parts in 'allParts' list*/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /** @return list of all current products in 'allProducts' list*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
