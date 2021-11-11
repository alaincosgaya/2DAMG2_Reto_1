package controller;

import classes.User;
import exceptions.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static logic.UiLogicFactory.getUiImplem;

/**
 * El controlador de la ventana de SignUp la cual controla las excepciones y las
 * acciones de los botones y los textos.
 *
 * @author Idoia Ormaetxea y Alain Cosgaya
 */
public class SignUpController {

    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getName());
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
    private Button btnAtras;

    @FXML
    private BorderPane paneSignUp;

    private final int max = 50;

    private Stage stage;

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    /**
     * El metodo que instancia la ventana.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Stage anotherStage = new Stage();
        anotherStage.setResizable(false);
        anotherStage.getIcons().add(new Image(
                "/photos/descargas-removebg-preview.png")
        );
        anotherStage.setTitle("SignUp");
        anotherStage.setScene(new Scene(root));
        LOGGER.info("Llamada a metodos y restricciones del controlador");
        txtEmail.textProperty().addListener(this::emailTextValidation);
        txtPasswd.textProperty().addListener(this::passwdTextCaractValidation);
        txtPasswd.textProperty().addListener(this::passwdTextNumValidation);
        txtPassw2.textProperty().addListener(this::repeatpasswd);
        reportedFields();
        anotherStage.show();
    }

    /**
     * El metodo que controla que no se introduzcan espacios en los campos de
     * texto.
     *
     * @param event
     */
    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtUser) || evt.equals(txtPasswd) || evt.equals(txtPassw2)
                || evt.equals(txtEmail)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(txtName)) {
            if (txtName.getText().length() >= max) {
                event.consume();
            }
        }
        if (evt.equals(txtUser)) {
            if (txtUser.getText().length() >= max) {
                event.consume();
            }
        }
        if (evt.equals(txtPasswd)) {
            if (txtPasswd.getText().length() >= max) {
                event.consume();
            }
        }
        if (evt.equals(txtPassw2)) {
            if (txtPassw2.getText().length() >= max) {
                event.consume();
            }
        }
        if (evt.equals(txtEmail)) {
            if (txtEmail.getText().length() >= max) {
                event.consume();
            }
        }

    }

    /**
     * El metodo que indica las acciones del botón y crea la ventana a la que se
     * dirige, llevandole los datos.
     *
     * @param event
     */
    @FXML
    private void buttonEvent(ActionEvent event) {

        try {
            LOGGER.info("Inicializacion de la variable user");
            User user = new User();
            user.setUsername(txtUser.getText());
            user.setFullName(txtName.getText());
            user.setEmail(txtEmail.getText());
            user.setPassword(txtPasswd.getText());
            LOGGER.info("Ejecucion del metodo signUp de la implementacion");
            user = getUiImplem().signUp(user);
            LOGGER.info("Registro de usuario exitoso");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario registrado correctamente");
            alert.show();
            LOGGER.info("Carga del FXML de Session");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/Session.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SessionController controller = ((SessionController) loader.getController());
            controller.setStage(stage);
            controller.initData(user);
            controller.initStage(root);

            paneSignUp.getScene().getWindow().hide();

        } catch (IOException e) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, e);
        } catch (ConnectException | SignUpException | UpdateException | ServerFullException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * El metodo que indica las acciones del botón y crea la ventana a la que se
     * dirige.
     *
     * @param event
     */
    @FXML
    private void buttonEventBack(ActionEvent event) {

        try {
            LOGGER.info("Carga del FXML de SignIn");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/SignIn.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SignInController controller = ((SignInController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

            paneSignUp.getScene().getWindow().hide();

        } catch (IOException e) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * El metodo que valida si el formato de correo es correcto.
     *
     * @param ov valor observable
     * @param oldV valor antiguo
     * @param newV valor nuevo
     */
    private void emailTextValidation(ObservableValue ov, String oldV,
            String newV) {

        if (!txtEmail.getText().equals("")) {
            try {
                String email = txtEmail.getText();
                validarEmailPattern(email);
                lblEmail.setVisible(false);
            } catch (EmailPatternException e) {
                lblEmail.setVisible(true);
            }
        } else {
            lblEmail.setVisible(false);
        }
    }

    /**
     * El metodo que valida si se han introducido menos de 8 caracteres.
     *
     * @param ov valor observable
     * @param oldV valor antiguo
     * @param newV valor nuevo
     */
    private void passwdTextCaractValidation(ObservableValue ov, String oldV,
            String newV) {

        if (!txtPasswd.getText().equals("")) {

            try {
                String passwd = txtPasswd.getText();
                validarMinCaractPasswdPattern(passwd);
                lblCaract.setVisible(false);
            } catch (PasswordLengthException e) {
                lblCaract.setVisible(true);
            }

        }else{
            lblCaract.setVisible(false);
        }

    }

    /**
     * El metodo que valida si se han introducido números.
     *
     * @param ov valor observable
     * @param oldV valor antiguo
     * @param newV valor nuevo
     */
    private void passwdTextNumValidation(ObservableValue ov, String oldV,
            String newV) {

        if (!txtPasswd.getText().equals("")) {
            try {
                String passwd = txtPasswd.getText();
                validarNumPasswdPattern(passwd);
                lblNum.setVisible(false);
            } catch (PasswordNumException e) {
                lblNum.setVisible(true);
            }
        }else{
            lblNum.setVisible(false);
        }
    }

    /**
     * El metodo que valida si las contraseñas coinciden entre ellas.
     *
     * @param ov valor observable
     * @param oldV valor antiguo
     * @param newV valor nuevo
     */
    private void repeatpasswd(ObservableValue ov, String oldV, String newV) {

        if (!txtPassw2.getText().equals("")) {
            try {
                String passwd = txtPassw2.getText();
                validarEqualPasswd();
                lblPasswd2.setVisible(false);
            } catch (SamePasswordException e) {
                lblPasswd2.setVisible(true);
            }
        }else{
            lblPasswd2.setVisible(false);
        }
    }

    /**
     * El metodo que controla si los campos están informados.
     */
    private void reportedFields() {
        btnSignUp.disableProperty().bind(
                txtPasswd.textProperty().isEmpty().or(
                        txtEmail.textProperty().isEmpty().or(
                                txtPassw2.textProperty().isEmpty().or(
                                        txtUser.textProperty().isEmpty().or(
                                                txtName.textProperty().isEmpty().or(
                                                        lblCaract.visibleProperty().or(
                                                                lblEmail.visibleProperty().or(
                                                                        lblNum.visibleProperty().or(
                                                                                lblPasswd2.visibleProperty()
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    /**
     * El metodo que valida que el patron del correo es correcto.
     *
     * @param email recoge el valor del correo.
     * @throws EmailPatternException
     */
    public void validarEmailPattern(String email) throws EmailPatternException {

        String regex = "^(.+)@(.+)[.](.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new EmailPatternException(lblEmail.getText());
        }

    }

    /**
     * El metodo que controla los caracteres mínimos de la contraseña.
     *
     * @param passwd recoge el valor de la contraseña.
     * @throws PasswordLengthException
     */
    public void validarMinCaractPasswdPattern(String passwd)
            throws PasswordLengthException {
        String regex = "^(.+){8,50}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);

        if (!matcher.matches()) {
            throw new PasswordLengthException(lblCaract.getText());
        }

    }

    /**
     * El metodo que controla si la contraseña contiene números.
     *
     * @param passwd recoge el valor de la contraseña.
     * @throws PasswordNumException
     */
    public void validarNumPasswdPattern(String passwd)
            throws PasswordNumException {

        String regex = ".*\\d.*";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);
        if (!matcher.matches()) {
            throw new PasswordNumException(lblNum.getText());
        }
    }

    /**
     * El metodo que compara que las dos contraseñas son iguales.
     *
     * @throws SamePasswordException
     */
    public void validarEqualPasswd() throws SamePasswordException {
        if (!txtPasswd.getText().equals(txtPassw2.getText())) {
            throw new SamePasswordException(lblPasswd2.getText());
        }
    }
}
