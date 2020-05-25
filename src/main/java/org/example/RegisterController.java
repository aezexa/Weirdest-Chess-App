package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static org.example.User.allUsers;

public class RegisterController implements Initializable {

    private static final Pattern userPassFormatPattern = Pattern.compile ( "\\w+" );

    @FXML
    private Label registerLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button destroyButton;

    ScreensController myController;


    @FXML
    private void registerButtonAction () throws IOException {

        String name = usernameField.getText ();
        String password = passwordField.getText ();

        if ( hasNoFormatErrors ( name , password ) &&
                !hasRegisterErrors ( name ) ) {

            allUsers.add ( new User ( name , password ) );
            Parent root = FXMLLoader.load(App.class.getResource( "registrationCompletedScreen.fxml" ));
            Stage window = new Stage ( );
            window.setTitle ( "Registeration Completed!!1" );
            window.setScene ( new Scene ( root ) );
            window.initModality ( Modality.APPLICATION_MODAL );
            window.showAndWait ();
            App.setRoot ( "mainScreen" );
        }
    }

    public static boolean hasNoFormatErrors ( String name , String password ) {
        if ( !userPassFormatPattern.matcher ( name ).matches ( ) ) {
            Alert alert = new Alert ( Alert.AlertType.ERROR );
            alert.setContentText ( "Username Format Is Invalid" );
            alert.showAndWait ();
            return false;
        } else if ( !userPassFormatPattern.matcher ( password ).matches ( ) ) {
            Alert alert = new Alert ( Alert.AlertType.ERROR );
            alert.setContentText ( "Password Format Is Invalid" );
            alert.showAndWait ();
            return false;
        } else
            return true;
    }

    private static boolean hasRegisterErrors ( String name ) {
        if ( getUserWithName ( name ) != null ) {
            Alert alert = new Alert ( Alert.AlertType.ERROR );
            alert.setContentText ( "A User Exists With This Username" );
            alert.showAndWait ();
            return true;
        }
        return false;
    }

    public static User getUserWithName ( String name ) {
        for (User user : allUsers) {
            if ( user.getName ( ).equals ( name ) )
                return user;
        }
        return null;
    }

    @FXML
    private void cancelButtonAction () {
        App.setRoot ( "mainScreen" );
    }

    @FXML
    private void destroyButtonAction () {
        Stage stage = (Stage) destroyButton.getScene().getWindow();
        stage.close ();
    }

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {

    }
}
