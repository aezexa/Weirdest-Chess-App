package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import static main.App.getFXMLLoader;
import static main.App.inGame;

public class ChessBoard {

    Scene appScene;
    Stage appStage;

    void start () throws IOException {
        App.setRoot ( "chessBoard" );
        appScene = App.mainScene;
        inGame = true;
//        App.currentStage = new Stage ( getFXMLLoader("gameMenuScreen").load () );
        StackPane mainPane = getFXMLLoader ( "chessBoard" ).load ();
        App.currentStage.setScene ( new Scene ( mainPane, 600, 600 ) );
        // Load your Image
//        ImageView backgroundImageView = new ImageView (
//                new Image ("/resources/Black-board-4.jpg",600,600,false,false));
        // Initialize the grid
        GridPane boardGrid = initBoard();
        // Set the dimensions of the grid
        boardGrid.setPrefSize(BORDER_WIDTH, BORDER_HEIGHT);

        // Use a StackPane to display the Image and the Grid
        Button exitButton = new Button ( "Exit" );
        exitButton.setOnAction ( event -> {
            App.setRoot ( "gameMenuScreen" );
            App.currentStage.setScene ( appScene );
            inGame = false;
        } );
//        mainPane.getChildren().addAll(backgroundImageView, boardGrid,exitButton);
        mainPane.getChildren().addAll(boardGrid,exitButton);
//        Stage stage = null;
//        try {
//            stage = new Stage ( getFXMLLoader("gameMenuScreen").load () );
//        } catch (IOException e) {
//            e.printStackTrace ( );
//        }
//        assert stage != null;
//        stage.setScene(new Scene (mainPane, 600, 600));
//        stage.setResizable(false);
//        stage.show();
//        App.mainScene = new Scene (mainPane, 600, 600);
    }

    private GridPane initBoard() {
        GridPane boardGrid = new GridPane();

        int tileNum = 8;
        double tileWidth = (double) BORDER_WIDTH / tileNum;
        double tileHeight = (double) BORDER_HEIGHT / tileNum;

        for (int i = 0; i < tileNum; i++) {
            for (int j = 0; j < tileNum; j++) {
                Tile tile = new Tile ( i , j );
                // Set each 'Tile' the width and height
                tile.setPrefSize(tileWidth, tileHeight);
                // Add node on j column and i row
                boardGrid.add(tile, j, i);
            }
        }
        // Return the GridPane
        return boardGrid;
    }

    static class Tile extends Pane {
        private final int positionX;
        private final int positionY;

        public Tile(int x, int y) {
            positionX = x;
            positionY = y;
            setOnMouseClicked(e -> System.out.println(positionX + " " + positionY) );
        }
    }

    private static void initializeGame () {

    }

    private static void endGame () {

    }

    private final int BORDER_WIDTH = 600;
    private final int BORDER_HEIGHT = 600;
}
