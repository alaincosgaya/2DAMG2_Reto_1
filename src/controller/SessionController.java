/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author idoia
 */
public class SessionController implements Initializable{
    
    @FXML
    private BorderPane bPane;
    
    @FXML
    private void buttonEventBack(ActionEvent event) throws Exception {

        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("SignIn");
            anotherStage.setScene(new Scene(anotherRoot, 600, 400));
            anotherStage.show();

            bPane.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @FXML
    private void buttonEventExit(ActionEvent event) throws Exception {

        Platform.exit();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}