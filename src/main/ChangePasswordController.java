package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePasswordController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField newPasswordField;
    @FXML private Button destroyButton;

    @FXML private void changePasswordAcceptButtonAction () {
        User user = User.getUserWithName ( usernameField.getText () );
        if (user == null)
            App.error ( "This Person Isn't Real Yet." );
        else if (user.getPassword ().equals ( passwordField.getText () )) {
            user.setPassword ( newPasswordField.getText ( ) );
            destroyButtonAction ();
        }
        else
            App.error ( "Wrong Password" );

    }

    @FXML private void destroyButtonAction () {
        Stage stage = (Stage) destroyButton.getScene().getWindow();
        stage.close ();
    }
}
