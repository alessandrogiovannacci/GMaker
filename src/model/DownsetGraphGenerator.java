package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ale
 */
public class DownsetGraphGenerator extends GraphGenerator {
    
    private final ArrayList<String[]> relations;
    
    //level 0 elements
    private ArrayList<String> level0elements = new ArrayList<>();

    //level 1 elements
    private ArrayList<String> level1elements = new ArrayList<>();

    //level 0 AND level 1 elements
    private ArrayList<String> level0AndLevel1elements = new ArrayList<>();

    //downset
    private Set<String> downset = new HashSet<>();
    private static String[] downsetArr; //final downset
    
    //------------------------------------------------------------------------------------------------------------
    
    public DownsetGraphGenerator(String[] set, ArrayList<String[]> relations){
        super.setElements = set;
        this.relations = relations;
        GraphGenerator.PNG_FILE_NAME = "downsetgraph.png";
        GraphGenerator.DOT_FILE_NAME = "downsetgraph.dot";
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    @Override
    public void generateGraph() throws IOException{
        downset.add("0");
        
        //adding elements to lvl0elements OR lvl1elements. element with no relation goes to lvl0elements
        relations.forEach((x) -> {
            if(x[1].equals("no relation")){
                if(!level0elements.contains(x[0])){ 
                    level0elements.add(x[0]);
                }
            }
            else{
                if(!level0elements.contains(x[0])){
                    level0elements.add(x[0]);
                }
                if(!level1elements.contains(x[1])){
                    level1elements.add(x[1]);
                }
            }
        });
        
        //remove same strings from level0elements and level1elements
        Set<String> temp0 = new HashSet<>();
        level0elements.forEach((x) -> {
            temp0.add(x);
        });
	level0elements = new ArrayList<>(temp0);

	Set<String> temp1 = new HashSet<>();
        level1elements.forEach((x) -> {
            temp1.add(x);
        });
	level1elements = new ArrayList<>(temp1);
           
        //*************DOWNSET GENERATION ALGORITHM**************************************
        
        //************STEP 1****************
        level0elements.forEach((x) -> {
            downset.add(x);
        });
        //eliminate elements that are both level 0 and level 1
        level0elements.stream().filter((x) -> (level1elements.contains(x))).map((x) -> {
            level0AndLevel1elements.add(x);
            return x;
        }).forEachOrdered((x) -> {
            downset.remove(x);
        });
        
        //************STEP 2****************
	for(int i = 0; i < level0elements.size(); i++){
            for(int j = 0; j < level0elements.size(); j++){
		if(!level0elements.get(i).equals(level0elements.get(j)))
                    if(!downset.contains(level0elements.get(j) + "," + level0elements.get(i))){ 
			downset.add(level0elements.get(i) + "," + level0elements.get(j));
                    }
            }
	}
        
        //when level0elements are 3, add (x,y,z) to downset, where x,y,z are level 0 elements
        if(level0elements.size() == 3){
           String t = "";
           for(String x: level0elements){
               t = t + x + ",";
           }
           t = t.substring(0, t.length()-1);
           downset.add(t);
        }
           
        //************STEP 3****************
        boolean sameUpperElement;
	//for each relation
        for(String[] x: relations){
            if(x[1].equals("no relation")){
                continue;
            }
            sameUpperElement = false;
            for(String[] y: relations){
                if(x != y){
                    if(x[1].equals(y[1])){
                        sameUpperElement = true;
                    }
                }
            }
            if(!level0AndLevel1elements.contains(x[0]) && !sameUpperElement){ // 
                downset.add(x[0] + "," + x[1]); //add the element iff the 1st is not in level 0 AND level 1
            }
        }
        
	//************STEP 4****************
        //****STEP 4.1****
        /*check if all relations have same upper element.
        If yes, skip step 4.2*/
        String upperElement = "";
        boolean allRelationsWithSameUpperElement = true;
        for(String[] rel: relations){
            if(!rel[1].equals("no relation")){
                upperElement = rel[1];
                break;
            }
        }
        for(String[] rel: relations){
            if(!rel[1].equals(upperElement)){
                allRelationsWithSameUpperElement = false;
            }
        }
        //****STEP 4.2****
        if(!allRelationsWithSameUpperElement){
            //for each relation, add (r1,r2,x) where x is a lvl 0 elem
            for(String[] rel: relations){
                if(!rel[1].equals("no relation")){
                    for(String x: level0elements){
                        /*check if there are two elements with the same upper element
                        If yes, an element with no relation doesn't have to be taken
                        in the ternary relation*/
                        sameUpperElement = false; 
                        for(String[] r: relations){
                            if(r[1].equals(rel[1])){
                                sameUpperElement = true;
                            }
                        } 
                        if(sameUpperElement){
                            boolean elementWithNoRelation = false;
                            for(String[] r2: relations){
                                if(r2[0].equals(x) && r2[1].equals("no relation")){
                                    elementWithNoRelation = true;
                                }
                            }
                            if(elementWithNoRelation){
                                /*check if there is only one relation and one or more single elements.
                                If yes, add (r1,r2,x), where r1 and r2 are elements of the relation, and x is/are single element/elements
                                */
                                int binaryRelations = 0;
                                for(String[] y: relations){
                                    if(!y[1].equals("no relation")){
                                        binaryRelations++;
                                    }
                                }
                                if(binaryRelations == 1){
                                    for(String[] relation: relations){
                                        if(!relation[1].equals("no relation")){
                                            for(String w: level0elements){
                                                if(!w.equals(relation[0])){
                                                    if(w.compareTo(rel[0]) <= 0){
                                                        downset.add(w + "," + rel[0] + "," + rel[1]);
                                                    }
                                                    else{
                                                        if(w.compareTo(rel[1]) <= 0){
                                                            downset.add(rel[0] + "," + w + "," + rel[1]);
                                                        }
                                                        else{
                                                            downset.add(rel[0] + "," + rel[1] + "," + w);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                continue;
                            }
                        }
                        /*end check*/
                        if(!x.equals(rel[0]) && !x.equals(rel[1])) {
                            if(x.compareTo(rel[0]) <= 0){
                                downset.add(x + "," + rel[0] + "," + rel[1]);
                            }
                            else{
                                if(x.compareTo(rel[1]) <= 0){
                                    downset.add(rel[0] + "," + x + "," + rel[1]);
                                }
                                else{
                                    downset.add(rel[0] + "," + rel[1] + "," + x);
                                }
                            }
                        }
                    }
                }
            }
        }
        //****STEP 4.3****
        /*check if all relations have same down element.
        If yes, add (d,x,y) where d is down element and x,y are all level1elements */
        String downElement = "";
        boolean allRelationsWithSameDownElement = true;
        boolean elementsWithNoRelation = false;
        for(String[] rel: relations){
                downElement = rel[0];
                break;
        }
        for(String[] rel: relations){
            if(rel[1].equals("no relation")){
                elementsWithNoRelation = true;
                continue;
            }
            if(!rel[0].equals(downElement)){
                allRelationsWithSameDownElement = false;
            }
        }
        
        //in this case, there are STAND ALONE elements (1 or more).
        if(allRelationsWithSameDownElement && elementsWithNoRelation){
            String newRel = downElement + ",";
            for(String x: level1elements){
                newRel = newRel + x + ",";
            }
            newRel = newRel.substring(0, newRel.length()-1);
            downset.add(newRel);
        }
        //in this case, ALL level1elements have same down element
        if(allRelationsWithSameDownElement && ((level0elements.size() + level1elements.size())>3) && !elementsWithNoRelation){
            int i = 0;
            downset.add(downElement + "," + level1elements.get(i) + "," + level1elements.get(i+1));
            downset.add(downElement + "," + level1elements.get(i) + "," + level1elements.get(i+2));
            downset.add(downElement + "," + level1elements.get(i+1) + "," + level1elements.get(i+2));
        }
        //****STEP 4.4****
        //remove same ternary sets
        Set<String> tempSet = new HashSet<>();
        for(String x: downset){
            if(x.length() == 5){
                for(String y: downset){
                    if(y.length() == 5 && !x.equals(y)){
                        tempSet.add(y);
                    }
                }
            }
        }
        /*all ternary sets are in tempSet
        Now i have to remove same sets*/
        byte sameElements = 0;
        Set<String> elementsToRemove = new HashSet<>();
        for(String x: tempSet){
            sameElements = 0;
            String[] t1 = x.split(",");
            for(String y: tempSet){
                sameElements = 0;
                if(!elementsToRemove.contains(y) && !elementsToRemove.contains(x)){
                    if(!x.equals(y)){
                        String[] t2 = y.split(",");
                        for(int i = 0; i < t1.length; i++){
                            for(int j = 0; j < t2.length; j++){
                                if(t1[i].equals(t2[j])){
                                    sameElements++;
                                    if(sameElements == 3){
                                        elementsToRemove.add(y);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } 
        for(String x: elementsToRemove){
            if(downset.contains(x)){
                downset.remove(x);
            }
        }
        
        
	//************STEP 5****************
	String all = "";
	for(int i = 0; i < setElements.length; i++){
            all = all + setElements[i];
            if(i != setElements.length-1){
		all = all + ",";
            }
	}
	downset.add(all);

	//-------------------------------------------------------------------------

	//convert HashSet into String[]
	downsetArr = new String[downset.size()];
	int i = 0;
	for(String x: downset){
            downsetArr[i++] = x;
	}
	Arrays.sort(downsetArr, new java.util.Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
        		return s1.length() - s2.length();
            }
	});
     
        
        GraphVizManager.createDownsetGraphDotFile(downsetArr);
        GraphVizManager.launchDot(DOT_FILE_NAME, PNG_FILE_NAME);
        
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    public static String[] getDownset(){
        return downsetArr;
    }
    
}
