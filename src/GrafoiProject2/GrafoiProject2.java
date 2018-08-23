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
    

    
    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws Exception{

        
    }
}  