package thomas.inventoryappc482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/** ----------------------------------------------------------------------------------------------------------*/

/** When the Main class is launched, the user is taken to 'MainForm.fxml' which will be considered the Home page of the application */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800.0, 388.0);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <p><b>RUNTIME ERROR:<b/><p/>
     *<br>
     * A few errors I encountered during the creation of my project:
     * When adding dummy data to test the parts and products tables, the products would flash into the tables
     * then disappear. After making some adjustments and correcting the fxId of a couple aspects the tables populated
     * correctly with the correct test data.
     * Another problem I encountered was getting the search bar to properly recognize the elements in the parts and
     * products table. When testing the search bar, if I didn't type the part name exactly, it
     * would give an alert that the item wasn't found. To fix this, I converted the string to all lowercase
     * no matter how it is typed in the search bar. There is still an issue with whitespace in the search bar, the
     * proper spacing has to be included in the name when searching for a part, or it will not pop up in the table.
     *<br>
     * --- (FIXED) Need to work on tables repopulating after searching for item --
     *         - Added an 'On Key Pressed' action event, with an if-statement, if the search bar is empty return all items
     *<br>
     * --- (FIXED) Need to fix In-House and Outsourced Toggle (Needs to select one radio button at a time) --
     *          - Added a 'ToggleGroup' to the radio buttons in Scene Builder
     *<br>
     * -- (FiXED) Need to fix IDs (Ids should be uniquely generated and text field should be disabled from user) --
     *          - Created getRandomIdValue() to generate a random ID number for each part and product,
     *            added the FXML 'editable' tag to the text field of ID and set it to 'false', so the generated
     *            ID can not be changed by user.
     *<br>
     * -- (FIXED) Need to fix search error, when searching part or product by ID --
     *          - Error message was in the wrong place, the error message would execute before checking the product
     *            or part for the ID number. So I moved the Error alert underneath the if-statements.
     *<br>
     * -- (FIXED) Need to fix repopulate search part table in modify and add product tables --
     * *         - Added an 'On Key Pressed' action event, with an if-statement, if the search bar is empty return all items
     *<br>
     *
     * <p><b>FUTURE ENHANCEMENT:<b/><p/>
     * <br>
     * In the future I would like to continue fixing the search bar features, add a more fun GUI, and add
     * a dark mode/ light mode toggle, that matches the operating systems' preference.
     *
     * */
    public static void main(String[] args) {
        launch();
    }
}
