package main;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChessScreen implements Initializable {

    private StackPane screen;
    private ChessControl chessControl;

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {
//        App.currentStage.setMinWidth ( 300 );
//        App.currentStage.setMinHeight ( 300 );
        screen = new StackPane (  );
        chessControl = new ChessControl ();
        screen.getChildren ().add ( chessControl );

        screen.setPrefSize ( 600, 700 );

        App.currentStage.setScene (  new Scene ( screen , 600, 700 ) );
        App.currentStage.centerOnScreen ();

    }
}
