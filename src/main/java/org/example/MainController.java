package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static org.example.User.getUserWithName;
import static org.example.App.error;

public class MainController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField newPasswordField;

    @FXML private Button destroyButton;

    @FXML
    private void registerButtonAction ( ) {
        App.setRoot ( "registerScreen" );
    }
    @FXML
    private void loginButtonAction ( ) {
        App.setRoot ( "loginScreen" );

    }

    @FXML
    private void deleteAccountButtonAction ( ) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource( "deleteAccountScreen.fxml" ));
        Stage window = new Stage ( );
        window.setTitle ( "Delete Account" );
        window.setScene ( new Scene ( root ) );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.showAndWait ();
    }

    @FXML private void deleteAccountAcceptButtonAction () {
        User.allUsers.remove ( getUserWithName ( usernameField.getText () ) );
        destroyButtonAction ();
    }

    @FXML private void changePasswordButtonAction ( ) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource( "changePasswordScreen.fxml" ));
        Stage window = new Stage ( );
        window.setTitle ( "Change Password" );
        window.setScene ( new Scene ( root ) );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.showAndWait ();
    }
    @FXML private void changePasswordAcceptButtonAction () {
        User user = User.getUserWithName ( usernameField.getText () );
        if (user == null)
            error ( "This Person Isn't Real Yet." );
        else if (user.getPassword ().equals ( passwordField.getText () )) {
            user.setPassword ( newPasswordField.getText ( ) );
            destroyButtonAction ();
        }
        else
            error ( "Wrong Password" );

    }

    @FXML private void destroyButtonAction () {
        Stage stage = (Stage) destroyButton.getScene().getWindow();
        stage.close ();
    }

    @FXML
    private void exitButton () {
        if ( ConfirmBox.display ( "You Boutta Finish This Thing" , "Are You Sure You Finna Finish This Thing?" ) )
            Platform.exit ( );
    }

}
