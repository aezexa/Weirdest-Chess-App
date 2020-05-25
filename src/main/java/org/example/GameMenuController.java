package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenuController {

    @FXML private Button newGameButton;
    @FXML private Button scoreBoardButton;
    @FXML private Button logoutButton;

    @FXML private void newGameButtonAction () {

    }
    @FXML private void scoreBoardButtonAction () throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource( "scoreBoardScreen.fxml" ));
        Stage window = new Stage ( );
        window.setTitle ( "Score Board" );
        window.setScene ( new Scene ( root ) );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.showAndWait ();
    }
    @FXML private void logoutButtonAction () {
        App.setRoot ( "mainScreen" );
    }

}
