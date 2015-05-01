/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainClass;

import Game.GameConfig;
import Game.ParallelZombieGameOfLife;
import Tests.AutoTester;
/**
 *
 * @author gilberto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length > 0 && args[0].equals("MAKETEST")){
            
            // Get entries to make the automated test
            if (args.length == 5){
                System.out.println("Test mode");
                String inputPath = args[1];
                String outputPath = args[2];
                int noOfTests = Integer.parseInt(args[3]);
                int noOfThreads = Integer.parseInt(args[4]);
                AutoTester at = new AutoTester(inputPath, outputPath, noOfTests , noOfThreads);
                at.Maketests();
            }
            else {
                printTestMan();
            }
        } else {
            GameConfig gf = new GameConfig(args);
            ParallelZombieGameOfLife pzgl = new ParallelZombieGameOfLife(gf);
            pzgl.setPrintIterations(true);
            pzgl.StartGame();
            pzgl.saveResults();
        }
        
    }
    
    private static void printTestMan(){
        String man = "Arguments minimun lenght is 5\n";
        man += "Arguments: MAKETEST <Input path> <Output path> <Number of Tests> <Máximum number of threads to test>";
        System.out.println(man);
    }
    
}
