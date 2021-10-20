/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author idoia
 */
public class SignInController implements Initializable {

    @FXML
    private TextField textUser;
    
    @FXML
    private PasswordField textPasswd;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private Label lblCaract;
    
    @FXML
    private Label lblNum;
    
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
    private void buttonEventSignIn(ActionEvent event) throws Exception {
        
        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/Session.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("Session");
            anotherStage.setScene(new Scene(anotherRoot, 640, 360));
            anotherStage.show();
            
            //((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
            
    @FXML
    private void buttonEvent(ActionEvent event) throws Exception {
        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("SignUp");
            anotherStage.setScene(new Scene(anotherRoot, 600, 400));
            anotherStage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void passwdTextCaractValidation() {
        textPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                String passwd = textPasswd.getText();
                validarMinCaractPasswdPattern(passwd);
                if (validarMinCaractPasswdPattern(passwd) == true) {
                    lblCaract.setVisible(false);
                } else {
                    lblCaract.setVisible(true);
                }
            }
        });
    }
    private void passwdTextNumValidation() {
        textPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                String passwd = textPasswd.getText();
                validarNumPasswdPattern(passwd);
                if (validarNumPasswdPattern(passwd) == true) {
                    lblNum.setVisible(false);
                } else {
                    lblNum.setVisible(true);
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passwdTextCaractValidation();
        passwdTextNumValidation();
    }
    
    public static boolean validarMinCaractPasswdPattern(String passwd) {
        String regex = "^(.+){8}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);

        return matcher.matches();

    }

    public static boolean validarNumPasswdPattern(String passwd) {
        
        return passwd.matches(".*\\d.*"); 
    }

}
