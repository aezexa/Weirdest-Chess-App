package main;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
        screen = new StackPane (  );
        chessControl = new ChessControl ();

        screen.setPrefSize ( 600, 700 );

        screen.getChildren ().addAll ( chessControl );
        App.currentStage.setScene (  new Scene ( screen , 900, 700 ) );
        App.currentStage.centerOnScreen ();

    }
}
