package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoardScreen implements Initializable {

    @FXML private TableView < User > tableView;

    @FXML private TableColumn< User,Integer> rankColumn;
    @FXML private TableColumn< User,String> userColumn;
    @FXML private TableColumn< User,Integer> scoreColumn;
    @FXML private TableColumn< User,Integer> winsColumn;
    @FXML private TableColumn< User,Integer> drawsColumn;
    @FXML private TableColumn< User,Integer> lossesColumn;

    @FXML private Button closeButton;

    @FXML private void closeButtonAction () {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close ();
    }

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {
        User.allUsers.sort ( new User.scoreboardSorter () );
        for (int i = 0; i < User.allUsers.size ( ); i++) {
            User.allUsers.get ( i ).makeScoreboardCompatible ( i+1 );
        }
        final ObservableList < User > data = FXCollections.observableArrayList (
              User.allUsers
        );
        rankColumn.setCellValueFactory ( new PropertyValueFactory<> ( "rankProperty" ) );
        userColumn.setCellValueFactory ( new PropertyValueFactory <> ( "nameProperty" ) );
        scoreColumn.setCellValueFactory ( new PropertyValueFactory <> ( "scoreProperty" ) );
        winsColumn.setCellValueFactory ( new PropertyValueFactory<> ( "winsProperty" ) );
        drawsColumn.setCellValueFactory ( new PropertyValueFactory<> ( "drawsProperty" ) );
        lossesColumn.setCellValueFactory ( new PropertyValueFactory<> ( "lossesProperty" ) );

        tableView.setItems ( data );
    }
}
