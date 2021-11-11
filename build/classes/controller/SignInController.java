package controller;

import classes.User;
import exceptions.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static logic.UiLogicFactory.getUiImplem;

/**
 * El controlador de la ventana de SignIn la cual controla las excepciones y las
 * acciones de los botones y los textos.
 *
 * @author Idoia Ormaetxea y Alain Cosgaya
 */
public class SignInController {

    private static final Logger LOGGER = Logger.getLogger(SignInController.class.getName());

    private Stage stage;

    @FXML
    private TextField textUser;

    @FXML
    private PasswordField textPasswd;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkSignIn;

    @FXML
    private Label lblCaract;

    @FXML
    private Label lblNum;
    
    @FXML
    private Pane paneVentana;
    

    private final int max = 50;

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
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
        stage.setTitle("SignIn");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        textPasswd.textProperty().addListener(this::passwdTextCaractValidation);
        textPasswd.textProperty().addListener(this::passwdTextNumValidation);
        reportedFields();
        btnLogin.setOnAction(this::buttonEventSignIn);
        linkSignIn.setOnAction(this::buttonEvent);
        textPasswd.setOnKeyTyped(this::eventKey);
        textUser.setOnKeyTyped(this::eventKey);
        textPasswd.setOnKeyTyped(this::eventKey);
        stage.show();

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
        if (evt.equals(textUser) || evt.equals(textPasswd)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(textUser)) {
            if (textUser.getText().length() >= max) {
                event.consume();
            }
        }
        if (evt.equals(textPasswd)) {
            if (textPasswd.getText().length() >= max) {
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
    private void buttonEventSignIn(ActionEvent event) {

        try {
            LOGGER.info("Inicializacion de la variable user");
            User user = new User();
            user.setUsername(textUser.getText());
            user.setPassword(textPasswd.getText());
            LOGGER.info("Ejecucion del metodo signIn de la implementacion");
            user = getUiImplem().signIn(user);

            LOGGER.info("Carga del FXML de Session");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/Session.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SessionController controller = ((SessionController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
            controller.initData(user);
            paneVentana.getScene().getWindow().hide();

        } catch (IOException e1) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, e1);
        } catch (ConnectException | SignInException | UpdateException | ServerFullException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * El metodo que indica las acciones del botón y crea la ventana a la que se
     * dirige.
     *
     * @param event
     */
    @FXML
    private void buttonEvent(ActionEvent event) {
        try {
            LOGGER.info("Carga del FXML de SignUp");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/SignUp.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SignUpController controller = ((SignUpController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

            paneVentana.getScene().getWindow().hide();

        } catch (IOException e) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, e);
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
        if (!textPasswd.getText().equals("")) {
            try {
                String passwd = textPasswd.getText();
                validarMinCaractPasswdPattern(passwd);
                lblCaract.setVisible(false);
            } catch (PasswordLengthException e) {
                lblCaract.setVisible(true);
            }
        } else {
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
        if (!textPasswd.getText().equals("")) {
            try {
                String passwd = textPasswd.getText();
                validarNumPasswdPattern(passwd);
                lblNum.setVisible(false);
            } catch (PasswordNumException e) {
                lblNum.setVisible(true);
            }
        } else {
            lblNum.setVisible(false);
        }

    }

    /**
     * El metodo que controla si los campos están informados.
     */
    private void reportedFields() {
        btnLogin.disableProperty().bind(
                textUser.textProperty().isEmpty().or(
                        textPasswd.textProperty().isEmpty()).or(
                        lblCaract.visibleProperty().or(
                                lblNum.visibleProperty()
                        )
                )
        );
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
}
