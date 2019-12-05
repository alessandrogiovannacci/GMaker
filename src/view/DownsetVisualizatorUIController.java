package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.GMaker;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class DownsetVisualizatorUIController implements Initializable {
    
    @FXML
    private StackPane pane;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private Label downsetLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        downsetLabel.setText("(O(" + DownsetInputManagerUIController.getSetName() + "), ≤)");
        //display png image
        imageView = new ImageView();
        File f = new File("downsetgraph.png");
        Image image = new Image(f.toURI().toString());
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);
        
    }

    @FXML
    private void handleSetOfDisjointDownsetsButtonAction() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/SetOfDisjointDownsetsVisualizatorUI.fxml")); //edit string to test window
        root.setStyle("-fx-background-color: #6b6767;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(GMaker.getVersion());
        stage.show();
    }
    
    @FXML
    private void handleSetOfJoinIrreducibleElementsButtonAction() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/JoinIrreducibleElementsSecondVisualizatorUI.fxml")); //edit string to test window
        root.setStyle("-fx-background-color: #6b6767;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(GMaker.getVersion());
        stage.show();
    }
    
}
