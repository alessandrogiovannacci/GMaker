package model;

import java.io.IOException;

/**
 *
 * @author Ale
 */
public class SetOfDisjointDownsetGraphGenerator extends GraphGenerator {
    
    public SetOfDisjointDownsetGraphGenerator(String[] set){
        super.setElements = set;
        GraphGenerator.PNG_FILE_NAME = "setofdisjointdownsetgraph.png";
        GraphGenerator.DOT_FILE_NAME = "setofdisjointdownsetgraph.dot";
    }

    @Override
    public void generateGraph() throws IOException {
        setElements[0] = "0";
        
        String[] res = new String[setElements.length*setElements.length];
        int k = 0;
        boolean disjoint;
        for(int i = 0; i < setElements.length; i++){
            for(int j = 0; j < setElements.length; j++){
                if(setElements[i].equals("0") && setElements[j].equals("0")){
                        res[k++] = setElements[i] + "/" + setElements[j];
                        continue;
                }
                disjoint = true;
                String[] t1 = setElements[i].split(",");
                String[] t2 = setElements[j].split(",");
                for(int w = 0; w < t1.length; w++){
                        for(int z = 0; z < t2.length; z++){
                                if(t1[w].equals(t2[z])){
                                        disjoint = false;
                                }
                        }
                }
                if(disjoint){
                    res[k++] = setElements[i] + "/" + setElements[j];
                }
            }
        }

        //create final set
        String[] disjointCartesianProductSet = new String[k];
        int i = 0;
        for(String x: res){
            if(x != null){
                    disjointCartesianProductSet[i++] = x;
            }
        }

        GraphVizManager.createSetOfDisjointDownsetGraphDotFile(disjointCartesianProductSet);
        GraphVizManager.launchDot(SetOfDisjointDownsetGraphGenerator.getDotFileName(), SetOfDisjointDownsetGraphGenerator.getPngFileName());
    }
    
}
