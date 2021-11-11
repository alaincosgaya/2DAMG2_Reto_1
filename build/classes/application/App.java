package application;

import controller.*;
import java.util.logging.Logger;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Es la clase principal.
 * @author Idoia Ormaetxea y Alain Cosgaya
 */
public class App extends Application{
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    
    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info("Carga del FXML de SignIn");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));     
        Parent root = (Parent) loader.load();
        LOGGER.info("Llamada al controlador del FXML");
        SignInController controller = ((SignInController) loader.getController());
        controller.setStage(stage);
        controller.initStage(root);
        
    }
    
    
    public static void main(String[] args){
        launch(args);
    }  
}
