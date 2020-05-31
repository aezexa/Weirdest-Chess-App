package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.App.*;

public class MainController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField newPasswordField;
    @FXML private ImageView playPauseImageView;
    @FXML private CheckBox dumbSoundCheckBox;

    @FXML private Button destroyButton;

    @FXML private void registerButtonAction ( ) {
        App.setRoot ( "registerScreen" );
    }
    @FXML private void loginButtonAction ( ) {
        App.setRoot ( "loginScreen" );
    }

    @FXML private void deleteAccountButtonAction ( ) throws IOException {
        Parent root = getFXMLLoader ( "deleteAccountScreen" ).load ();
        Stage window = new Stage ( );
        window.setTitle ( "Delete Account" );
        Scene scene = new Scene ( root );
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, mouseEvent -> sound () );
        window.setScene ( scene );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.showAndWait ();
    }

    @FXML private void deleteAccountAcceptButtonAction () {
        User user = User.getUserWithName ( usernameField.getText () );
        if (user == null) {
            App.error ( "This Person Isn't Real Yet." );
            return;
        }
        User.allUsers.remove ( user );
        destroyButtonAction ();
    }

    @FXML private void changePasswordButtonAction ( ) throws IOException {
        Parent root = getFXMLLoader ( "changePasswordScreen" ).load ();
        Stage window = new Stage ( );
        window.setTitle ( "Change Password" );
        Scene scene = new Scene ( root );
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, mouseEvent -> sound () );
        window.setScene ( scene );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.showAndWait ();
    }
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

    @FXML private void playPauseAction () {

        if (isPaused) {
            backgroundSong.play ( );
            playPauseImageView.setImage ( pauseImage );
        }
        else {
            backgroundSong.pause ( );
            playPauseImageView.setImage ( playImage );
        }
        isPaused = !isPaused;

    }

    @FXML private void dumbSoundCheckBoxAction () {
        if (dumbSoundCheckBox.isSelected ())
            isDumb = true;
        else
            isDumb = false;
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

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {
        if (isPaused) {
            playPauseImageView.setImage ( playImage );
        }
        else {
            playPauseImageView.setImage ( pauseImage );
        }
    }
}
