package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void registerButtonAction ( ) {
        App.setRoot ( "registerScreen" );
    }
    @FXML
    private void loginButtonAction ( ) {
        App.setRoot ( "loginScreen" );

    }
    @FXML
    private void deleteAccountButtonAction ( ) {
        Stage window = new Stage ( );
        window.setTitle ( "Delete Account" );
    }
    @FXML
    private void changePasswordButtonAction ( ) {
        Stage window = new Stage ( );
        window.setTitle ( "Change Password" );
    }
    @FXML
    private void exitButton () {
        if ( ConfirmBox.display ( "You Boutta Finish This Thing" , "Are You Sure You Finna Finish This Thing?" ) )
            Platform.exit ( );
    }

}
