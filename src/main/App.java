package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
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
    public static ArrayList<AudioClip> clickSounds;
    public static boolean inGame;

    @Override
    public void start(Stage stage) throws IOException {
        startChess ( stage );
    }


    static void setRoot(Parent root) {
        currentScene.setRoot( root );
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
        clickSounds = new ArrayList <> (  );
        clickSounds.add ( new AudioClip ( new File ( "src/resources/click.mp3" ).toURI ().toString () ) );
        clickSounds.add ( new AudioClip ( new File ( "src/resources/actual_click.mp3" ).toURI ().toString () ) );
        clickSounds.add ( new AudioClip ( new File ( "src/resources/taq.mp3" ).toURI ().toString () ) );
        clickSounds.add ( new AudioClip ( new File ( "src/resources/akh.mp3" ).toURI ().toString () ) );
        clickSounds.add ( new AudioClip ( new File ( "src/resources/loq.mp3" ).toURI ().toString () ) );
    }

    public static void sound ( ) {
        Random rand = new Random ();
        clickSounds.get(rand.nextInt(clickSounds.size())).play ();
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
//        root.prefHeightProperty().bind(currentScene.heightProperty());
//        root.prefWidthProperty().bind(currentScene.widthProperty());
        stage.show();
    }

    private static void startChess (Stage stage) throws IOException {
        currentStage = stage;
        BorderPane root = getFXMLLoader ( "chessScreen" ).load ();
        stage.show ();
    }

}