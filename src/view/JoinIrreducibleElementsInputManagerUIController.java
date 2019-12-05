package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.GMaker;
import model.DownsetGraphGenerator;

/**
 * FXML Controller class
 *
 * @author Ale
 */
public class JoinIrreducibleElementsInputManagerUIController implements Initializable {
    
    @FXML
    private StackPane pane;
    
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox orderRelationComboBox;
    
    @FXML
    private ComboBox elementsComboBox;
    
    @FXML
    private TextField setNameTextField;
    
    @FXML
    private TextField setElementsTextField;
    
    @FXML
    private ComboBox elements2ComboBox;
    
    @FXML
    private Label messagesLabel;
    
    @FXML
    private Label relationsAddedLabel;
    
    @FXML
    private Label relationsAddedDisplayerLabel;
    
    @FXML
    private Button generateJoinIrreducibleElementsGraphButton;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private ScrollPane scrollPane;
    
    private static String setName;
    private String[] setElements;
    private ArrayList<String[]> relations = new ArrayList<>();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrollPane.setStyle("-fx-background: rgb(80,80,80);");
        updateOrderRelationComboBox();
        relationsAddedDisplayerLabel.setText("");
        
        setElementsTextField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                updateElementsComboBox();
            }
        });
        
        orderRelationComboBox.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                updateElements2ComboBox(newValue);
            }
        });
        
    }    
    
    //--------------------------------------------------------------------------------------------------------------------------
    
    private void updateOrderRelationComboBox(){
        List<String> list = new ArrayList<>();
        list.add("no relation");
        list.add("≤");
        ObservableList obList = FXCollections.observableList(list);
        orderRelationComboBox.getItems().clear();
        orderRelationComboBox.setItems(obList);
    }
    
    //--------------------------------------------------------------------------------------------------------------------------
    
    private void updateElementsComboBox(){
        if(!checkInput(setElementsTextField.getText())){
            messagesLabel.setText("Invalid input. Choose a number n ∈ N | n > 0");
        }
        else{
            messagesLabel.setText("");
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(setElements));
            ObservableList obList = FXCollections.observableList(list);
            elementsComboBox.getItems().clear();
            elementsComboBox.setItems(obList);
            elements2ComboBox.getItems().clear();
            elements2ComboBox.setItems(obList);
        }
    }
    
    //--------------------------------------------------------------------------------------------------------------------------
    
    private void updateElements2ComboBox(String newValue){
        switch(newValue){
            case("≤"):{
                elements2ComboBox.setVisible(true);
            }
            break;
            
            case("no relation"):{
                elements2ComboBox.setVisible(false);
            }
            break;
        }
    }
    
    //--------------------------------------------------------------------------------------------------------------------------
    
    @FXML
    private void handleAddRelationButtonAction(ActionEvent event) throws IOException{
        messagesLabel.setText("");
        if(orderRelationComboBox.getValue().equals("no relation")){
            messagesLabel.setTextFill(Color.valueOf("#2bd90d"));
            messagesLabel.setText("Element \"" + elementsComboBox.getValue() + "\" has no order relation. Add a relation or proceed with graph generation");
            relationsAddedDisplayerLabel.setText(relationsAddedDisplayerLabel.getText() + elementsComboBox.getValue() + ": no relation" + "\n");
            String[] noRel = new String[2];
            noRel[0] = (String) elementsComboBox.getValue();
            noRel[1] = (String) orderRelationComboBox.getValue();
            relations.add(noRel);
            return;
        }
        if(!elementsComboBox.getSelectionModel().isEmpty() && !elements2ComboBox.getSelectionModel().isEmpty()){
            if(elementsComboBox.getValue().equals(elements2ComboBox.getValue())){
                messagesLabel.setTextFill(Color.valueOf("#ffffff"));
                messagesLabel.setText("Implicit relation: \"" + elementsComboBox.getValue() + "\" is <= of itself");
            }
            else{
                messagesLabel.setTextFill(Color.valueOf("#2bd90d"));
                messagesLabel.setText("Relation " + "(" + elementsComboBox.getValue() + " , " + elements2ComboBox.getValue() + ")" + " successfully added! Add another relation or proceed with graph generation");
                relationsAddedDisplayerLabel.setText(relationsAddedDisplayerLabel.getText() + "(" + elementsComboBox.getValue() + " , " + elements2ComboBox.getValue() + ")" + "\n");
                String[] rel = new String[2];
                rel[0] = (String) elementsComboBox.getValue();
                rel[1] = (String) elements2ComboBox.getValue();
                relations.add(rel);
            }
        }
    }
    
    @FXML
    private void handleGenerateGraphButtonAction(ActionEvent event) throws IOException{
        if(!relations.isEmpty()){
            setName = setNameTextField.getText();
            messagesLabel.setText("");
            //todo
            //relationGraphLabel.setText("(" + setName + ", ≤)");

            //relation graph generation
            DownsetGraphGenerator generator = new DownsetGraphGenerator(setElements, relations);
            generator.generateGraph();

            //display png image
            imageView = new ImageView();
            File f = new File("downsetgraph.png");
            Image image = new Image(f.toURI().toString());
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            pane.getChildren().add(imageView);
            StackPane.setAlignment(imageView, Pos.CENTER);

            generateJoinIrreducibleElementsGraphButton.setVisible(true);
        }
    }
    
    @FXML
    private void handleGenerateJoinIrreducibleElementsGraphButtonAction(ActionEvent event) throws IOException{
        resetButton.setVisible(true);
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/JoinIrreducibleElementsVisualizatorUI.fxml")); //edit string to test window
        root.setStyle("-fx-background-color: #6b6767;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(GMaker.getVersion());
        stage.show();
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
    
    public static String getSetName(){
        return setName;
    }
    
    @FXML
    private void handleResetButtonAction() throws IOException{
        imageView.setImage(null);
        setNameTextField.setText("");
        setElementsTextField.setText("");
        relationsAddedDisplayerLabel.setText("");
        elementsComboBox.getItems().removeAll(elementsComboBox.getItems());
        elements2ComboBox.getItems().removeAll(elements2ComboBox.getItems());
        orderRelationComboBox.valueProperty().set("");
        messagesLabel.setText("");
        generateJoinIrreducibleElementsGraphButton.setVisible(false);
        relations = new ArrayList<>();
        resetButton.setVisible(false);
    }
    
}
