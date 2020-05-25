package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

import static org.example.App.error;
import static org.example.User.*;

public class GameMenuController {

    @FXML
    private Button newGameButton;
    @FXML
    private Button destroyButton;
    @FXML
    private Button scoreBoardButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TextField blackUserField;
    @FXML
    private TextField limitField;

    @FXML
    private void newGameButtonAction () throws IOException {
        Parent root = FXMLLoader.load ( App.class.getResource ( "newGameInputScreen.fxml" ) );
        Stage window = new Stage ( );
        window.setTitle ( "Score Board" );
        window.setScene ( new Scene ( root ) );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.showAndWait ( );
    }

    @FXML
    private void newGameScreenAcceptAction () {
        String name = blackUserField.getText ( );
        int limit = Integer.parseInt ( limitField.getText ( ) );
        if ( !hasNewGameErrors ( name , limit ) ) {

            setBlackUser ( getUserWithName ( name ) );


            System.out.println ( "new game started successfully between " +
                    getWhiteUser ( ).getName ( ) +
                    " and " +
                    getBlackUser ( ).getName ( ) +
                    " with limit " +
                    limit ); // 0 means no limit

//            GameMenu.goToGame ( whiteUser , blackUser , limit , scanner );
        }
    }

    private static boolean hasNewGameErrors ( String name , int limit ) {
        if ( !Pattern.compile ( "\\w+" ).matcher ( name ).matches ( ) ) {
            error ( "Username Format Is Invalid" );
            return true;
        } else if ( limit < 0 ) {
            error ( "Negative Limit Defies Laws Of Chess Physics. -Albert Honarvar" );
            return true;
        } else if ( name.equals ( getWhiteUser ( ).getName ( ) ) ) {
            error ( "Are You That Much Alone? (Don't worry I'm the same tbh.)" );
            return true;
        } else if ( getUserWithName ( name ) == null ) {
            error ( "This Person Isn't Real Yet." );
            return true;
        }
        return false;
    }

    @FXML
    private void scoreBoardButtonAction () throws IOException {
        Parent root = FXMLLoader.load ( App.class.getResource ( "scoreBoardScreen.fxml" ) );
        Stage window = new Stage ( );
        window.setTitle ( "Score Board" );
        window.setScene ( new Scene ( root ) );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.showAndWait ( );
    }

    @FXML
    private void destroyButtonAction () {
        Stage stage = (Stage) destroyButton.getScene ( ).getWindow ( );
        stage.close ( );
    }

    @FXML
    private void logoutButtonAction () {
        App.setRoot ( "mainScreen" );
    }

}
