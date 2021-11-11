package controller;

import classes.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * El controlador de la ventana de Session la cual controla las excepciones y
 * las acciones de los botones y los textos.
 * 
 * @author Idoia Ormaetxea y Alain Cosgaya
 */
public class SessionController{
    
    private static final Logger LOGGER = Logger.getLogger(SessionController.class.getName());

    @FXML
    private BorderPane bPane;
    
    @FXML
    private Label lblNomUsu;
    
    @FXML
    private Label lblUsername;
    
    @FXML
    private Label lblEmail;
    
    private Stage stage;
    
    /**
     * El metodo que indica el stage.
     * @param stage1 
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }
    /**
     * El metodo que rellena los datos de los campos de texto.
     * @param user Los datos del usuario
     */
    public void initData(User user) {
        String txtName;
        txtName = user.getFullName();
        LOGGER.info("Comprobacion de si hay espacios en el atributo FullName");
        if(txtName.contains(" ")){
            LOGGER.info("Hay espacios en el atributo FullName");
            txtName = txtName.substring(0,user.getFullName().indexOf(" "));
        }
        lblNomUsu.setText(txtName);
        lblUsername.setText(user.getUsername());
        lblEmail.setText(user.getEmail());
    }
    /**
     * El metodo que instancia la ventana.
     * @param root 
     */
    public void initStage(Parent root) {
        Stage anotherStage = new Stage();
        anotherStage.setResizable(false);
        anotherStage.getIcons().add(
                new Image("/photos/descargas-removebg-preview.png")
        );
        anotherStage.setTitle("Session");
        anotherStage.setScene(new Scene(root));
        anotherStage.show();
    }
    /**
     * El metodo que indica la acción al botón y crea la ventana a la que se
     * dirige.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void buttonEventBack(ActionEvent event) throws IOException {

        try {
            LOGGER.info("Cierre de sesion del usuario");
            LOGGER.info("Carga del FXML de SignIn");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/SignIn.fxml")
            );
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SignInController controller = ((SignInController) 
                    loader.getController()
                    );
            controller.setStage(stage);
            controller.initStage(root);
            
            bPane.getScene().getWindow().hide();

        } catch (IOException e) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    /**
     * El metodo que indica la acción al botón y sale de la aplicación.
     * @param event 
     */
    @FXML
    private void buttonEventExit(ActionEvent event) {
        LOGGER.info("Cierre del programa");
        Platform.exit();
    }
}
