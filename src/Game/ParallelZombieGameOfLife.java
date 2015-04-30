/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

/**
 *
 * @author gilberto
 */
public class ParallelZombieGameOfLife {
    
    private final int iterations;
    private final int grid;
    private final int[][] m;
    
    /**
     * Constructor of the class
     * @param gf
     */
    public ParallelZombieGameOfLife(GameConfig gf){
        m = gf.getMatrix();
        iterations = gf.getIterations();
        grid = gf.getGridSize();
    }
    
    /**
     * Apply rules to m[i][j] based on neighbors
     * @param i
     * @param j
     * @param neighbors 
     */
    private synchronized void applyRules(int i, int j, int[] neighbors){
        
    }
    
    
}
