package thomas.inventoryappc482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Optional;
import java.util.ResourceBundle;
/** --------------------------------------------------------------------------------------------------------------*/

/** This class is a controller for the 'Product' screen. Allows user to create a new product and save to inventory */
public class AddProductController implements Initializable {
    public Button cancelBtn;
    public Button saveAddProductBtn;
    public TextField idText;
    public TextField nameText;
    public TextField maxText;
    public TextField minText;
    public TextField priceText;
    public TextField inventoryText;
    public TextField AddProduct_AllPart_Search;

    /** Remove Associated Part and add Button*/
    public Button ASRemoveBtn;
    public Button addBtn;

    /** All Parts Table */
    public TableView allPartsTable;
    public TableColumn allPartIdColumn;
    public TableColumn allPartNameColumn;
    public TableColumn allPartInventoryColumn;
    public TableColumn allPartPriceColumn;

    /** Associated Parts Table */
    public TableView ASPartsTable;
    public TableColumn ASPartIdColumn;
    public TableColumn ASPartNameColumn;
    public TableColumn ASInventoryPartColumn;
    public TableColumn ASPartPriceColumn;
    /** Associated Parts */
    private ObservableList<Part> ASPart = FXCollections.observableArrayList();

    /** --------------------------------------------------------------------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idText.setText(String.valueOf(Inventory.getRandomIdValue())); /** Generates random ID for each product */

        /** Binding columns to Parts and Associated Parts tables */

        /** Parts Table Columns */
        allPartsTable.setItems(Inventory.getAllParts());
        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        /** Associated Parts Table Columns */
        ASPartsTable.setItems(ASPart);
        ASPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ASPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ASInventoryPartColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ASPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** --------------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent When the cancel button is clicked, user will be returned to home screen (main) */
    public void toHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        Scene scene = new Scene(root, 800.0, 388.0);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent Assigns input from user to specific parameters to create a new part */
    public void saveAddProduct(ActionEvent actionEvent) throws IOException {
        if (idText.getText().isEmpty() || nameText.getText().isEmpty() || priceText.getText().isEmpty() || inventoryText.getText().isEmpty() || minText.getText().isEmpty() || maxText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Invalid input or blank text field");
            alert.showAndWait();
        }
        /**  Gets user input */
        else {
            try {
                /** Get user input from text fields */
                int ID = Integer.parseInt(idText.getText());
                String name = nameText.getText();
                double price = Double.parseDouble(priceText.getText());
                int stock = Integer.parseInt(inventoryText.getText());
                int min = Integer.parseInt(minText.getText());
                int max = Integer.parseInt(maxText.getText());

                if (min > max){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Minimum value cannot be greater than maximum value");
                    alert.showAndWait();
                }
                else if (stock <= min || stock >= max){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Stock value cannot exceed max value or be below min value");
                    alert.showAndWait();
                }
                else {
                    Product newProduct = new Product(ID, name, price, stock, min, max);
                    for (Part p : ASPart){
                        newProduct.addASPart(p);
                    }
                    Inventory.addProducts(newProduct);
                    /** After adding new product returns user to home (MainForm.fxml) screen*/
                    Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                    Stage stage = (Stage) cancelBtn.getScene().getWindow();
                    Scene scene = new Scene(root, 800.0, 388.0);
                    stage.setTitle("Home");
                    stage.setScene(scene);
                    stage.show();
                }
            }catch (Exception exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Enter a value for all available text fields");
                alert.showAndWait();
            }
        }
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent Uses the search text field to find a part in the inventory*/
    public void searchForAllPart_InAddProduct(ActionEvent actionEvent) {
        String search = AddProduct_AllPart_Search.getText();
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
                alert.setTitle("Part Not Found");
                alert.setContentText("Unable to find part under: " + search);
                alert.showAndWait();
                /** If unable to find searched part an ERROR is given along with used search term to inform user */
            }
        }
        allPartsTable.setItems(parts);
    }

    /** ----------------------------------------------------------------------------------------------------------*/
    /** Part Search bar Refresh, repopulates tables when text field is empty */
    /** On key pressed */
    /** @param keyEvent This key event repopulates the 'parts' table when the text field of the search bar is empty*/
    public void addProductSearchOnKeyPressed(KeyEvent keyEvent) {
        if (AddProduct_AllPart_Search.getText().isEmpty()){
            allPartsTable.setItems(Inventory.getAllParts());
        }
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent Removes associated part from list */
    public void removeASPart(ActionEvent actionEvent) {
        Part selectPart = (Part) ASPartsTable.getSelectionModel().getSelectedItem();
        if (selectPart != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Remove Part?");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == ButtonType.OK){
                ASPart.remove(selectPart);
                ASPartsTable.setItems(ASPart);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a part");
            alert.setContentText("Select a part to remove from associated list");
            alert.showAndWait();
        }
    }
    /** ----------------------------------------------------------------------------------------------------------*/
    /** @param actionEvent Adds part to associated list */
    public void addToASPartTable(ActionEvent actionEvent) {
        Part selectPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        if (selectPart != null){
            ASPart.add(selectPart);
            ASPartsTable.setItems(ASPart);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Selection");
            alert.setContentText("Select a part to add");
            alert.showAndWait();
        }
    }
}
