package GrafoiProject2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Osia Dalampira-Kiprigli 2744
 */
public class GrafoiProject2 {

     private static String FILENAME=null;
    
   /*
    * This method calculates how many lines has our input file.
    * Since an adjacency matrix has the same number of columns
    * and rows, we only need to know the number of lines of the file
    * to calculate the desirable size.
    */  
    public static int calculateLines(String filename) throws Exception{
        Path path = Paths.get(filename);
        long lineCount = Files.lines(path).count();
        int lines = (int) lineCount;
        return lines;
    }
    /*
    * This method reads the adjacency matrix from the input file
    * and stores it to a 2d array that represents the adjacency
    * matrix to be returned.
    */
    
    public static String[][] loadMatrixData(String filename) throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        int lines = calculateLines(filename);
        String [][] adjacency_matrix = new String[lines][lines];
        while(scanner.hasNextLine()) {
             for (int i=0; i<adjacency_matrix.length; i++) {
                String[] line = scanner.nextLine().trim().split(" ");
                for (int j=0; j<line.length; j++) {
                    adjacency_matrix[i][j] = (String) (line[j]);
                }
            }
        }
        scanner.close();
        System.out.println("The adjacency matrix is: ");
        displayTwoDArray(adjacency_matrix);
        System.out.println();
        System.out.println();
     
        return adjacency_matrix;
    }

    
        /*
    * This method multiplies the given matrices and returns a new matrix as a result.
    * 
    */
        
    public static String[][] multiplyMatrices(String matrix[][], String plain_matrix[][]){
        
        int length = matrix.length;
        String[][] newMatrix = new String[length][length]; 
        //We initialize the new array with "0"
        for(int i=0;i<newMatrix.length;i++){
            for (int j=0; j<newMatrix.length; j++){
                newMatrix[i][j]="0";
            }          
        }
        //We create a second array called oldMatrix to help us store the data 
        //and we initialize it with "0" as well
        String[][] oldMatrix = new String[length][length];
        for(int i=0;i<oldMatrix.length;i++){
            for (int j=0; j<oldMatrix.length; j++){
                oldMatrix[i][j]="0";
            }          
        }
        //Traversing every cell of the matrix
        for(int i=0; i<length; i++){
            for (int j=0; j<length; j++){
                for (int k=0; k<length; k++){
                    //String exists is the sequence of vertexes to be multiplied with the vertex of the M matrix
                    //If exists.equals("0"), that means the vertex already exists in that sequence
                    //If !(exists.equals("0")) , that means the vertex does not exist n the sequence and we can multiply the matrices 
                    String exists=alreadyExists(matrix[i][k],plain_matrix[k][j]);              
                    //If neither of the matrices' cells equal to "0" and the vertex does not exist in the sequence we
                    //can multiply the matrices
                    if( !(matrix[i][k].equals("0")) && !(plain_matrix[k][j]).equals("0") && 
                            !(exists.equals("0"))){
                        //The matrix contains two sequences that are separated by "|"
                        if(matrix[i][k].contains("|")){
                            //If both of the sequences do not contain the vertex of the plain matrix
                            //the variable exists also contains two sequences separated by "|"
                                if(exists.contains("|")){
                                    //We split those parts and store them to two string variables called part1 and part2.
                                    String[] matrices = exists.split("\\|");
                                    String part1=matrices[0];
                                    String part2 = matrices[1];
                                    //The result of the multiplication is stored to a matrix called newMatrix and it contains
                                    //the first sequence(part1) + the new vertex and the second sequence(part2)+ the new vertex, separated by "|"
                                    newMatrix[i][j]=part1+plain_matrix[k][j]+"|"+part2+plain_matrix[k][j];
                                }else{ //the variable exists contains only one of the sequences so the result should be that path + the new vertex
                                newMatrix[i][j]=exists +plain_matrix[k][j];
                                    }
                            }
                        //If the matrix contains only one sequence and doesn't have any other data in it,
                        //the new matrix contains the vertex/ces of the matrix + the new vertex of the plain matrix 
                        else if (!matrix[i][k].contains("|")&& oldMatrix[i][j].equals("0")){ 
                            newMatrix[i][j]= matrix[i][k]+ plain_matrix[k][j];
                            //We update the oldMatrix to have the same value as the plain matrix, so in the possibility of a new 
                            //sequence of verteces we also take notice of the previous sequence
                            oldMatrix[i][j]=newMatrix[i][j];
                        } 
                         
                        //There is already a sequence of vertexes, so the new value should contains both of 
                        //the sequences separated be "|"
                        else {
                       
                            newMatrix[i][j]=newMatrix[i][j]+"|"+matrix[i][k]+ plain_matrix[k][j];
                        }
                    }
                }
            }
        }
       
        return newMatrix;
    }
    
    
    /*
    * This method is used to check whether a vertex already exists in the sequense of
    * vertexes of the matrix to be multiplied with the M matrix
    * @return "0" if the vertex exists or if it doesn't it returns the string to be multiplied
    */
    public static String alreadyExists(String vertexNumbers, String vertexNumber){
        //Initialization of a flag to keep track 
        int flag=0;
        String result=vertexNumbers; //result is initialized to be the sequence of the verteces
        if (vertexNumbers.contains("|")){ //if there are two different sequences until now to one cell
            String[] arrays = vertexNumbers.split("\\|"); //we split them to an array
            if(arrays[0].contains(vertexNumber)){
               
               if(arrays[1].contains(vertexNumber)){
                   result="0"; //Both sequences contain the vertex so the result equals to "0"
                   flag=1;
                }
               else{
                   result=arrays[1]; //The first sequence contains the vertex so the result equals to the second sequence
                   flag=-1;
                }
            }
                        
            if(flag==0){ 
                if(arrays[1].contains(vertexNumber)){ //The flag equals to 0 so if the second sequence contains the vertex, 
                    result=arrays[0];                 //the result is set to be the first sequence. If not, none of the sequences
                }                                     //contains the vertex so the result is the whole sequence of vertexes
            }
            
        }else{ //There is only one sequence to the cell
            if(vertexNumbers.contains(vertexNumber)){ //If the sequence contains the vertex, the result is set to "0"
                 result="0";                          //If not, we return the whole sequence of vertexes
            }
        }
      
        return result;
    }
    
    
    /* 
    * This method creates the M1 matrix and returns it, using the adjacency
    * matrix. When a cell of the adjacency matrix equals to "1", we have an edge between 
    * two verteces at the same cell at the M1 matrix. When a cell equals 
    * to "0" at the adjacency matrix, that means we don't have an endge and we also 
    * get a "0" at the M1 matrix.
    */
    public static String[][] create_M1_matrix(String[][] adj_matrix){
        
        String [][] matrix1 = new String[adj_matrix.length][adj_matrix.length];
        for(int i=0; i<adj_matrix.length; i++){
            for (int j=0;j<adj_matrix.length;j++){
                if(adj_matrix[i][j].equals("1")){
                    //Our vertexes start at the number of 1, so we need to add 1 (+1) to the
                    //index number of i and j since the arrays start counting from number zero
                    matrix1[i][j]=String.valueOf(i+1)+String.valueOf(j+1);
                }else{
                    //All the other cells equal to zero 
                    matrix1[i][j]="0";
                }
            }
        }
                
        return matrix1;
    }

    /* 
    * This method creates the M matrix and returns it, using the adjacency
    * matrix. When a cell of the adjacency matrix equals to "1", we have an edge between 
    * two vertexes but we keep only the second vertex at the M matrix. When a cell equals 
    * to "0" at the adjacency matrix, that means we don't have an endge and we also 
    * get a "0" at the M1 matrix.
    */
    public static String[][] create_M_matrix(String[][] adj_matrix){
        
        String [][] p_matrix = new String[adj_matrix.length][adj_matrix.length];
        for (int i=0;i<p_matrix.length;i++){
            for (int j=0;j<p_matrix.length;j++){
                if(adj_matrix[i][j].equals("1")){
                    //Our matrix counts vertexes from 1 so we add 1 (+1) to the
                   //index number of i and j since the arrays start counting from number zero
                    p_matrix[i][j]= String.valueOf(j+1);
                }else{
                    p_matrix[i][j]="0";
                }
            }
        }
        
        return p_matrix;  
    }
    
    /*
    * This method is used to check if we have a hamiltonian circle or path
    * at the last matrix we create.
    * @param checkingArray is the array with the sequence of verteces
    * @param checkingString is the string to be checked if it matches with the edges at the M1 matrix
    * @param matrixString is the cell of the M1 matrix to be checked with the checkingString 
    */
    public static int hamiltonian_circle(String[] checkingArray, String checkingString, String matrixString){
        int circle=0;
        //if the checkingString matches with the matrixString, that means we have a hamiltonian circle
        if (checkingString.equals(matrixString)){
                       System.out.println("Hamiltonian circle: ");
                       for(int i=0; i<checkingArray.length; i++){
                           System.out.print(checkingArray[i]+" ");
                        }
                       System.out.println();
                       System.out.println();
                       circle = 1;
                       }
        return circle;
    } 
    
    /*
    * This method is used to display the matrices using
    * 10 digits for every string and having them centered
    */
    
    public static void displayTwoDArray(String[][] matrix){
        
        for (String[] row : matrix)
        {
            for (String column : row)
            {
                System.out.printf("%10s    ", column);
            }
        System.out.println();
        }
    }
    
    
        /*
    * This method is used to display the content of a one dimension array
    */
    public static void displayArray(String[] array){
        
        for (int i=0; i<array.length; i++){
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }
   
    /*
    * This method sets the cells of the adjacency matrix that correspond to the edges of the hamiltonian circle to zero
    * @return the edges of the hamiltonian circle
    */
    public static String[] zeroesToAdjacency(String[] t_circle, String[][] adj_matrix){
        //We create an array that stores the edges of the hamiltonian circle
        String[] ham_edges= new String[t_circle.length];
        for(int i=0; i<t_circle.length; i++){
            int begin, end;
            int j=i;
            if(i==t_circle.length-1){ //When we get to the last element of the circle we have an edge between the last and the first vertex
                begin = Integer.parseInt(t_circle[i]);
                begin = begin - 1; //because we start counting from zero to arrays and our hamiltonian circle counts from 1
                end = Integer.parseInt(t_circle[0]);
                end = end - 1;
                ham_edges[i]=t_circle[i]+t_circle[0]; //the edge that appears to the hamiltonian circle
            }else{
                begin = Integer.parseInt(t_circle[i]);
                begin = begin - 1;
                end = Integer.parseInt(t_circle[j+1]);
                end = end -1;
                ham_edges[i]=t_circle[i]+t_circle[j+1]; //the edge that appears to the hamiltonian circle
            } 
            //We set the cells to zero since we used that edge for the hamiltonian circle
            adj_matrix[begin][end]="0"; //The adjacency matrix is symmetrical
            adj_matrix[end][begin]="0"; //so we have to check both cells
  
        }
        return ham_edges;
    }
    
    /*
    * This method is used to store the edges that are left out from the hamiltonian circle
    * and keeps only one copy of them because our adjacency matrix is symmetrical.
    * @return the arraylist of the edges
    */
    public static ArrayList keepingTheEdges(String[][] adj_matrix){
        ArrayList<String> edges = new ArrayList();
        for (int i=0; i<adj_matrix.length; i++){
            for (int j=0; j<adj_matrix.length; j++){ //We check if the adjacency matrix equals to 1 at a specific cell, meaning that there is
                //an edge. If the same edge doesn't already exist in the arraylist of edges, we add it to the list
                if (adj_matrix[i][j].equals("1") && !edges.contains(String.valueOf(i+1)+String.valueOf(j+1)) 
                        && !edges.contains(String.valueOf(j+1)+String.valueOf(i+1))){
                   edges.add(String.valueOf(i+1) + String.valueOf(j+1)); //The matrix starts counting from 0 and our verteces
                }                                                        //start counting from 1
            }
        }
       
       return edges;
    }
    
    /*
    * This method is used to add all the vertexes of our graph to a string that we are going
    * to need later
    * @return the string with the vertexes
    */
    
    public static String loadVertexes(int number){
        String vertexes="0";
        String j;
        for(int i=0; i<number; i++){
            j=String.valueOf(i+1);
            vertexes=vertexes+j;
        }
        return vertexes;
    }
    
    /*
    * This method is used to create a two-d array that is keeps record of the vertexes that can have an edge between them
    * It has a size of (number_of_vertexes)*(2), meaning that every row represents a vertex and the first column represents
    * the vertexex that the current vertex can be connected to INSIDE the hamiltonian circle and the second column represents the
    * the vertexes that the current vertex can be connected to OUTSIDE the hamiltonian circle
    * To do that, it needs the array that contains the edges of the hamiltonian circle
    * @return the array that is created
    */
    public static String[][] createArrayForVertexes(String[] ham_edges, int length){
        String[][] vertexesArray = new String[length][2];
        for (int i=0;i<length;i++){
            for(int j=0; j<2; j++){
                vertexesArray[i][j]=loadVertexes(length); //we load our array with all the vertexes
            }
        }
        int p;
        for (int i=0; i<length; i++){
            p=i+1; //arrays start counting from 0 but our vertexes start counting from 1
            for (int j=0; j<2; j++){ 
                for(int k=0; k<ham_edges.length; k++){
                    if(ham_edges[k].contains(String.valueOf(p))){ //if the edge contains the current vertex
                       String[] v = ham_edges[k].split(""); //we split the edges and we store them
                       String v1=v[0];                      //to two variables that represent the verteces
                       String v2=v[1];

                        if(v1.equals(String.valueOf(p))){ //we remove the vertex that the current vertex is connected to
                                                          //because they are already connected
                            vertexesArray[i][j] = vertexesArray[i][j].replace(v2, "");
                        }else{
                            vertexesArray[i][j] = vertexesArray[i][j].replace(v1, "");
                        }
                     
                    }//remove the current vertex since we want to keep only the vertexes that we can have an edge between them
                     vertexesArray[i][j] = vertexesArray[i][j].replace(String.valueOf(p), "");
                }     
            }
        }
        return vertexesArray;
    }
    
    /*
    * This method is used to add the remaining edges to the graph. To do so, it requires the 2-d array
    * of the vertexes that contains the possible connections between them, an arraylist of the edges that
    * are going to be added and an array that contains the vertexes of the hamiltonian circle.
    * The method illustates the idea that when we have an edge between two vertexes inside (or outside) the circle
    * the remaining vertexes can get connected only to the ones that are included to the same path beginning from 
    * the first vertex and ending to the second vertex. If the vertexes from the one path get connected with the ones
    * from the other path inside (or outside) of the circle the previous edge and these ones would be crossed.
    * @return an array that its first element contains the edges of the set A and the second element 
    * contains the edges of the set B where set A=edges inside the hamiltonian circle and set B=the edges
    * outside the hamiltonian circle
    */
    public static String[] addEdges(String[][] t_vertexes, ArrayList<String> t_edges, String[] t_circle){
        String[] array= new String[2]; //is going to be returned with set A at its first index and set B at its second
        ArrayList<String> a = new ArrayList();
        ArrayList<String> b = new ArrayList();
    
        for(int i=0; i<t_edges.size(); i++){
            String[] parts = t_edges.get(i).split("");
            String part1 = parts[0]; //first vertex of the edge to be added
            String part2 = parts[1]; //second vertex of the edge to be added
            int index1=0, index2=0;
            String[] circle_vertexes = new String[2]; //is going to store the vertexes from the two different paths
            
            circle_vertexes[0] = "";
            circle_vertexes[1] = "";
            
            for(int m=0; m<t_circle.length; m++){ //we keep the index of the first vertex from the variable t_circle
                    if(part1.equals(t_circle[m])){
                        index1=m;
                    }
                }
                for (int k=0; k<t_circle.length; k++){ //we keep the index of the second vertex
                    if(part2.equals(t_circle[k])){
                        index2=k;
                    }
                }
                if(index1>index2){
                    for(int z=index2+1;z<index1; z++){
                        circle_vertexes[0]=circle_vertexes[0]+t_circle[z]; //we store the vertexes of the one path
                    }
                    for(int z=0; z<t_circle.length; z++){
                        circle_vertexes[1]=circle_vertexes[1]+t_circle[z]; //we store the whole circle to the variable
                    }    
                    circle_vertexes[1] = circle_vertexes[1].replace(part1, ""); //we remove the vertexes of the edge
                    circle_vertexes[1] = circle_vertexes[1].replace(part2, "");
                    circle_vertexes[1] = circle_vertexes[1].replace(circle_vertexes[0], ""); //we remove the vertexes of the first
                                                                                             //path and we get the second path
                    
                }
                else{ 

                    for(int z=index1+1;z<index2; z++){

                        circle_vertexes[0]=circle_vertexes[0]+t_circle[z];
                    }
                    for(int q=0; q<t_circle.length; q++){
                        circle_vertexes[1]=circle_vertexes[1]+t_circle[q];
                    }    
                    circle_vertexes[1] = circle_vertexes[1].replace(part1, "");
                    circle_vertexes[1] = circle_vertexes[1].replace(part2, "");
                    circle_vertexes[1] = circle_vertexes[1].replace(circle_vertexes[0], "");
                    
                }
                String[] single_vertexes1 = circle_vertexes[0].split(""); //we split the paths to single vertexes
                String[] single_vertexes2 = circle_vertexes[1].split("");

                //if the t_vertexes array at the beginnig vertex of the edge contains the ending vertex of the edge INSIDE the circle
                if(t_vertexes[Integer.parseInt(part1)-1][0].contains(part2)){
                    //arrays start counting from 0 but we count the vertexes from 1
                t_vertexes[Integer.parseInt(part1)-1][0] = t_vertexes[Integer.parseInt(part1)-1][0].replace(part2, ""); //remove the vertex
                t_vertexes[Integer.parseInt(part1)-1][1] = t_vertexes[Integer.parseInt(part1)-1][1].replace(part2, ""); //remove the vertex since it is already added
             
                for(int y=0; y<single_vertexes1.length; y++){ //remove the vertexes of the one path form the others of the second path
                    for (int x=0;x<single_vertexes2.length;x++){
                        //arrays start counting from 0 but we count the vertexes from 1
                        if(t_vertexes[Integer.parseInt(single_vertexes1[y])-1][0].contains(single_vertexes2[x])){
                            t_vertexes[Integer.parseInt(single_vertexes1[y])-1][0] = t_vertexes[Integer.parseInt(single_vertexes1[y])-1][0].replace(single_vertexes2[x], "");
                          
                        } 
                    }
                    
                }
                //remove the beginning vertex from the ending vertex
                t_vertexes[Integer.parseInt(part2)-1][0] = t_vertexes[Integer.parseInt(part2)-1][0].replace(part1, ""); //INSIDE the circle
                t_vertexes[Integer.parseInt(part2)-1][1] = t_vertexes[Integer.parseInt(part2)-1][1].replace(part1, ""); //OUTSIDE the circle
                for(int y=0; y<single_vertexes2.length; y++){ //remove the vertexes of the second path from the others of the first path
                    for (int x=0;x<single_vertexes1.length;x++){
                        if(t_vertexes[Integer.parseInt(single_vertexes2[y])-1][0].contains(single_vertexes1[x])){
                            t_vertexes[Integer.parseInt(single_vertexes2[y])-1][0] = t_vertexes[Integer.parseInt(single_vertexes2[y])-1][0].replace(single_vertexes1[x], "");
                           
                        } 
                    }
                    
                }
                //add edge to set A
                a.add(t_edges.get(i));
               
            //if the t_vertexes array at the beginnig vertex of the edge contains the ending vertex of the edge OUTSIDE the circle
            }else if(t_vertexes[Integer.parseInt(part1)-1][1].contains(part2)){
                //arrays start counting from 0 but we count the vertexes from 1
                t_vertexes[Integer.parseInt(part1)-1][1] = t_vertexes[Integer.parseInt(part1)-1][1].replace(part2, ""); //remove the vertex
             
               for(int y=0; y<single_vertexes1.length; y++){ //remove the vertexes of the one path from the others of the second path
                    for (int x=0;x<single_vertexes2.length;x++){
                        if(t_vertexes[Integer.parseInt(single_vertexes1[y])-1][1].contains(single_vertexes2[x])){
                            t_vertexes[Integer.parseInt(single_vertexes1[y])-1][1] = t_vertexes[Integer.parseInt(single_vertexes1[y])-1][1].replace(single_vertexes2[x], "");
                          
                        } 
                    }
                    
                }//remove the beginning vertex from the ending vertex
                t_vertexes[Integer.parseInt(part2)-1][1] = t_vertexes[Integer.parseInt(part2)-1][1].replace(part1, "");
                //remove the vertexes of the second path from the ones of the first path
                    for(int y=0; y<single_vertexes2.length; y++){ 
                    for (int x=0;x<single_vertexes1.length;x++){
                        if(t_vertexes[Integer.parseInt(single_vertexes2[y])-1][1].contains(single_vertexes1[x])){
                            t_vertexes[Integer.parseInt(single_vertexes2[y])-1][1] = t_vertexes[Integer.parseInt(single_vertexes2[y])-1][1].replace(single_vertexes1[x], "");
                        } 
                    }
                    
                }
                //add edge to set B
                b.add(t_edges.get(i));
            }else{
                System.out.println("The edges are crossed so the graph can't be planar!");
                System.exit(0);
            }
            
        }
        array[0] = String.join(", ", a);
        array[1] = String.join(", ", b);
        System.out.println();
 
        return array;
    }
    
    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws Exception{

       //When a file is not provided, the "sample.txt" is used
        if(args.length<=0){
            FILENAME = "sample.txt";
        }else{
            FILENAME=args[0];
        }
        
        //creation of the adjacency matrix
        String[][] adjacency_matrix = loadMatrixData(FILENAME);
        
        
        //The number of vertexes equals to the length of the adjacency matrix since
        //we have a square matrix
        int vertex = adjacency_matrix.length;
 
        //creation of the M1 matrix
        String [][] matrix = create_M1_matrix(adjacency_matrix);
               
        //creation of the M matrix
        String [][] plain_matrix = create_M_matrix(adjacency_matrix);
        
        //We create a new matrix and assign it's cells to "0"
        String[][] newMatrix = new String [vertex][vertex];
        for(int i=0; i<vertex;i++){
            for (int j=0;j<vertex;j++){
                newMatrix[i][j]="0";
            }
        }

        //We multiply the M1 matrix (matrix) and the M matrx (plain_matrix)
        //together and set the result to the newMatrix variable
        newMatrix = multiplyMatrices(matrix, plain_matrix);
        
         //We already have multiplied two matrices together, so we have a matrice with
        //three vertexes at some cells. That is why we create a for-loop starting 
        //from the number of vertexes minus 3 until we have no vertex left,
        //and we call the method multiplyMatrices() everytime till we reach the end of the graph
        //and we have visited every vertex
        for(int i=vertex-3; i>0; i--){
            newMatrix = multiplyMatrices(newMatrix, plain_matrix);
        }
        
          
        String[] checking;
        int ham_circle1, ham_circle2;
        String[] circle=null;
        //we trsverse every cell to check if it has a hamiltonian circle
        search : {
        for (int i=0; i<newMatrix.length; i++){
            for(int j=0;j<newMatrix.length; j++){
                
                if(!newMatrix[i][j].equals("0")){
                   if(newMatrix[i][j].contains("|")){ //if the cell of the new matrix contains two sequences
                       String[] checking1;
                       String[] checking2;
                       checking = newMatrix[i][j].split("\\|");
                       checking1=checking[0].split(""); //We save each sequence to a different array
                       checking2=checking[1].split(""); //and we name them checking1, checking2
                       
                       String trial1= checking1[checking1.length-1]+ checking1[0]; //We save the last and the fist element of 
                       String trial2= checking2[checking2.length-1]+ checking2[0]; //the array as a string and we name them trial1, trial2
                       
                       //We use the function hamiltonian_circle() to check if any of our strings 
                       //matches with same edges at our adjacency matrix or not
                       ham_circle1= hamiltonian_circle(checking1,trial1, matrix[j][i] );
                       if (ham_circle1==1){
                          circle=checking1;
                          break search;
                       }else{
                           ham_circle2= hamiltonian_circle(checking2,trial2, matrix[j][i] );
                           if(ham_circle2==1){
                               circle=checking2;
                               break search;
                           }
                       }
                       
                    
                } else{
                    //if the cell of the new matrix contains one sequence
                    checking=newMatrix[i][j].split(""); //We save the path to an array
                    String trial = checking[checking.length-1]+ checking[0]; //We save the last and the first item of the array as a string
                    
                    //We check if the string matches with the same edge or not at the adjacency matrix
                    ham_circle1=hamiltonian_circle(checking, trial, matrix[j][i]);
                    if(ham_circle1==1){
                        circle = checking;
                        
                        break search;
                    }

                    }
                }
            }         
        }
        }
        if(circle==null){
            System.out.println("There is no Hamiltonian circle so the graph can't be planar!");
            System.exit(0);
        }
        
        String[] ham_edges=zeroesToAdjacency(circle, adjacency_matrix);
                        
        ArrayList<String> edges=keepingTheEdges(adjacency_matrix);
        
        String[][] vertecesArray= createArrayForVertexes(ham_edges, circle.length);
        
        String[] finalArray = addEdges(vertecesArray, edges, circle);
        System.out.println("The graph is planar!");
        String a = finalArray[0];
        String b = finalArray[1];
        
        System.out.println("Set A includes "+ a);
        System.out.println("Set B includes "+ b);
        
    }
}  