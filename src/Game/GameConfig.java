/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author gilberto
 */
public class GameConfig {

    /**
     * Game iterations
     */
    private int iterations = 0;
    /**
     * Grid size
     */
    private int gridSize = 0;
    
    /**
     * Input file
     */
    private String configFile;
    
    /**
     * Output file
     */
    private String outFile;
    
    /**
     * Matrix
     */
        private int[][] matrix;

    private boolean Debug;

    /**
     * Get the value of Debug
     *
     * @return the value of Debug
     */
    public boolean isDebug() {
        return Debug;
    }

    /**
     * Set the value of Debug
     *
     * @param Debug new value of Debug
     */
    public void setDebug(boolean Debug) {
        this.Debug = Debug;
    }

    /**
     *  Constructor of GameConfig
     * @param args
     */
    public GameConfig(String[] args) {
        
        // Try to parse game parameters
        try{
            if (args.length < 4){
                System.err.println("Not enough arguments.");
                System.exit(1);
            }
            
            
            gridSize = Integer.parseInt(args[0]);
            
            if(gridSize < 2){
                System.err.println("Minimum problem size is 2");
                System.exit(1);
            }
            
            iterations = Integer.parseInt(args[1]);
            configFile = args[2];
            outFile = args[3];
            matrix = new int[gridSize][gridSize];
            
            // only prints a attributes if on debug mode
            if(Debug){
                String outP = "Grid Size: " + gridSize + 
                        "\nNo Iterations: " + iterations +
                        "\nConfig File: " + configFile +
                        "\nOutput File: " + outFile +
                        "\nMatrix size: " + gridSize + "x" + gridSize + "\n";
            
                System.out.println(outP);
            }

        }catch(Exception ex){
            System.err.println("ERROR on parsing parâmeters:\n\t" + ex.getMessage());
            System.exit(1);
        }
        
        // Try to get file reader
        try(FileReader fr = new FileReader(configFile)){
            
            // Try to get file content
            try(BufferedReader br = new BufferedReader(fr)){
                // Try reading the file
                String line = br.readLine();
                while(line != null){
                    
                    // Parse each line                    
                    try{
                        int[] ijv = getThreeInts(line);
                        // Only prints numbers got on debug mode
                        if(Debug)
                            System.out.printf("%d, %d, %d\n",ijv[0], ijv[1], ijv[2]);
                        
                        int i = ijv[0];
                        int j = ijv[1];
                        int v = ijv[2];
                        matrix[i][j] = v;                    
                    }
                    catch(Exception ex){
                        System.err.println("ERROR out of bounds:\n\t" + ex.getMessage() + "\n\t" + ex.getCause());
                        System.exit(1);
                    }
                    
                    line = br.readLine();
                }
                
                // Print matrix on debug mode
                if(Debug){
                    System.out.println("Matrix read:\n");
                    for(int i = 0; i < gridSize; i++){

                        for(int j = 0; j < gridSize; j++){
                            System.out.print(matrix[i][j] + " ");
                        }
                        System.out.println();
                    }                    
                }
                
            }catch(Exception ex){
                System.out.println("ERROR error on parsing config file:\n\t" + ex.getMessage() + "\n\t" + ex.getCause());
                System.exit(1);
            }
        } catch(Exception ex){
            System.err.println("ERROR error on opening file:\n\t" + ex.getMessage());
            System.exit(1);
        }       
        
    }
    

    /**
     * Get the value of gridSize
     *
     * @return the value of gridSize
     */
    public int getGridSize() {
        return gridSize;
    }   
    
    

    /**
     * Get the value of iterations
     *
     * @return the value of iterations
     */
    public int getIterations() {
        return iterations;
    }
    
    
    /**
     * Get the value of matrix
     *
     * @return the value of matrix
     */
    public int[][] getMatrix() {
        return matrix;
    }
    
    

    /**
     * Extract 3 ints from a string
     * @param s
     * @return 
     */
    private int[] getThreeInts(String s){
        
        String fs = "", ss = "", ts = "";
        int idx = 0;
        int sLen = s.length();
        boolean noMoreFlag = false, isDig;
        
        
        while( idx < sLen){
            char c = s.charAt(idx);
            isDig = Character.isDigit(c);
            if (isDig){
                fs += c;
                noMoreFlag = true;
            }
            
            if(!isDig && noMoreFlag){
                noMoreFlag = false;
                break;
            }
            
            // Inc
            idx++;
        }
        
        while( idx < sLen){
            char c = s.charAt(idx);
            isDig = Character.isDigit(c);
            if (isDig){
                ss += c;
                noMoreFlag = true;
            }
            
            if(!isDig && noMoreFlag){
                noMoreFlag = false;
                break;
            }
            
            // Inc
            idx++;
        }
        
        while( idx < sLen){
            char c = s.charAt(idx);
            isDig = Character.isDigit(c);
            if (isDig){
                ts += c;
                noMoreFlag = true;
            }
            
            if(!isDig && noMoreFlag){
                noMoreFlag = false;
                break;
            }
            
            // Inc
            idx++;
        }
        
        int[] threeNumbers = new int[]{ 
            Integer.parseInt(fs),
            Integer.parseInt(ss),
            Integer.parseInt(ts)
        };
        
        return threeNumbers;
    }
}
