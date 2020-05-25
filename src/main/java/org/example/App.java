package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX org.example.App
 */
public class App extends Application {

    public static Scene mainScene;
    public static Stage currentStage;


    @Override
    public void start(Stage stage) {
        App.currentStage = stage ;
        BorderPane root = (BorderPane) loadFXML("mainScreen");
        assert root != null;
        mainScene = new Scene ( root, 600, 400 );
        stage.setTitle ( "Weird Chess" );
        stage.setScene( mainScene );
        stage.show();
    }

    static void setRoot(String fxml) {
        mainScene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace ( );
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }


}