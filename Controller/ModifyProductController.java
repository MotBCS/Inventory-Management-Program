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

/** This class is a controller for the 'Modify Product' screen. Allows user to modify an existing product and re-save it back to the inventory. */
public class ModifyProductController implements Initializable {
    public TextField idText;
    public TextField nameText;
    public TextField maxText;
    public TextField minText;
    public TextField priceText;
    public TextField inventoryText;
    public TextField ModifyProduct_AllPart_Search;

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

    /** Remove Associated Part, Add, Cancel, and Save Buttons */
    public Button ASRemoveBtn;
    public Button addBtn;
    public Button saveModifyProductBtn;
    public Button cancelBtn;

    /** Select Product and index position of item */
    private static Product selection;
    private static int selectionPosition;

    /** Associated Parts */
    private static ObservableList<Part> ASPart = FXCollections.observableArrayList();


    /** --------------------------------------------------------------------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /** Loads all current parts in inventory */
        loadInfo();

        /** All parts Table */
        allPartsTable.setItems(Inventory.getAllParts());
        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        /** Associated Parts Table */
        ASPartsTable.setItems(selection.getAllASPart());
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

    /** @param actionEvent  When user clicks the save button, program will save each input if there are no blank text fields.
     * Then sends user back to home screen */
    public void saveModifyProduct(ActionEvent actionEvent) throws IOException {
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
                    Product refreshProduct = new Product(ID, name, price, stock, min, max);
                    for (Part p : ASPart){
                        refreshProduct.addASPart(p);
                    }
                    Inventory.refreshProduct(selectionPosition, refreshProduct);

                    /** After adding updating existing product returns user to home (MainForm.fxml) screen*/
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

    /** @param actionEvent Uses the search text field to find a parts in the inventory */
    public void searchForAllPart_InModifyProduct(ActionEvent actionEvent) {
        String search = ModifyProduct_AllPart_Search.getText();
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
    public void productSearchOnKeyPressed(KeyEvent keyEvent) {
        if (ModifyProduct_AllPart_Search.getText().isEmpty()){
            allPartsTable.setItems(Inventory.getAllParts());
        }
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent Removes associated part from list */
    public void removeASPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Remove Part?");
        alert.setContentText("Remove Selected Part?");

        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK){
            Part selectPart = (Part) ASPartsTable.getSelectionModel().getSelectedItem();
            ASPart.remove(selectPart);
            ASPartsTable.setItems(ASPart);
        }
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent Adds part to associated list */
    public void addToASPartTable(ActionEvent actionEvent) {
        Part selectPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        ASPart.add(selectPart);
        ASPartsTable.setItems(ASPart);
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** Sends data collected from user's input */
    public static void sendInfo(Product product, int selectedPosition){
        selection = product;
        selectionPosition = selectedPosition;
        ASPart = selection.getAllASPart();
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** Loads data collected from user's input */
    private void loadInfo() {
        /** Gets user input */
        idText.setText(Integer.toString(selection.getId()));
        nameText.setText(selection.getName());
        inventoryText.setText(Integer.toString(selection.getStock()));
        priceText.setText(Double.toString(selection.getPrice()));
        minText.setText(Integer.toString(selection.getMin()));
        maxText.setText(Integer.toString(selection.getMax()));
    }
}
