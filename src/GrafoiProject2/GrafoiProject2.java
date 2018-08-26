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