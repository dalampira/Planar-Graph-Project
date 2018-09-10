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
    
    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws Exception{

        
    }
}  