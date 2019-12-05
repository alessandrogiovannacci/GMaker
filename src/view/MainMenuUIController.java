package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.GMaker;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class MainMenuUIController implements Initializable {
    
    @FXML
    private void handlePowersetButton(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/InputManagerUI.fxml"));
        root.setStyle("-fx-background-color: #6b6767;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(GMaker.getVersion());
        stage.show();
    }
    
    @FXML
    private void handleDownsetButton(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/DownsetInputManagerUI.fxml"));
        root.setStyle("-fx-background-color: #6b6767;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(GMaker.getVersion());
        stage.show();
    }
    
    @FXML
    private void handleJoinIrreducibleButton(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/JoinIrreducibleElementsInputManagerUI.fxml")); //edit string to test window
        root.setStyle("-fx-background-color: #6b6767;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(GMaker.getVersion());
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
