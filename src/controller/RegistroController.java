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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author idoia
 */
public class RegistroController implements Initializable {

    @FXML
    private Label lblCaract;

    @FXML
    private PasswordField txtPasswd;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblEmail;
    
    @FXML
    private Label lblNum;
    
    @FXML
    private PasswordField txtPassw2;
    
     @FXML
    private Label lblPasswd2;

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

            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void buttonEventBack(ActionEvent event) throws Exception {

        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/Login_1.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("Login");
            anotherStage.setScene(new Scene(anotherRoot, 600, 400));
            anotherStage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void emailTextValidation() {

        txtEmail.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                String email = txtEmail.getText();
                validarEmailPattern(email);
                if (validarEmailPattern(email) == true) {
                    lblEmail.setVisible(false);
                } else {
                    lblEmail.setVisible(true);
                }
            }
        });
    }

    private void passwdTextCaractValidation() {
        txtPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                String passwd = txtPasswd.getText();
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
        txtPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                String passwd = txtPasswd.getText();
                validarNumPasswdPattern(passwd);
                if (validarNumPasswdPattern(passwd) == true) {
                    lblNum.setVisible(false);
                } else {
                    lblNum.setVisible(true);
                }
            }
        });
    }
    
    private void repeatpasswd() {
    
        txtPassw2.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                String passwd = txtPassw2.getText();
                validarEqualPasswdPattern(passwd);
                if (validarNumPasswdPattern(passwd) == true) {
                    lblPasswd2.setVisible(false);
                } else {
                    lblPasswd2.setVisible(true);
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emailTextValidation();
        passwdTextCaractValidation();
        passwdTextNumValidation();
        repeatpasswd();

    }
    
    public static boolean validarEmailPattern(String email) {

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }

    public static boolean validarMinCaractPasswdPattern(String passwd) {
        String regex = "^(.+){8}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);
        System.out.println(matcher.matches());

        return matcher.matches();

    }

    public static boolean validarNumPasswdPattern(String passwd) {
        
        return passwd.matches(".*\\d.*");
    }

     public boolean validarEqualPasswdPattern(String passwd) {
        
     return txtPasswd.getText().equals(txtPassw2.getText());
     }

}
