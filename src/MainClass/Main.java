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
        
        
        
        if(false){
            GameConfig gf = new GameConfig(args);

            ParallelZombieGameOfLife pzgl = new ParallelZombieGameOfLife(gf);
            pzgl.StartGame();
        }
    }
    
}
