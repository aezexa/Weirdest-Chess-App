package main;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.regex.Pattern;

import static main.App.getFXMLLoader;
import static main.App.sound;

public class LoginController {

    private static final Pattern userPassFormatPattern = Pattern.compile ( "\\w+" );

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;


    @FXML
    private void loginButtonAction () throws IOException {

        String name = usernameField.getText ();
        String password = passwordField.getText ();

        if ( RegisterController.hasNoFormatErrors ( name , password ) &&
                hasNoLoginRemoveErrors ( name , password ) ) {

            User.setWhiteUser ( User.getUserWithName ( name ) );

            FXMLLoader loader = getFXMLLoader ( "loginCompletedScreen" );
            Parent root = loader.load ();
            LoginCompletedController lcc = loader.getController ();
            lcc.setName ( name );
            Stage window = new Stage ( );
            window.setTitle ( "Welcome To The Game " + name + "!" );
            Scene scene = new Scene ( root );
            scene.addEventFilter( MouseEvent.MOUSE_PRESSED, mouseEvent -> sound () );
            window.setScene ( scene );
            window.initModality ( Modality.APPLICATION_MODAL );
            window.centerOnScreen ();
            window.show ();

            PauseTransition delay = new PauseTransition ( Duration.seconds(3));
            delay.setOnFinished( event -> {
                window.close();
                App.setRoot ( "gameMenuScreen" );
            } );
            scene.addEventHandler ( KeyEvent.KEY_PRESSED, keyEvent -> {
                delay.stop ();
                window.close();
                App.setRoot ( "gameMenuScreen" );
            } );
            delay.play();
        }
    }

    private static boolean hasNoLoginRemoveErrors ( String name , String password ) {
        User tempUser;
        if ( (tempUser = User.getUserWithName ( name )) == null ) {
            App.error (  "This Person Isn't Real Yet."  );
            return false;
        } else if ( !tempUser.getPassword ( ).equals ( password ) ) {
            App.error ( "Wrong Password" );
            return false;
        } else
            return true;
    }
    @FXML
    private void cancelButtonAction () {
        App.setRoot ( "mainScreen" );
    }

}
