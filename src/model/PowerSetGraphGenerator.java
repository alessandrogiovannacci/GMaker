package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ale
 */
public class PowerSetGraphGenerator extends GraphGenerator{
    
    public PowerSetGraphGenerator(String[] set){
        super.setElements = set;
        GraphGenerator.PNG_FILE_NAME = "powersetgraph.png";
        GraphGenerator.DOT_FILE_NAME = "powersetgraph.dot";
    }
    
    //------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void generateGraph() throws IOException{
        //creating file
        String output = DOT_FILE_NAME;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("graph mygraph{ rankdir=BT\n");
            
            //enumerate subset
            for(int i = 0, max = 1 << setElements.length; i < max; i++){
                List newSet = new ArrayList();
                for (int j = 0; j < setElements.length; j++) {
                    // check if the j bit is set to 1
                    int isSet = (int) i & (1 << j);
                    if (isSet > 0) {
                        newSet.add(setElements[j]);
                    }
                }
                // For the new subset, print links to all supersets
                if (newSet.size() != setElements.length) {
                    printLinksToImmediateSupersets(newSet, setElements, writer);
                }
            }
            
            //write end of file
            writer.write("}");
        }
        
        //launch GraphViz
        GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME);
    }
    
    //------------------------------------------------------------------------------------------------------------------------
    
    private void printLinksToImmediateSupersets(List subset, String[] set, BufferedWriter writer) throws IOException{
        for (String value : set) {
            if (subset.contains(value) == false) {
                List newSet = new ArrayList();
                newSet.addAll(subset);
                newSet.add(value);
                writer.write(asString(subset) + " -- " + asString(newSet) + " \n");
            }
        }
    }
    
    //------------------------------------------------------------------------------------------------------------------------
    
    private String asString(List set){
        Collections.sort(set);
	if (set.isEmpty()) {
            return "\"{}\"";
	}
	StringBuilder buffer = new StringBuilder();
	buffer.append("\"{");
	for (int i = 0; i < set.size(); i++) {
            String value = (String) set.get(i);
            buffer.append(value);
            if (i != set.size() - 1) {
		buffer.append(",");
            }
	}
	buffer.append("}\"");
	return buffer.toString();
    }
}
