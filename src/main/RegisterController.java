package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static main.App.*;

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

    private Stage registrationCompletedStage;

    ScreensController myController;

    static MediaPlayer mediaPlayer;

    ImageView imageView = new ImageView ( new Image ( "/resources/registrationCompleted.jpg" ) );


    @FXML
    private void registerButtonAction () throws IOException {

        String name = usernameField.getText ();
        String password = passwordField.getText ();

//        Parent root = getFXMLLoader ( "registrationCompletedScreen" ).load ();
//        Stage window = new Stage ( );
//        window.setTitle ( "Registration Completed!!1" );
//        Scene scene = new Scene ( root );
//        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, mouseEvent -> sound () );
//        window.setScene ( scene );
//        window.initModality ( Modality.APPLICATION_MODAL );

        AnchorPane registrationCompleted = new AnchorPane (  );
        Text text = new Text ( "NICE BRO!\n" +
                "Registration Completed!!!11!" );
        text.setStyle ( "-fx-font-weight: bold" );
        text.setStroke ( Color.BLACK );
        text.setStrokeWidth ( 0.5 );
        BoxBlur bb = new BoxBlur (  );
        bb.setWidth ( 5 );
        bb.setHeight ( 5 );
        bb.setIterations ( 3 );
        imageView.setEffect ( bb );
        text.setFont ( Font.font ( "Comic Sans MS" , 24 ) );
        text.setFill ( Color.valueOf ( "#1cbd4c" ) );
        text.setLayoutX ( 84 );
        text.setLayoutY ( 209 );
        text.setTextAlignment ( TextAlignment.CENTER );
        Button button = new Button ( "Please Let Me Just Enter The App" );
        button.setTextAlignment ( TextAlignment.CENTER );
        button.setPrefWidth ( 225.6 );
        button.setPrefHeight ( 25.6 );
        button.setLayoutX ( 124 );
        button.setLayoutY ( 346 );
        button.setAlignment ( Pos.CENTER );
        registrationCompleted.getChildren ().addAll ( imageView , text, button );
        registrationCompletedStage = new Stage (  );
        registrationCompletedStage.setTitle ( "Congrats!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
        registrationCompletedStage.setScene ( new Scene ( registrationCompleted ) );
        registrationCompletedStage.initModality ( Modality.APPLICATION_MODAL );

        button.setOnAction ( event -> destroyButtonAction () );

        registrationCompletedStage.setOnCloseRequest ( windowEvent -> mediaPlayer.stop () );


        if ( hasNoFormatErrors ( name , password ) &&
                !hasRegisterErrors ( name ) ) {

            User.allUsers.add ( new User ( name , password ) );
            mediaPlayer = new MediaPlayer ( new Media ( new File ( "src/resources/congrats.mp3" ).toURI ().toString () ) );
            mediaPlayer.play ();
            backgroundSong.pause ();
            registrationCompletedStage.showAndWait ();
            App.setRoot ( "mainScreen" );
            if (!isPaused)
                backgroundSong.play ( );
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
//        Stage stage = (Stage) destroyButton.getScene().getWindow();
        mediaPlayer.stop ();
//        stage.close ();
        registrationCompletedStage.close ();
    }

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {

    }
}
