package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteAccountController {

    @FXML private TextField usernameField;

    @FXML private Button destroyButton;

    @FXML private void deleteAccountAcceptButtonAction () {
        User user = User.getUserWithName ( usernameField.getText () );
        if (user == null) {
            App.error ( "This Person Isn't Real Yet." );
            return;
        }
        User.allUsers.remove ( user );
        destroyButtonAction ();
    }

    @FXML private void destroyButtonAction () {
        Stage stage = (Stage) destroyButton.getScene().getWindow();
        stage.close ();
    }

}
