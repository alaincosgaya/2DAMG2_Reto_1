package application;

import controller.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Is the main class
 * @author
 */
public class App extends Application{

     @Override
    public void start(Stage stage) throws Exception {
         
        Parent root = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
        stage.setTitle("SignIn");
        stage.setScene(scene);
        stage.show();
        
    }
    
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }  
}