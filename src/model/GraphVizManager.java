package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class GraphVizManager {
    
    private final static String SET_ELEMENT_LEFT_DELIMITER = "\"{";
    private final static String SET_ELEMENT_RIGHT_DELIMITER = "}\"";
    private final static String LINK = " -- ";
    private final static String CART_PRODUCT_LEFT_DELIMITER = "\"(";
    private final static String CART_PRODUCT_RIGHT_DELIMITER = ")\"";
    
    public GraphVizManager(){}
    
    //-----------------------------------------------------------------------------------------------------------------------------------------
    
    public static void launchDot(String fileInputname, String fileOutputName) throws IOException{
        /*
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "cd \"C:\\Users\\Ale\\Documents\\netbeansprojects\\gmaker\" && dot -Tpng " + fileInputname +  " > " + fileOutputName);
        builder.redirectErrorStream(true);
        */
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "cd " + System.getProperty("user.dir") + "\\&& dot -Tpng " + fileInputname +  " > " + fileOutputName);
        builder.redirectErrorStream(true);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null){
                break;
            }
        }
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------------
    
    public static void createRelationsGraphDotFile(ArrayList<String> rel) throws IOException{
        String output = RelationsGraphGenerator.getDotFileName();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("graph relations_graph{ rankdir=BT\n");
            for(String x: rel){
                writer.write(x + "\n");
            }
            writer.write("}");
        }
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------------
    
    public static void createDownsetGraphDotFile(String[] downset) throws IOException{
        String output = DownsetGraphGenerator.getDotFileName();
        Set<String> fileRows = new TreeSet<>();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("graph downsets_graph{ rankdir=BT\n");
            
            downset[0] = ""; //correct representation of empty set is {}
            //print {} and its supersets
            for(int i = 1; downset[i].length() == 1; i++){
		String t0 = SET_ELEMENT_LEFT_DELIMITER + downset[0] + SET_ELEMENT_RIGHT_DELIMITER +
			LINK + SET_ELEMENT_LEFT_DELIMITER + downset[i] + SET_ELEMENT_RIGHT_DELIMITER;
		fileRows.add(t0 + "\n");
            }
            
            //print {x} and its supersets
            for(int i = 0; i < downset.length; i++){
		for(int j = i + 1; j < downset.length; j++){
                    if(downset[i].length() == 1 && downset[j].length() == 3){
			String[] t = downset[j].split(",");
			for(int k = 0; k < t.length; k++){
                            if(downset[i].equals(t[k])){
				String t1 = SET_ELEMENT_LEFT_DELIMITER + downset[i] + SET_ELEMENT_RIGHT_DELIMITER +
					LINK + SET_ELEMENT_LEFT_DELIMITER + downset[j] + SET_ELEMENT_RIGHT_DELIMITER;
				fileRows.add(t1 + "\n");
				break;
                            }
			}
                    }
		}
            }

            //print {x,y} and its supersets
            byte elementsContainedInSuperset;
            for(int i = 0; i < downset.length; i++){
		for(int j = i + 1; j < downset.length; j++){
                    if(downset[i].length() == 3 && downset[j].length() == 5){
                        elementsContainedInSuperset = 0;
			String[] s = downset[i].split(",");
			String[] t = downset[j].split(",");
			for(int k = 0; k < s.length; k++){
                            for(int z = 0; z < t.length; z++){
				if(s[k].equals(t[z])){
                                    elementsContainedInSuperset++;
                                    if(elementsContainedInSuperset == s.length){
                                        String t2 = SET_ELEMENT_LEFT_DELIMITER + downset[i] + SET_ELEMENT_RIGHT_DELIMITER +
                                            LINK + SET_ELEMENT_LEFT_DELIMITER + downset[j] + SET_ELEMENT_RIGHT_DELIMITER;
                                        fileRows.add(t2 + "\n");
                                        break;
                                    }
				}
                            }
			}
                    }
		}
            }
            
            //print {x,y,z} and its supersets
            for(int i = 0; i < downset.length; i++){
		for(int j = i + 1; j < downset.length; j++){
                    if(downset[i].length() == 5 && downset[j].length() == 7){
                        elementsContainedInSuperset = 0;
			String[] s = downset[i].split(",");
			String[] t = downset[j].split(",");
			for(int k = 0; k < s.length; k++){
                            for(int z = 0; z < t.length; z++){
				if(s[k].equals(t[z])){
                                    elementsContainedInSuperset++;
                                    if(elementsContainedInSuperset == s.length){
                                        String t3 = SET_ELEMENT_LEFT_DELIMITER + downset[i] + SET_ELEMENT_RIGHT_DELIMITER +
                                            LINK + SET_ELEMENT_LEFT_DELIMITER + downset[j] + SET_ELEMENT_RIGHT_DELIMITER;
                                        fileRows.add(t3 + "\n");
                                    break;
                                    }
				}
                            }
			}
                    }
		}
            }
            
            //TODO add {x,y,z,w} and its supersets
            /*
            for(int i = 0; i < downset.length; i++){
		for(int j = i + 1; j < downset.length; j++){
                    if(downset[i].length() == 7 && downset[j].length() == 9){
                        elementsContainedInSuperset = 0;
			String[] s = downset[i].split(",");
			String[] t = downset[j].split(",");
			for(int k = 0; k < s.length; k++){
                            for(int z = 0; z < t.length; z++){
				if(s[k].equals(t[z])){
                                    elementsContainedInSuperset++;
                                    if(elementsContainedInSuperset == s.length){
                                        String t3 = SET_ELEMENT_LEFT_DELIMITER + downset[i] + SET_ELEMENT_RIGHT_DELIMITER +
                                            LINK + SET_ELEMENT_LEFT_DELIMITER + downset[j] + SET_ELEMENT_RIGHT_DELIMITER;
                                        fileRows.add(t3 + "\n");
                                    break;
                                    }
				}
                            }
			}
                    }
		}
            }
            */
            for(String x: fileRows){
                writer.write(x);
            }
            writer.write("}");
        }
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------------
    
    public static void createJoinIrreducibleElementsGraphDotFile(String[] joinIrreducibleElementsSet) throws IOException{
        String output = JoinIrreducibleElementsGraphGenerator.getDotFileName();
        Set<String> fileRows = new TreeSet<>();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("graph join_irreducible_elements_graph{ rankdir=BT\n");
            
            //print {x} and its supersets
            for(int i = 0; i < joinIrreducibleElementsSet.length; i++){
		for(int j = i + 1; j < joinIrreducibleElementsSet.length; j++){
                    if(joinIrreducibleElementsSet[i].length() == 1){
			String[] t = joinIrreducibleElementsSet[j].split(",");
			for(int k = 0; k < t.length; k++){
                            if(joinIrreducibleElementsSet[i].equals(t[k])){
				String t1 = SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[i] + SET_ELEMENT_RIGHT_DELIMITER +
                                            LINK + SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[j] + SET_ELEMENT_RIGHT_DELIMITER;
				fileRows.add(t1 + "\n");
				break;
                            }
                            else{
                                //add element which hasn't got relations with other elements
                                if(k == t.length -1){ //add it only when all elements have been checked
                                    String t1 = SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[i] + SET_ELEMENT_RIGHT_DELIMITER;
                                    fileRows.add(t1 + "\n");
                                    
                                    //check if next element is the last. if yes, add it
                                    if(joinIrreducibleElementsSet[i+1].equals(joinIrreducibleElementsSet[joinIrreducibleElementsSet.length-1])){
                                        String t2 = SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[i+1] + SET_ELEMENT_RIGHT_DELIMITER;
                                        fileRows.add(t2 + "\n");
                                    }
                                    break;
                                } 
                            }
                            
			}
                        
                    }
		}
            }

            //print {x,y} and its supersets
            byte elementsContainedInSuperset;
            for(int i = 0; i < joinIrreducibleElementsSet.length; i++){
		for(int j = i + 1; j < joinIrreducibleElementsSet.length; j++){
                    if(joinIrreducibleElementsSet[i].length() == 3){
                        elementsContainedInSuperset = 0;
			String[] s = joinIrreducibleElementsSet[i].split(",");
			String[] t = joinIrreducibleElementsSet[j].split(",");
			for(int k = 0; k < s.length; k++){
                            for(int z = 0; z < t.length; z++){
				if(s[k].equals(t[z])){
                                    elementsContainedInSuperset++;
                                    if(elementsContainedInSuperset == s.length){
                                        String t2 = SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[i] + SET_ELEMENT_RIGHT_DELIMITER +
                                            LINK + SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[j] + SET_ELEMENT_RIGHT_DELIMITER;
                                        fileRows.add(t2 + "\n");
                                    break;
                                    }
				}
                            }
			}
                    }
		}
            }

            //print {x,y,z} and its supersets
            for(int i = 0; i < joinIrreducibleElementsSet.length; i++){
		for(int j = i + 1; j < joinIrreducibleElementsSet.length; j++){
                    if(joinIrreducibleElementsSet[i].length() == 5){
                        elementsContainedInSuperset = 0;
			String[] s = joinIrreducibleElementsSet[i].split(",");
			String[] t = joinIrreducibleElementsSet[j].split(",");
			for(int k = 0; k < s.length; k++){
                            for(int z = 0; z < t.length; z++){
				if(s[k].equals(t[z])){
                                    elementsContainedInSuperset++;
                                    if(elementsContainedInSuperset == s.length){
                                        String t3 = SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[i] + SET_ELEMENT_RIGHT_DELIMITER +
                                            LINK + SET_ELEMENT_LEFT_DELIMITER + joinIrreducibleElementsSet[j] + SET_ELEMENT_RIGHT_DELIMITER;
                                        fileRows.add(t3 + "\n");
                                    break;
                                    }
				}
                            }
			}
                    }
		}
            }
            
            for(String x: fileRows){
                writer.write(x);
            }
            writer.write("}");
        }
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------------
    
    public static void createSetOfDisjointDownsetGraphDotFile(String[] disjointSet) throws IOException{
        String output = SetOfDisjointDownsetGraphGenerator.getDotFileName();
        Set<String> fileRows = new TreeSet<String>();

	try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("graph set_of_disjoint_downsets_graph{ rankdir=BT\n");

            for(int i = 0; i < disjointSet.length; i++){
                for(int j = 0; j < disjointSet.length; j++){
                    if(!disjointSet[i].equals(disjointSet[j])){
                        String[] temp1 = disjointSet[i].split("/");
                        String[] temp2 = disjointSet[j].split("/");
                        boolean firstIncluded = false;
                        boolean secondIncludes = false;
                        
                        //logic
                        if(temp1[0].equals("0")){
                            if(temp1[1].equals("0")){
                                //case (0,0)
                                if(temp2[0].length() == 1 && temp2[1].length() == 1){
                                    if(temp2[0].equals("0") || temp2[1].equals("0")){
                                        String t = "";
                                        if(temp2[0].equals("0")){
                                            String first = "{},{}";
                                            String second = "{},{" + temp2[1] + "}";
                                            t = CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[j]*/ second + CART_PRODUCT_RIGHT_DELIMITER +
                                                LINK +
                                                CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[i]*/ first + CART_PRODUCT_RIGHT_DELIMITER;
                                        }
                                        else{
                                            String first = "{},{}";
                                            String second =  "{" + temp2[0] + "},{}";
                                            t = CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[i]*/ first + CART_PRODUCT_RIGHT_DELIMITER +
                                                LINK +
                                                CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[j]*/ second + CART_PRODUCT_RIGHT_DELIMITER;
                                        }
                                        fileRows.add(t + "\n");
                                    }
                                }
                            }
                            else{
                                //case (0,x)
                                if(temp2[0].length() == 1){
                                    int count = 0;
                                    boolean included = false;
                                    String[] temp2CommaSplit = temp2[1].split(",");
                                    for(int v = 0; v < temp2CommaSplit.length; v++){
                                        if(temp1[1].contains(temp2CommaSplit[v])){
                                            count++;
                                        }
                                        if(count == temp2CommaSplit.length){
                                            if(Math.abs(temp2[1].length() - temp1[1].length()) <= 2){
                                                included = true;
                                            }
                                        }
                                    }
                                    //eliminate link when there is a "middle node"
                                    if(!temp2[0].equals("0")){
                                        int c = 0;
                                        boolean contained = false;
                                        for(int x = 0; x < disjointSet.length; x++){
                                            if(!disjointSet[x].equals(disjointSet[i])){
                                                String[] t = disjointSet[x].split("/");
                                                if((t[0].equals("0")) && (temp2[1].length() != temp1[1].length())){
                                                    String[] t2 = t[1].split(",");
                                                    for(int v = 0; v < t2.length; v++){
                                                        if(temp1[1].contains(t2[v])){
                                                            c++;
                                                        }
                                                        if(c == t2.length){
                                                            contained = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if(contained){
                                            included = false;
                                        }
                                    }
                                    //end
                                    
                                    if(included){
                                        String first = "{},{" + temp1[1] + "}";
                                        String second = temp2[0].equals("0") ? "{}," : "{" + temp2[0] + "},";
                                        if(temp2[1].equals("0")){
                                            second = second + "{}";
                                        }
                                        else{
                                            second = second + "{" + temp2[1] + "}";
                                        }
                                        String t = CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[i]*/ first + CART_PRODUCT_RIGHT_DELIMITER +
                                                   LINK +
                                                   CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[j]*/ second + CART_PRODUCT_RIGHT_DELIMITER;
                                        fileRows.add(t + "\n");
                                    }
                                }
                            }
                        }
                        else{
                            //case (x,y)
                            //1st part
                            String[] temp1CommaSplit = temp1[0].split(",");
                            int count = 0;
                            for(int k = 0; k < temp1CommaSplit.length; k++){
                                if(temp2[0].contains(temp1CommaSplit[k])){
                                    count++;
                                }
                                if(count == temp1CommaSplit.length){
                                    if(Math.abs(temp1[0].length() - temp2[0].length()) <= 2){
                                        firstIncluded = true;
                                    }
                                }
                            }
                            
                            //2nd part
                            if(firstIncluded){
                                if(temp2[1].equals("0")){
                                    if(temp1[1].equals("0")){
                                       secondIncludes = true;
                                    }
                                    if(!temp1[1].equals("0") && temp1[1].length() == 1){
                                        if(temp2[0].length() > temp1[0].length()){
                                            boolean middleNode = false;
                                            for(int z = 0; z < disjointSet.length; z++){
                                                if(!disjointSet[z].equals(disjointSet[i])){
                                                    String[] t = disjointSet[z].split("/");
                                                    if(t[0].contains(temp1[0]) && t[1].equals("0")){
                                                        middleNode = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if(!middleNode){
                                                secondIncludes = true;
                                            }
                                        }
                                        else{
                                            secondIncludes = true;
                                        }
                                    }
                                }
                                else{
                                    String[] temp2CommaSplit = temp2[1].split(",");
                                    int counter = 0;
                                    for(int w = 0; w < temp2CommaSplit.length; w++){
                                        if(temp1[1].contains(temp2CommaSplit[w])){
                                            counter++;
                                        }
                                        if(counter == temp2CommaSplit.length){
                                            if(Math.abs(temp2[1].length() - temp1[1].length()) <= 2){
                                                secondIncludes = true;
                                            }
                                        }
                                    }
                                }
                                
                                //last phase
                                if(secondIncludes){
                                    String first = temp1[0].equals("0") ? "{}," : "{" + temp1[0] + "},";
                                    if(temp1[1].equals("0")){
                                       first = first + "{}";
                                    }
                                    else{
                                       first = first + "{" + temp1[1] + "}";
                                    }
                                    String second = temp2[0].equals("0") ? "{}," : "{" + temp2[0] + "},";
                                    if(temp2[1].equals("0")){
                                       second = second + "{}";
                                    }
                                    else{
                                       second = second + "{" + temp2[1] + "}";
                                    }
                                    String s = CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[i]*/ first + CART_PRODUCT_RIGHT_DELIMITER +
                                               LINK +
					       CART_PRODUCT_LEFT_DELIMITER + /*disjointSet[j]*/ second + CART_PRODUCT_RIGHT_DELIMITER;
                                    fileRows.add(s + "\n");
                                }
                            }
                            
                        }
                        
                    }
                }
            }
            
            //write rows in file
            for(String x: fileRows){
                writer.write(x);
            }
            writer.write("}");
            
            
        }
        
        

    }
    
}
