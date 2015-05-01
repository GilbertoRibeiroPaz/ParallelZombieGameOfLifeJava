/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import Game.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilberto
 */
public class AutoTester {

    private final String configsPath;
    private final String outputPath;
    private final int noOfTests;
    private final int noOfThreads;
    private HashMap<Integer, String> tests;

    /**
     *
     * @param configsPath Defaults path to save all generated config files
     * @param outputsPath
     * @param noOfTests Number of tests to considerate
     * @param noOfThreads
     */
    public AutoTester(String configsPath, String outputsPath, int noOfTests, int noOfThreads) {
        tests = new HashMap<>();
        this.configsPath = configsPath;
        this.outputPath = outputsPath;
        this.noOfTests = noOfTests;
        this.noOfThreads = noOfThreads;
        CreateConfigFiles();
    }

    /**
     * Create the configuration files for test
     */
    private void CreateConfigFiles() {

        for (int i = 0; i < noOfTests; i++) {

            // Save current path and generated size
            int genSize = i + 2; // Minimum size = 2
            String curPath = configsPath + "Config" + genSize + ".txt";

            // Size to path
            tests.put(genSize, curPath);

            // Generate grid and save the file
            GridGenerator.SavePopToEntryPattern(curPath,
                    GridGenerator.GenRandomPopulation(genSize)
            );
        }
    }

    public void Maketests() {

        // For each test, make tests with differents number of iterations
        for (int i = 0; i < noOfTests; i++) {

            // For each iteration parse args and set GameConfig
            for (int iter = 2; iter < 11; iter++) {

                for (int ths = 1; ths <= noOfThreads; ths++) {

                    int genSize = i + 2;
                    String size = Integer.toString(genSize);
                    String iterations = Integer.toString(iter);
                    String inputFile = tests.get(genSize);
                    String outFile = outputPath + "ResultFor" + genSize + "Threads" + ths + "Iteration" + iter + ".txt";
                    String[] args = new String[]{
                        size , iterations, inputFile, outFile                        
                    };

                    GameConfig gf = new GameConfig(args);
                    
                    System.out.println("Input file: " + inputFile);
                    int[][] m = gf.getMatrix();
                    for(int nl = 0; nl < genSize; nl++){
                        for(int nc = 0; nc < genSize; nc++){
                            System.out.print(m[nl][nc] + " ");
                        }    
                        System.out.println();
                    }
                    

                    ParallelZombieGameOfLife pzglcase = new ParallelZombieGameOfLife(gf, ths);
                    pzglcase.StartGame();
                    pzglcase.saveResults();
                }
            }

        }
    }

}
