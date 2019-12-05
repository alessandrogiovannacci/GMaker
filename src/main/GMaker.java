package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ale
 */
public class GMaker extends Application {
    
    //version
    private static final String VERSION = "GMaker 1.0";
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenuUI.fxml")); //edit string to test window
        root.setStyle("-fx-background-color: #6b6767;");
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(VERSION);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static String getVersion(){
        return VERSION;
    }
}
