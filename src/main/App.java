package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * JavaFX main.App
 */
public class App extends Application {

    public static Scene currentScene;
    public static Scene appScene;
    public static Stage currentStage;
    public static ArrayList<AudioClip> dumbClickSounds;
    public static AudioClip clickSound;
    public static MediaPlayer backgroundSong;
    public static boolean isPaused;
    public static AudioClip chessMove;
    public static AudioClip chessHit;
    public static boolean inGame;
    public static boolean isDumb;
    public static Image playImage = new Image ( "/resources/play.png" );
    public static Image pauseImage = new Image ( "/resources/pause.png" );

    @Override
    public void start(Stage stage) throws IOException {

//        startChess ( stage );
        startGame ( stage );

    }

    static void setRoot(String fxml) {
        try {
            currentScene.setRoot ( getFXMLLoader ( fxml ).load () );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public static FXMLLoader getFXMLLoader ( String fxml) {
        return new FXMLLoader(App.class.getResource("/resources/fxml/" + fxml + ".fxml"));
    }

    public static void error (String message) {
        Alert alert = new Alert ( Alert.AlertType.ERROR );
        alert.setContentText ( message );
        alert.showAndWait ();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void initializeSounds () {
        dumbClickSounds = new ArrayList <> (  );
        dumbClickSounds.add ( new AudioClip ( new File ( "src/resources/click.mp3" ).toURI ().toString () ) );
        dumbClickSounds.add ( new AudioClip ( new File ( "src/resources/taq.mp3" ).toURI ().toString () ) );
        dumbClickSounds.add ( new AudioClip ( new File ( "src/resources/akh.mp3" ).toURI ().toString () ) );
        dumbClickSounds.add ( new AudioClip ( new File ( "src/resources/loq.mp3" ).toURI ().toString () ) );
        clickSound = new AudioClip ( new File ( "src/resources/mouseClick.mp3" ).toURI ().toString () );
        chessHit = new AudioClip ( new File ( "src/resources/chessHit.mp3" ).toURI ().toString () );
        chessMove = new AudioClip ( new File ( "src/resources/chessMove.mp3" ).toURI ().toString () );
        backgroundSong = new MediaPlayer ( new Media ( App.class.getResource("/resources/backgroundSong.mp3").toExternalForm() ) );
        backgroundSong.setCycleCount ( AudioClip.INDEFINITE );
        backgroundSong.setVolume ( 0.1 );
        backgroundSong.play ();
    }

    public static void sound ( ) {
        if (isDumb) {
            Random rand = new Random ( );
            dumbClickSounds.get ( rand.nextInt ( dumbClickSounds.size ( ) ) ).play ( );
        } else {
            clickSound.play ();
        }
    }

    private static void startGame (Stage stage) throws IOException {
        initializeSounds ();
        App.currentStage = stage ;
        BorderPane root = getFXMLLoader ("mainScreen").load ();
        currentScene = new Scene ( root, 600, 400 );
        currentScene.addEventFilter( MouseEvent.MOUSE_PRESSED, mouseEvent -> sound ( ) );
        stage.setTitle ( "Weird Chess" );
        stage.setScene( currentScene );
        stage.setResizable ( false ); //felan
        stage.show();
    }

    private static void startChess (Stage stage) throws IOException {
        currentStage = stage;
        stage.setResizable ( false );
        BorderPane root = getFXMLLoader ( "chessScreen" ).load ();
        stage.show ();
    }

}