/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     *
     * @param gf
     */
    public ParallelZombieGameOfLife(GameConfig gf) {
        m = gf.getMatrix();
        iterations = gf.getIterations();
        grid = gf.getGridSize();
    }

    /**
     * Apply rules to m[i][j] based on neighbors
     *
     * @param i
     * @param j
     * @param neighbors
     */
    private synchronized void applyRules(int i, int j, int[] neighbors) {

        // 1º - for living cells        
        if (m[i][j] == 1) {

            int aliveNeibors = 0;
            boolean zombieNeibor = false;

            for (int neighbor : neighbors) {
                // Counting alive neibors
                if (neighbor == 1) {
                    aliveNeibors++;
                }

                // zome neibor is zombieee, turn zombie
                if (neighbor == 2) {
                    zombieNeibor = true;
                    break;
                }
            }

            // 70M813 RULEZ FIRST
            // zome neibor is zombieee, turn zombie
            // 5ª rule
            if (zombieNeibor) {
                m[i][j] = 2;
            } else {
                // 8 alive neibors, turn zombie
                // 6ª rule
                if (aliveNeibors == 8) {
                    m[i][j] = 2;
                } // Less than 2 alive neibors, death by lonelliness
                // 1ª rule
                else if (aliveNeibors < 2) {
                    m[i][j] = 0;
                } // More than 3 neibors, death by overpopulation
                // 2ª rule
                else if (aliveNeibors > 3) {
                    m[i][j] = 0;
                }
                // 2 or 3 neibors, same state. Not needed
                // 4ª rule
                // else if (aliveNeibors == 2 || aliveNeibors == 3) cel = cel;
            }
        } // 2º for zombie cells
        else if (m[i][j] == 2) {
            int aliveNeibors = 0;
            for (int neighbor : neighbors) {
                // counting alive neibors
                if (neighbor == 1) {
                    aliveNeibors++;
                }
            }
            // no alive neibors, zombie dies
            // 7ª rule
            if (aliveNeibors == 0) {
                m[i][j] = 0;
            }
            // zome alive neibor makes cell keeps zombie
            // if some neibor is zombie, keeps zombie? TODO
            // 8ª rule
            // if (aliveNeibors > 0) cel = cel;
        } else if (m[i][j] == 0) {
            int aliveNeibors = 0;
            for (int neighbor : neighbors) {
                if (neighbor == 1) {
                    aliveNeibors++;
                }
            }

            // some dead cell with 3 alive neibors, turns ALIVE!!!
            // 3ª rule
            if (aliveNeibors == 3) {
                m[i][j] = 1;
            }
        }
    }

    /**
     * Runs iteration neighbors getter
     *
     * @param initI
     * @param finalI
     * @param neighborhood
     */
    private void runIterationParallel(int initI, int finalI, int[][][] neighborhood) {

        for (int i = initI; i < finalI; i++) {
            for (int j = 0; j < grid; j++) {

                //general case
                if (i > 0 && i < (grid - 1) && j > 0 && j < (grid - 1)) {
                    neighborhood[i][j] = new int[]{
                        m[i - 1][j - 1], m[i - 1][j], m[i - 1][j + 1],
                        m[i][j - 1], /* You are here */ m[i][j + 1],
                        m[i + 1][j - 1], m[i + 1][j], m[i + 1][j + 1]
                    };
                    /*
                     * N N N
                     * N Y N
                     * N N N
                     */
                } // first line first collum
                else if (i == 0 && j == 0) {
                    neighborhood[i][j] = new int[]{
                        m[i][j + 1], m[i + 1][j], m[i + 1][j + 1]
                    };

                    /*
                     * Y N
                     * N N
                     */
                } //last line, first colunm
                else if (i == (grid - 1) && j == 0) {
                    neighborhood[i][j] = new int[]{
                        m[i - 1][j], m[i - 1][j + 1], m[i][j + 1]
                    };
                    /*
                     * N N
                     * Y N
                     */
                } //first line, last colunm
                else if (i == 0 && j == (grid - 1)) {
                    neighborhood[i][j] = new int[]{
                        m[i][j - 1], m[i + 1][j - 1], m[i + 1][j]
                    };
                    /*
                     * N Y
                     * N N
                     */
                } //last line, last colunm
                else if (i == (grid - 1) && j == (grid - 1)) {
                    neighborhood[i][j] = new int[]{
                        m[i - 1][j - 1], m[i - 1][j], m[i][j - 1]
                    };
                    /*
                     * N N
                     * N Y
                     */
                } // first line, not first or last colunm
                else if (i == 0 && j > 0 && j < (grid - 1)) {
                    neighborhood[i][j] = new int[]{
                        m[i][j - 1], m[i][j + 1],
                        m[i + 1][j - 1], m[i + 1][j], m[i + 1][j + 1]
                    };
                    /*
                     * N Y N
                     * N N N
                     */
                } // last line, not first or last colunm
                else if (i == (grid - 1) && j > 0 && j < (grid - 1)) {
                    neighborhood[i][j] = new int[]{
                        m[i - 1][j - 1], m[i - 1][j], m[i - 1][j + 1],
                        m[i][j - 1], m[i][j + 1]
                    };
                    /*
                     * N N N
                     * N Y N
                     */
                } // first colunm, not first or last line
                else if (j == 0 && i > 0 && i < (grid - 1)) {
                    neighborhood[i][j] = new int[]{
                        m[i - 1][j], m[i - 1][j + 1],
                        m[i][j + 1],
                        m[i + 1][j], m[i + 1][j]
                    };
                    /*
                     * N N
                     * Y N
                     * N N
                     */
                } // last colunm, not first or last line
                else if (j == (grid - 1) && i > 0 && i < (grid - 1)) {
                    neighborhood[i][j] = new int[]{
                        m[i - 1][j - 1], m[i - 1][j],
                        m[i][j - 1],
                        m[i + 1][j - 1], m[i + 1][j]
                    };
                    /*
                     * N N
                     * N Y
                     * N N
                     */
                }

            } // j
        } //i
    }

    
    /**
     * Start game running on multithreaded.
     */
    public void StartGame() {
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of Processors: " + processors);
        
        
        int[][][] neighborhood = new int[grid][grid][8];
        for (int currentIteration = 0; currentIteration < iterations; currentIteration++) {
            

            // Selection algo
            int value = grid / processors;
            Thread[] threads = new Thread[processors];
            ParallelZombieGameOfLife pzgl = this;
            
            // Create threads and pupulate the neiborhood with they
            for (int i = 0; i < processors; i++) {
                // Calculate next displacement on matrix
                int initI = value * i;
                int finalI = value * (i + 1);
                
                threads[i] = new Thread(new Runnable() {
                    
                    public void run() {
                        pzgl.runIterationParallel(initI, finalI, neighborhood);
                    }
                });
            
                threads[i].start();
            }
            
            // join for selection
            for(Thread t: threads){
                try{
                    t.join();
                }
                catch(Exception ex){
                    System.out.println("ERROR on thread join: " + ex.getMessage());
                }
                
            }

            // Clear threads vetor
            
            // Apply rules for each i,j with created threads
            // Mult apply rules
            for (int i = 0; i < grid; i++) {
                for (int j = 0; j < grid; j++) {
                    int neighborsCount = neighborhood[i][j].length;
                    System.out.printf("Person %d, %d -> ", i, j);
                    for (int k = 0; k < neighborsCount; k++) {
                       System.out.print(neighborhood[i][j][k] + " ");
                    }
                    System.out.println();
                }
            }
        }
    }


}
