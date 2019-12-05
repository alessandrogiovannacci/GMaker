package model;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ale
 */
public class RelationsGraphGenerator extends GraphGenerator{
    
    private final ArrayList<String[]> relations;
    
    public RelationsGraphGenerator(ArrayList<String[]> relations){
        this.relations = relations;
        GraphGenerator.PNG_FILE_NAME = "relationsgraph.png";
        GraphGenerator.DOT_FILE_NAME = "relationsgraph.dot";
    }
    
    @Override
    public void generateGraph() throws IOException{
        ArrayList<String> fileInput = new ArrayList<>();
        int i = 0;
        
        for(String[] x: relations){
            if(x[1].equals("no relation")){
                fileInput.add("{" + x[0] + "}");
            }
            else{
                fileInput.add("{" + x[0] + "}" + "--" + "{" + x[1] + "}");
            }
        }
        
        GraphVizManager.createRelationsGraphDotFile(fileInput);
        GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME);
    }
    
}
