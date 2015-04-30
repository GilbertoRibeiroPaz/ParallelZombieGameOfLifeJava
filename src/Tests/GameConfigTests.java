/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import Game.*;
/**
 *
 * @author gilberto
 */
public class GameConfigTests {
    public static void main(String[] args){
        // Test acquiring parameters
        GameConfig gft = new GameConfig(args);
        String cf = "";
        cf = "Grid Size: " + gft.getGridSize();
        cf = "\nIterations: " + gft.getIterations();
        for(int i = 0; i < gft.getGridSize(); i++){
            String lineToPrint = "";
            for(int j = 0; j < gft.getGridSize(); j++){
                lineToPrint += gft.getMatrix()[i][j] + " ";
            }
            System.out.println(lineToPrint);
        }
    }
}
