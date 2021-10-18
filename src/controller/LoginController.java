/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author idoia
 */
public class LoginController implements Initializable {

    @FXML
    private TextField textUser;
    
    @FXML
    private PasswordField textPasswd;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private void eventKey(KeyEvent event){
        Object evt = event.getSource();
        
        if(evt.equals(textUser)){
            if(event.getCharacter().equals(" ")){
                event.consume();
            }
        }
        
        if(evt.equals(textPasswd)){
            if(event.getCharacter().equals(" ")){
                event.consume();
            }
        }
    }
    
    @FXML
    private void buttonEvent(ActionEvent event) throws Exception {
        
        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/Session.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("Session");
            anotherStage.setScene(new Scene(anotherRoot, 600, 400));
            anotherStage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
            
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/Registro.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("Registro");
            anotherStage.setScene(new Scene(anotherRoot, 600, 400));
            anotherStage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
