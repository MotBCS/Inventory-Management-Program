package thomas.inventoryappc482;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
/** ------------------------------------------------------------------------------------------------------*/
/** This class is the controller for the 'Main' (Home) screen. Allows user to search for parts and products by name or ID
 * as well as go to modify or add screen for both parts and products */

public class MainController implements Initializable {
    /** Buttons , Text-fields, and Labels for home (main) screen of inventory application */
    public Button exitBtn;
    public Label homePage;

    /** Parts Table and Columns */
    public TableView partTable;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;

    /** Part Table Buttons */
    public Button deletePartBtn;
    public Button modifyPartBtn;
    public Button addPartBtn;
    public TextField searchPartsBar;

    /** Products Table and Columns */
    public TableView productTable;
    public TableColumn productIdColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryColumn;
    public TableColumn productPriceColumn;

    /** Product Table Buttons */
    public Button deleteProductsBtn;
    public Button modifyProductBtn;
    public Button addProductBtn;
    public TextField searchProductBar;

    /** For Dummy Data */
    public static boolean newItem = true;

    /** Inventory and Selection */
    private Part selectPart;
    private Product selectProduct;
    private int selectedPosition;


    /** ----------------------------------------------------------------------------------------------------------*/
    /** Dummy data for testing */
    public void dummyData(){
        if(!newItem){
            return;
        }
        newItem = false;
        /** In-house Parts */
        /** getRandomIdValue was created to generate a random ID value for each part and product */
        InHouse x = new InHouse(Inventory.getRandomIdValue(), "Hard Shell", 14.95,55,1,100,9000);
        InHouse y = new InHouse(Inventory.getRandomIdValue(), "Backlight", 10.25,72,1,100,7211);
        InHouse z = new InHouse(Inventory.getRandomIdValue(), "Button Pack", 7.95,67,1,100,8937);
        /** Out-sourced Parts */
        OutSource w = new OutSource(Inventory.getRandomIdValue(), "Battery", 29.95, 95,1,100,"BtrBattery");
        Inventory.addPart(x);
        Inventory.addPart(y);
        Inventory.addPart(z);
        Inventory.addPart(w);

        /** Products */
        Product a = new Product(Inventory.getRandomIdValue(), "GameBoy", 159.99,20,1,100);
        Product b = new Product(Inventory.getRandomIdValue(), "WiiU", 209.99,14,1,100);
        Product c = new Product(Inventory.getRandomIdValue(), "DS Lite", 99.99,10,1,100);
        Inventory.addProducts(a);
        Inventory.addProducts(b);
        Inventory.addProducts(c);
    }
    /** --------------------------------------------------------------------------------------------------------*/
    /**
     * Runtime Error:
     * Caused by: java.lang.NullPointerException: Cannot invoke "javafx.scene.control.TableColumn.setCellValueFactory(javafx.util.Callback)" because "this.partPriceColumn" is null
     * 	at thomas.inventoryappc482/thomas.inventoryappc482.MainController.initialize(MainController.java:104)
     *
     * 	This was a small error caused by a typo in the part table columns' name. To fix this issue I went back all looked over all the
     * 	columns in the tables and checked to make sure all the names were correctly spelled.
     * */

    /** Button Functionality */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dummyData();

        /** Binding columns to Parts and Products tables */
        /** Parts Table Columns */
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        /** Products Table Columns */
        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /** ------------------------------------------------------------------------------------------------------- */


    /** Parts */
    /** @param actionEvent Uses the search text field to locate a specific part in the 'allParts' inventory */
    public void searchParts(ActionEvent actionEvent) {
        String search = searchPartsBar.getText();
        ObservableList<Part> parts = Inventory.findPart(search);
        if (parts.isEmpty()){
            try {
                int searchID = Integer.parseInt(search);
                Part part = Inventory.findPart(searchID);
                if (part != null){
                    parts.add(part);
                }
            } catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Part Not Found");
                alert.setContentText("Unable to find part under: " + search);
                alert.showAndWait();
                /** If unable to find searched part an ERROR is given along with used search term to inform user */
            }
        }
        partTable.setItems(parts);
    }

    /** --------------------------------------------------------------------------------------------------------*/

    /** @param keyEvent This key event repopulates the 'parts' table when the text field of the search bar is empty */
    /** On key pressed */
    public void partSearchOnKeyPressed(KeyEvent keyEvent) {
        if (searchPartsBar.getText().isEmpty()){
            partTable.setItems(Inventory.getAllParts());
        }
    }
    /** --------------------------------------------------------------------------------------------------------*/


    /** @param actionEvent When the 'add' button is clicked, the user will be taken to 'addPartForm.fxml' */
    public void addPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPartForm_InHome.fxml"));
        Stage stage = (Stage) addPartBtn.getScene().getWindow();
        Scene scene = new Scene(root, 800.0,350.0);
        stage.setTitle("Add New Part");
        stage.setScene(scene);
        stage.show();
    }
    /** --------------------------------------------------------------------------------------------------------*/


    /** @param actionEvent When the 'modify' button is clicked and a part is selected, user will be taken to 'ModifyPartForm.fxml' */
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        selectPart = (Part) partTable.getSelectionModel().getSelectedItem();
        selectedPosition = Inventory.getAllParts().indexOf(selectPart);
        ModifyPartController.sendInfo(selectPart, selectedPosition);
        if (selectPart != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartForm_InHome.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) modifyPartBtn.getScene().getWindow();
            Scene scene = new Scene(root, 800.0,350.0);
            stage.setTitle("Modify Existing Part");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Select a part to modify");
            alert.showAndWait();
        }
    }
    /** --------------------------------------------------------------------------------------------------------*/


    /** @param actionEvent Deletes selected part (Gives user a deletion confirmation to delete
     * and an error if invalid input is given)*/
    public void deletePart(ActionEvent actionEvent) {
        Part selectPart = (Part) partTable.getSelectionModel().getSelectedItem();
        if (selectPart != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete Part?");
            alert.setContentText("Delete Selected Part?");

            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == ButtonType.OK){
                Inventory.deletePart(selectPart); /** Deletes selected part if user selects the choice 'OK' */
            }
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText("Invalid Input");
            alert2.setContentText("Select part to delete");
            alert2.showAndWait();
        }
    }

    /** --------------------------------------------------------------------------------------------------------*/

    /** Products */
    /** @param actionEvent Uses the search text field to locate a specific product in the inventory */
    public void searchProduct(ActionEvent actionEvent) {
        String search = searchProductBar.getText();
        ObservableList<Product> products = Inventory.findProduct(search);
        if (products.isEmpty()){
            try {
                int searchID = Integer.parseInt(search);
                Product product = Inventory.findProduct(searchID);
                if (product != null){
                    products.add(product);
                }
            } catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product Not Found");
                alert.setContentText("Unable to find product under: " + search);
                alert.showAndWait();
                /** If unable to find searched product an ERROR is given along with used search term to inform user */
            }
        }
        productTable.setItems(products);
    }

    /** --------------------------------------------------------------------------------------------------------*/

    /** Product Search bar Refresh, repopulates tables when text field is empty */
    /** On key pressed */
    public void productSearchOnKeyPressed(KeyEvent keyEvent) {
        if (searchProductBar.getText().isEmpty()){
            productTable.setItems(Inventory.getAllProducts());
        }
    }
    /** --------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent When the 'add' button is clicked, the user will be taken to 'addProductFormLG.fxml' */
    public void addProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddProductFormLG.fxml"));
        Stage stage = (Stage) addProductBtn.getScene().getWindow();
        Scene scene = new Scene(root, 800.0,760.0);
        stage.setTitle("Add New Product");
        stage.setScene(scene);
        stage.show();
    }
    /** --------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent When the 'modify' button is clicked and a part is selected, user will be taken to 'ModifyProductFormLG.fxml' */
    public void modifyProduct(ActionEvent actionEvent) throws IOException{
        selectProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        selectedPosition = Inventory.getAllProducts().indexOf(selectProduct);

        if (selectProduct != null){
            ModifyProductController.sendInfo(selectProduct, selectedPosition);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProductFormLG.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) modifyProductBtn.getScene().getWindow();
            Scene scene = new Scene(root, 800.0,760.0);
            stage.setTitle("Modify Existing Product");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Select a product to modify");
            alert.showAndWait();
        }
    }
    /** --------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent Deletes selected product (Gives user a deletion confirmation to delete
     * and an error if invalid input is given)*/
    public void deleteProduct(ActionEvent actionEvent) {
        Product selectProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        if ((selectProduct != null) && (selectProduct.getAllASPart().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete Product?");
            alert.setContentText("Delete Selected Product?");

            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == ButtonType.OK){
                Inventory.deleteProduct(selectProduct); /** Deletes selected product if user selects the choice 'OK' */
            }
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText("Invalid Input");
            alert2.setContentText("Cannot delete product with associated parts");
            alert2.showAndWait();
        }
    }

    /** ----------------------------------------------------------------------------------------------*/

    /**@param actionEvent Exits the program, user receives an alert to notify them before closing the application */
    public void exitApp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Close Program?");
        alert.setHeaderText("Are you sure you want to exit the program?");

        /** If user selects the choice 'OK' the program will close, otherwise it will stay open */
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK){
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
        }

    }
}


