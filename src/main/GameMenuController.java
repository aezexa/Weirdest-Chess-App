package main;

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

import static main.App.error;
import static main.App.getFXMLLoader;

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
        Parent root = getFXMLLoader ( "newGameInputScreen" ).load ( );
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

            User.setBlackUser ( User.getUserWithName ( name ) );

            ChessBoard chessBoard = new ChessBoard ();
            try {
                chessBoard.start ();
            } catch (IOException e) {
                error ( "Start Nshd" );
            }
            destroyButtonAction ();

//            GameMenu.goToGame ( whiteUser , blackUser , limit , scanner );
        }
    }

    private static boolean hasNewGameErrors ( String name , int limit ) {
        if ( !Pattern.compile ( "\\w+" ).matcher ( name ).matches ( ) ) {
            App.error ( "Username Format Is Invalid" );
            return true;
        } else if ( limit < 0 ) {
            App.error ( "Negative Limit Defies Laws Of Chess Physics.\n-Albert Honarvar" );
            return true;
        } else if ( name.equals ( User.getWhiteUser ( ).getName ( ) ) ) {
            App.error ( "Are You That Much Alone? (Don't worry I'm the same tbh.)" );
            return true;
        } else if ( User.getUserWithName ( name ) == null ) {
            App.error ( "This Person Isn't Real Yet." );
            return true;
        }
        return false;
    }

    @FXML
    private void scoreBoardButtonAction () throws IOException {
        Parent root = getFXMLLoader ( "scoreBoardScreen" ).load ();
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
