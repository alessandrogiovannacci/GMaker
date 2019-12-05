package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.GraphGenerator;
import model.PowerSetGraphGenerator;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class InputManagerUIController implements Initializable {
    
    @FXML
    private StackPane pane;
    
    @FXML
    private Label elementsLabel; 
            
    @FXML
    private Label messagesLabel;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private Button generateGraphButton;
    
    @FXML
    private TextField setNameTextField;
    
    @FXML
    private TextField elementsTextField;
      
    @FXML
    private Label hasseDiagramLabel;
    
    @FXML
    private Button resetButton;
    
    private String[] setElements;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleGenerateGraphButtonAction() throws IOException{
        if(!checkInput(elementsTextField.getText())){
            messagesLabel.setText("Invalid input. Choose a number n ∈ N | n > 0");
        }
        else{
            if(setElements != null){
                messagesLabel.setText("");
                elementsLabel.setText(setNameTextField.getText() + " = {");
                for(int i = 0; i < setElements.length; i++){
                    if(i != setElements.length -1){
                        elementsLabel.setText(elementsLabel.getText() + setElements[i] + ",");
                    }
                    else{
                        elementsLabel.setText(elementsLabel.getText() + setElements[i] + "}");
                    }
                }

                hasseDiagramLabel.setText(hasseDiagramLabel.getText() + setNameTextField.getText() + ")");
                hasseDiagramLabel.setVisible(true);
                resetButton.setVisible(true);

                PowerSetGraphGenerator generator = new PowerSetGraphGenerator(setElements);
                generator.generateGraph();

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
        
    }
    
    private boolean checkInput(String input){
        int x = 0;
        try{
            x = Integer.valueOf(input);
            if(x < 1){
                return false;
            }
        }catch(NumberFormatException e){
            return false;
        }
        char element = 'a';
        setElements = new String[x];
        for(int i = 0; i < x; i++){
            setElements[i] = String.valueOf(element++);
        }
        
        return true;
    }
    
    @FXML
    private void handleResetButtonAction() throws IOException{
        hasseDiagramLabel.setVisible(false);
        hasseDiagramLabel.setText("P(");
        imageView.setImage(null);
        setNameTextField.setText("");
        elementsTextField.setText("");
        elementsLabel.setText("");
        resetButton.setVisible(false);
    }
    
}
