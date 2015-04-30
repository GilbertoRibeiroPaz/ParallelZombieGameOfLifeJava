/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author gilberto
 */
public class GridGenerator {
    
    static public int[][] GenRandomPopulation(int size){
        int[][] popul = new int[size][size];
        Random rd = new Random();
        Date dt = new Date();
        rd.setSeed(dt.getTime());
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){                       
                popul[i][j] = rd.nextInt(3);
            }
            
        }
        
        return popul;
    }
    
    static public void SavePopToEntryPattern(String filePath, int[][] grid){
        // Try to get file writer
        try(FileWriter fw = new FileWriter(filePath)){
            
            // Try to get buffer
            try(BufferedWriter bw = new BufferedWriter(fw)){
                
                // Write coordinates to buffered file
                for(int i = 0; i < grid.length; i++){
                    for(int j = 0; j < grid.length; j++){
                        bw.write(i + "," + j + " " + grid[i][j]);
                        if (i < grid.length - 1 || j < grid.length - 1){
                            bw.write("\n");
                        }
                    }
                }
            }
            catch(Exception ex){
                System.err.printf("ERROR getting buffered file: " + ex.getMessage());
                System.exit(1);
            }
        }
        catch(Exception ex){
            System.out.println("ERROR getting file:\n\t" + ex.getMessage());
            System.exit(1);
        }
    }
}
