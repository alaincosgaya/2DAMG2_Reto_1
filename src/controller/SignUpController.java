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
public class SignUpController implements Initializable {

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
    private TextField txtUser;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnSignUp;

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtUser) || evt.equals(txtPasswd) || evt.equals(txtPassw2) || evt.equals(txtEmail)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }

    }

    @FXML
    private void buttonEvent(ActionEvent event) throws IOException {

        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/Session.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("Session");
            anotherStage.setScene(new Scene(anotherRoot));
            anotherStage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void buttonEventBack(ActionEvent event) throws IOException {

        try {
            Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));
            Stage anotherStage = new Stage();
            anotherStage.setResizable(false);
            anotherStage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
            anotherStage.setTitle("SignIn");
            anotherStage.setScene(new Scene(anotherRoot));
            anotherStage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void emailTextValidation() {
        txtEmail.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!txtEmail.getText().equals("")) {
                if (!newV) {
                    try {
                        String email = txtEmail.getText();
                        validarEmailPattern(email);
                        lblEmail.setVisible(false);
                    } catch (EmailPatternException e) {
                        lblEmail.setVisible(true);
                    }
                }
            }
        });

    }

    private void passwdTextCaractValidation() {

        txtPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!txtPasswd.getText().equals("")) {
                if (!newV) {
                    try {
                        String passwd = txtPasswd.getText();
                        validarMinCaractPasswdPattern(passwd);
                        lblCaract.setVisible(false);
                    } catch (PasswordLengthException e) {
                        lblCaract.setVisible(true);
                    }
                }
            }
        });
    }

    private void passwdTextNumValidation() {

        txtPasswd.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!txtPasswd.getText().equals("")) {
                if (!newV) {
                    try {
                        String passwd = txtPasswd.getText();
                        validarNumPasswdPattern(passwd);
                        lblNum.setVisible(false);
                    } catch (PasswordNumException e) {
                        lblNum.setVisible(true);
                    }
                }
            }
        });

    }

    private void repeatpasswd() {

        txtPassw2.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!txtPassw2.getText().equals("")) {
                if (!newV) {
                    try {
                        String passwd = txtPassw2.getText();
                        validarEqualPasswd();
                        lblPasswd2.setVisible(false);
                    } catch (SamePasswordException e) {
                        lblPasswd2.setVisible(true);
                    }
                }
            }
        });
    }

    private void reportedFields() {
        btnSignUp.disableProperty().bind(
                txtPasswd.textProperty().isEmpty().or(txtEmail.textProperty().isEmpty().
                        or(txtPassw2.textProperty().isEmpty().or(txtUser.textProperty().isEmpty().or(txtName
                                .textProperty().isEmpty())))));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emailTextValidation();
        passwdTextCaractValidation();
        passwdTextNumValidation();
        repeatpasswd();
        reportedFields();

    }

    public void validarEmailPattern(String email) throws EmailPatternException {

        String regex = "^(.+)@(.+){3,50}$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new EmailPatternException(lblEmail.getText());
        }

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

    public void validarEqualPasswd() throws SamePasswordException {
        if (!txtPasswd.getText().equals(txtPassw2.getText())) {
            throw new SamePasswordException(lblPasswd2.getText());
        }
    }
}
