package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginCompletedController implements Initializable {

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {

    }

    public void setName ( String name ) {
        usernameLabel.setText ( "Welcome To The Game " + name + "!" );
    }
}
