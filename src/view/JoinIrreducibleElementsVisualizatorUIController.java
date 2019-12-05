package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.DownsetGraphGenerator;
import model.GraphGenerator;
import model.JoinIrreducibleElementsGraphGenerator;

public class JoinIrreducibleElementsVisualizatorUIController implements Initializable {
    
    @FXML
    private StackPane pane;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private Label joinIrreducibleElementsLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        joinIrreducibleElementsLabel.setText("(J(" + JoinIrreducibleElementsInputManagerUIController.getSetName() + "), ≤)");
        
        JoinIrreducibleElementsGraphGenerator generator = new JoinIrreducibleElementsGraphGenerator(DownsetGraphGenerator.getDownset());
        try {
            generator.generateGraph();
        } catch (IOException ex) {
            Logger.getLogger(JoinIrreducibleElementsVisualizatorUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //display png image
        imageView = new ImageView();
        File f = new File(GraphGenerator.getPngFileName());
        Image image = new Image(f.toURI().toString());
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);
    }    
    
}
