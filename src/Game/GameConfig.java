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
     *  Constructor of GameConfig
     * @param configFile 
     */
    public GameConfig(String configFile) {
        try{
            // Try to get file
            FileReader fr = new FileReader(configFile);            
            BufferedReader br = new BufferedReader(fr);
            try{
                // Try reading the file
                String line = br.readLine();
                while(line != null){
                    // Test on reading lines
                    System.out.println(line);
                    line = br.readLine();
                }
            } finally{
                br.close();
                System.out.println("File " + configFile + " closed.");
            }
        } catch(Exception ex){
            System.err.println("ERROR: " + ex.getMessage());
        }
        
        
        
    }
    
    
    

    /**
     * Get the value of iterations
     *
     * @return the value of iterations
     */
    public int getIterations() {
        return iterations;
    }

}
