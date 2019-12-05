package model;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ale
 */
public class JoinIrreducibleElementsGraphGenerator extends GraphGenerator {
    
    private Set<String> joinIrreducibleElementsSet = new HashSet<>();
    private String[] joinIrreducibleElementsArr; //final set
    
    public JoinIrreducibleElementsGraphGenerator(String[] downset){
        super.setElements = downset;
        GraphGenerator.PNG_FILE_NAME = "join_irreducible_elements_graph.png";
        GraphGenerator.DOT_FILE_NAME = "join_irreducible_elements_graph.dot";
    }

    @Override
    public void generateGraph() throws IOException {
        //add singleton
        for(String x: setElements){
            if(x.length() == 1 && !x.equals("0")){
                joinIrreducibleElementsSet.add(x);
            }
        }
        
        //add {x,y} to joinIrreducibleElementsSet iff it has only one lower cover
        byte lowerCovers;
        for(String x: setElements){
            if(x.length() == 3){
                lowerCovers = 0;
                for(String y: setElements){
                    if(y.length() == 1){
                        String[] t1 = x.split(",");
                        for(String z: t1){
                            if(z.equals(y)){
                                lowerCovers++;
                            }
                        }
                    }
                }
                if(lowerCovers == 1){
                    joinIrreducibleElementsSet.add(x);
                }
            }
        }
        
        //add [x,y,z} to joinIrreducibleElementsSet iff it has only one lower cover
        boolean commonElement;
        byte sameLowerElementsCounter;
        for(String x: setElements){
            if(x.length() == 5){
                lowerCovers = 0;
                for(String y: setElements){
                    if(y.length() == 3){
                        commonElement = false;
                        sameLowerElementsCounter = 0;
                        String[] t1 = x.split(",");
                        String[] t2 = y.split(",");
                        for(String z: t1){
                            for(String w: t2){
                                if(z.equals(w)){
                                    sameLowerElementsCounter++;
                                }
                            }
                            if(sameLowerElementsCounter == t2.length){
                                commonElement = true;
                                break;
                            }
                        }
                        if(commonElement){
                            lowerCovers++;
                        }
                    }
                }
                if(lowerCovers == 1){
                    joinIrreducibleElementsSet.add(x);
                }
            }
        }
        
        //add [x,y,z,w} to joinIrreducibleElementsSet iff it has only one lower cover
        for(String x: setElements){
            if(x.length() == 7){
                lowerCovers = 0;
                for(String y: setElements){
                    if(y.length() == 5){
                        commonElement = false;
                        String[] t1 = x.split(",");
                        String[] t2 = y.split(",");
                        for(String z: t1){
                            for(String w: t2){
                                if(z.equals(w)){
                                    commonElement = true;
                                    break;
                                }
                            }
                            if(commonElement){
                                break;
                            }
                        }
                        if(commonElement){
                            lowerCovers++;
                        }
                    }
                }
                if(lowerCovers == 1){
                    joinIrreducibleElementsSet.add(x);
                }
            }
        }
        
        //convert HashSet into String[]
	joinIrreducibleElementsArr = new String[joinIrreducibleElementsSet.size()];
	int i = 0;
	for(String x: joinIrreducibleElementsSet){
            joinIrreducibleElementsArr[i++] = x;
	}
	Arrays.sort(joinIrreducibleElementsArr, new java.util.Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
        		return s1.length() - s2.length();
            }
	});
        
        //create dot file
        GraphVizManager.createJoinIrreducibleElementsGraphDotFile(joinIrreducibleElementsArr);
        GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME);
        
    }
    
}
