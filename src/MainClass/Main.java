/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainClass;

import Game.GameConfig;
import Game.GridGenerator;
import Game.ParallelZombieGameOfLife;
import java.lang.*;
/**
 *
 * @author gilberto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if ( args.length >= 1 && args[0].equals("CREATEGRID")){
            int[][] grid = GridGenerator.GenRandomPopulation(100);
            GridGenerator.SavePopToEntryPattern("../res/TestsConfigs/Config100.txt", grid);
            grid = GridGenerator.GenRandomPopulation(9);
            GridGenerator.SavePopToEntryPattern("../res/TestsConfigs/Config9.txt", grid);
            grid = GridGenerator.GenRandomPopulation(10);
            GridGenerator.SavePopToEntryPattern("../res/TestsConfigs/Config10.txt", grid);
            grid = GridGenerator.GenRandomPopulation(40);
            GridGenerator.SavePopToEntryPattern("../res/TestsConfigs/Config40.txt", grid);
            grid = GridGenerator.GenRandomPopulation(33);
            GridGenerator.SavePopToEntryPattern("../res/TestsConfigs/Config33.txt", grid);
            // Custom
            grid = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 2, 2, 2, 2, 0, 0},
                {0, 0, 0, 1, 0, 1, 2, 0, 0}
            };
            
            GridGenerator.SavePopToEntryPattern("../res/TestsConfigs/ConfigCustom9.txt", grid);
        }
        else{
            GameConfig gf = new GameConfig(args);

            ParallelZombieGameOfLife pzgl = new ParallelZombieGameOfLife(gf);
            if (args.length == 5 && args[4].equals("DEBUG") )
                pzgl.setDebug(true);
            pzgl.StartGame();
        }
    }
    
}
