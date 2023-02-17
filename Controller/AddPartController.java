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
/** The AddPartController class is a controller for the 'Add Part' Screen, Allows user to create a new part and save to inventory */
public class AddPartController implements Initializable {
    public RadioButton inHouseRadioBtn;
    public RadioButton outSourceRadioBtn;
    public TextField idText;
    public TextField nameText;
    public TextField inventoryText;
    public TextField priceText;
    public TextField maxText;
    public TextField minText;
    public TextField machineID_CompanyName_Text;
    /** Used to change label text between 'Machine ID' for In-house parts and 'Company' for Outsourced parts */
    public Label machineID_CompanyName;
    public Button saveBtn;
    public Button cancelBtn;
    public ToggleGroup tgOption;

    /** --------------------------------------------------------------------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idText.setText(String.valueOf(Inventory.getRandomIdValue()));

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

    /** @param actionEvent Assigns input from user to specific parameters to create a new part */
    public void saveAddedPart(ActionEvent actionEvent) throws IOException{
        /** Check to make sure ID, name, price, inventory, min, max and machine ID or Company is not empty, if it is
         * empty user receives an error to inform them of the empty text field */
        if (idText.getText().isEmpty() || nameText.getText().isEmpty() || priceText.getText().isEmpty() || inventoryText.getText().isEmpty() || minText.getText().isEmpty() || maxText.getText().isEmpty() || machineID_CompanyName_Text.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Invalid input or blank text field");
            alert.showAndWait();
        }
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
                        InHouse part = new InHouse(ID, name, price, stock, min, max, machineID);
                        Inventory.addPart(part);
                    } else if (outSourceRadioBtn.isSelected()) {
                        String company = machineID_CompanyName_Text.getText();
                        OutSource part = new OutSource(ID, name, price, stock, min, max, company);
                        Inventory.addPart(part);

                    }
                    /** When new part is added, returns user to home screen (MainForm.fxml)*/
                    Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                    Stage stage = (Stage) cancelBtn.getScene().getWindow();
                    Scene scene = new Scene(root, 800.0, 388.0);
                    stage.setTitle("Home");
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (Exception exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Input");
                alert.showAndWait();
            }
        }
    }
    /** ----------------------------------------------------------------------------------------------------------*/
    /** @param actionEvent Changes label between 'Machine ID' and 'Company' Depending on which radio button is selected*/
    public void toInHouseOption(ActionEvent actionEvent) {
        /** @param actionEvent When 'in-house' is selected the created part will be associated with in-house parts
         * the machineID_Company name label will be changed depending on which radio option is selected.'Machine ID'
         * for in-house and 'Company' for out-sourced*/
        machineID_CompanyName.setText("Machine ID");
    }
    /** ----------------------------------------------------------------------------------------------------------*/
    /** @param actionEvent Changes label between 'Machine ID' and 'Company' Depending on which radio button is selected*/
    public void toOutSourcedOption(ActionEvent actionEvent) {
        /** @param actionEvent When 'out-source' option is selected the created part will be associated with out-sourced parts
         * the machineID_Company name label will be changed depending on which radio option is selected.'Machine ID'
         * for in-house and 'Company' for out-sourced*/
        machineID_CompanyName.setText("Company");
    }
}
