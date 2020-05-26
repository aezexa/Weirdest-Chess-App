package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static main.App.getFXMLLoader;
import static main.App.sound;

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

    static MediaPlayer mediaPlayer;


    @FXML
    private void registerButtonAction () throws IOException {

        String name = usernameField.getText ();
        String password = passwordField.getText ();

        Parent root = getFXMLLoader ( "registrationCompletedScreen" ).load ();
        Stage window = new Stage ( );
        window.setTitle ( "Registeration Completed!!1" );
        Scene scene = new Scene ( root );
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, mouseEvent -> sound () );
        window.setScene ( scene );
        window.initModality ( Modality.APPLICATION_MODAL );

        if ( hasNoFormatErrors ( name , password ) &&
                !hasRegisterErrors ( name ) ) {

            User.allUsers.add ( new User ( name , password ) );
            mediaPlayer = new MediaPlayer ( new Media ( new File ( "src/resources/congrats.mp3" ).toURI ().toString () ) );
            mediaPlayer.play ();
            window.showAndWait ();
            App.setRoot ( "mainScreen" );
        }
    }

    public static boolean hasNoFormatErrors ( String name , String password ) {
        if ( !userPassFormatPattern.matcher ( name ).matches ( ) ) {
            App.error ( "Username Format Is Invalid" );
            return false;
        } else if ( !userPassFormatPattern.matcher ( password ).matches ( ) ) {
            App.error ( "Password Format Is Invalid" );
            return false;
        } else
            return true;
    }

    private static boolean hasRegisterErrors ( String name ) {
        if ( User.getUserWithName ( name ) != null ) {
            App.error ( "This Person Is Already Real." );
            return true;
        }
        return false;
    }

    @FXML
    private void cancelButtonAction () {
        App.setRoot ( "mainScreen" );
    }

    @FXML
    private void destroyButtonAction () {
        Stage stage = (Stage) destroyButton.getScene().getWindow();
        mediaPlayer.stop ();
        stage.close ();
    }

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {

    }
}
