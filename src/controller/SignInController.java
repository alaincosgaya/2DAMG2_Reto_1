/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
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
    
    private boolean exception = false;

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
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(textUser)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(textPasswd)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
    }

    @FXML
    private void buttonEventSignIn(ActionEvent event) throws IOException, PasswordLengthException, PasswordNumException  {

        try {
            
            String passwd = textPasswd.getText();
            validarNumPasswdPattern(passwd);
            validarMinCaractPasswdPattern(passwd);
            exception = false;
            lblCaract.setVisible(false);
            lblNum.setVisible(false);
            
        if(exception == false){
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/Session.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("Session");
            anotherStage.setScene(new Scene(anotherRoot));
            anotherStage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        }

        } catch (IOException e1) {
            e1.printStackTrace();
            //LOGGER
        }  catch (PasswordLengthException e) {
            exception = true;
            lblCaract.setVisible(true);
        }  catch (PasswordNumException e) {
            exception = true;
            lblNum.setVisible(true);
        }
    }

    @FXML
    private void buttonEvent(ActionEvent event) throws IOException {
        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("SignUp");
            anotherStage.setScene(new Scene(anotherRoot));
            anotherStage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            //LOGGER
        }
    }

    private void passwdTextCaractValidation() {

        textPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!textPasswd.getText().equals("")) {
                if (!newV) {
                    try {
                        String passwd = textPasswd.getText();
                        validarMinCaractPasswdPattern(passwd);
                        lblCaract.setVisible(false);
                    } catch (PasswordLengthException e) {
                        lblCaract.setVisible(true);
                    }
                }
            } else {
                lblCaract.setVisible(false);
            }
        });

    }

    private void passwdTextNumValidation() {

        textPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!textPasswd.getText().equals("")) {
                if (!newV) {
                    try {
                        String passwd = textPasswd.getText();
                        validarNumPasswdPattern(passwd);
                        lblNum.setVisible(false);
                    } catch (PasswordNumException e) {
                        lblNum.setVisible(true);
                    }
                }
            } else {
                lblNum.setVisible(false);
            }
        });
    }

    private void reportedFields() {
        btnLogin.disableProperty().bind(
                    textUser.textProperty().isEmpty().or(
                            textPasswd.textProperty().isEmpty()));
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passwdTextCaractValidation();
        passwdTextNumValidation();
        reportedFields();
    }

    public void validarMinCaractPasswdPattern(String passwd) throws PasswordLengthException {
        String regex = "^(.+){8,50}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);

        if (!matcher.matches()) {
            throw new PasswordLengthException(lblCaract.getText());
        }

    }

    public void validarNumPasswdPattern(String passwd) throws PasswordNumException {

        String regex = ".*\\d.*";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);
        if (!matcher.matches()) {
            throw new PasswordNumException(lblNum.getText());
        }
    }

}
