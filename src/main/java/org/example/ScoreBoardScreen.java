package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ScoreBoardScreen {

    @FXML private Button closeButton;

    @FXML private void closeButtonAction () {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close ();
    }
}
