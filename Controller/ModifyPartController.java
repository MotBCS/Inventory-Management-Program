package thomas.inventoryappc482;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** --------------------------------------------------------------------------------------------------------------*/

/** This class is a controller for the 'Modify Part' screen. Allows user to modify an existing part and re-save it back to the inventory. */
public class ModifyPartController implements Initializable {

    /** Text fields */
    public TextField idText;
    public TextField nameText;
    public TextField inventoryText;
    public TextField priceText;
    public TextField maxText;
    public TextField machineID_CompanyName_Text;
    public Label machineID_CompanyName;
    public TextField minText;

    /** Buttons */
    public Button cancelBtn;
    public Button saveBtn;
    public RadioButton inHouseRadioBtn;
    public RadioButton outSourceRadioBtn;

    /**private static Part selection;*/
    private static int selectionPosition;
    private static Part selection;

    /** Toggle group for in-house and out-source radio buttons */
    public ToggleGroup tgOption;


/** --------------------------------------------------------------------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInfo();
    }

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
    public void saveModifyPart(ActionEvent actionEvent) throws IOException {
        if (idText.getText().isEmpty() || nameText.getText().isEmpty() || priceText.getText().isEmpty() || inventoryText.getText().isEmpty() || minText.getText().isEmpty() || maxText.getText().isEmpty() || machineID_CompanyName_Text.getText().isEmpty()){
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
                    /** If the in-house or Outsource radio button is selected the part parameters change
                     * depending on which ever is selected. In-house adds 'Machine ID' and
                     * Out-source adds 'company' to the part constructor and collects user input*/
                    if (inHouseRadioBtn.isSelected()){
                        int machineID = Integer.parseInt(machineID_CompanyName_Text.getText());
                        InHouse refreshPart = new InHouse(ID, name, price, stock, min, max, machineID);
                        Inventory.getAllParts().set(selectionPosition, refreshPart);
                        Inventory.refreshPart(selectionPosition, refreshPart);
                    } else if (outSourceRadioBtn.isSelected()) {
                        String company = machineID_CompanyName_Text.getText();
                        OutSource refreshPart = new OutSource(ID, name, price, stock, min, max, company);
                        Inventory.refreshPart(selectionPosition, refreshPart);
                    }

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
                alert.setContentText("Invalid Input");
                alert.showAndWait();
            }
        }
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent When in-house is selected the created part will be associated with in-house parts
     * the machineID_Company name label will be changed depending on which radio option is selected.'Machine ID'
     * for in-house and 'Company' for out-sourced*/
    public void toInHouseOption(ActionEvent actionEvent) {
        machineID_CompanyName.setText("Machine ID");
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** @param actionEvent When out-source option is selected the created part will be associated with out-sourced parts
     * the machineID_Company name label will be changed depending on which radio option is selected.'Machine ID'
     * for in-house and 'Company' for out-sourced*/
    public void toOutSourcedOption(ActionEvent actionEvent) {
        machineID_CompanyName.setText("Company");
    }
    /** ----------------------------------------------------------------------------------------------------------*/

    /** Sends user's input from modify part to main controller */
    public static void sendInfo(Part selectPart, int selectedPosition) {
        selection = selectPart;
        selectionPosition = selectedPosition;
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

        if (selection instanceof InHouse){
            inHouseRadioBtn.setSelected(true); /** If the in-house radio button is selected (true) from the loaded data, text will be set to 'Machine ID'*/
            machineID_CompanyName.setText("Machine ID");
            tgOption.selectToggle(inHouseRadioBtn);
            machineID_CompanyName_Text.setText(String.valueOf(((InHouse) selection).getMachineID()));
        }
        if (selection instanceof OutSource){
            outSourceRadioBtn.setSelected(true);/** If the out-source radio button is selected (true) from the loaded data, text will be set to 'Company'*/
            machineID_CompanyName.setText("Company");
            tgOption.selectToggle(outSourceRadioBtn);
            machineID_CompanyName_Text.setText(String.valueOf(((OutSource) selection).getCompany()));
        }
    }
}
