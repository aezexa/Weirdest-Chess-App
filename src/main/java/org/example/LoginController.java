package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static org.example.RegisterController.hasNoFormatErrors;
import static org.example.App.error;
import static org.example.User.getUserWithName;

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

        if ( hasNoFormatErrors ( name , password ) &&
                hasNoLoginRemoveErrors ( name , password ) ) {

//            Parent root = FXMLLoader.load(App.class.getResource( "loginCompletedScreen.fxml" ));
            FXMLLoader loader = new FXMLLoader ( App.class.getResource( "loginCompletedScreen.fxml" ));
            Parent root = loader.load ();
            LoginCompletedController lcc = loader.getController ();
            lcc.setName ( name );
            Stage window = new Stage ( );
            window.setTitle ( "Welcome To The Game " + name + "!" );
            window.setScene ( new Scene ( root ) );
            window.initModality ( Modality.APPLICATION_MODAL );
            window.centerOnScreen ();
            window.show ();
//            Parent root1 = FXMLLoader.load(App.class.getResource( "gameMenuScreen.fxml" ));
            PauseTransition delay = new PauseTransition ( Duration.seconds(3));
            delay.setOnFinished( event -> {
                window.close();
                App.setRoot ( "gameMenuScreen" );
//                App.currentStage.setScene ( new Scene ( root1 ) );
//                App.currentStage.centerOnScreen ();
            } );
            delay.play();
        }
    }

    private static boolean hasNoLoginRemoveErrors ( String name , String password ) {
        User tempUser;
        if ( (tempUser = getUserWithName ( name )) == null ) {
            error (  "This Person Isn't Real Yet."  );
            return false;
        } else if ( !tempUser.getPassword ( ).equals ( password ) ) {
            error ( "Wrong Password" );
            return false;
        } else
            return true;
    }
    @FXML
    private void cancelButtonAction () {
        App.setRoot ( "mainScreen" );
    }

}
