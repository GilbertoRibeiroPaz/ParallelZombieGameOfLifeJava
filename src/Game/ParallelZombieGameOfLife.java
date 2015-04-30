/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.lang.System.

/**
 *
 * @author gilberto
 */
public class ParallelZombieGameOfLife {

    private int iterations;
    private int grid;
    private int[][] m;
    private int[][] original;
    private int customProcessorsNumber;
    private boolean useCustomProcessors;
    private long timeSpent;
    private boolean Debug;

    /**
     * 
     * @return time spent to compute
     */
    public long getTimeSpent() {
        return timeSpent;
    }

    
    
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
     * Constructor of the class with a custom number of threads
     *
     * @param gf
     * @param customProcessorsNumber
     */
    public ParallelZombieGameOfLife(GameConfig gf, int customProcessorsNumber) {
        m = gf.getMatrix();
        original = m.clone();
        iterations = gf.getIterations();
        grid = gf.getGridSize();
        this.customProcessorsNumber = customProcessorsNumber;
        useCustomProcessors = true;
    }

    /**
     * Constructor of the class
     *
     * @param gf
     */
    public ParallelZombieGameOfLife(GameConfig gf) {
        m = gf.getMatrix();
        original = m.clone();
        iterations = gf.getIterations();
        grid = gf.getGridSize();
        useCustomProcessors = false;
    }

    /**
     * Apply rules to m[i][j] based on neighbors
     *
     * @param i
     * @param j
     * @param neighbors
     */
    private void applyRules(int i, int j, int[] neighbors) {

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

        // Prints thread name on debug mode
        if (Debug) 
        {
            System.out.println("ThreadID = " + Thread.currentThread().getName());
        }

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
     * Prints the original matrix
     */
    public void printOriginalMatrix(){
        for(int i = 0; i < grid; i++){
            for(int j = 0; j < grid; j++){
                System.out.println(original[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    

    /**
     * Start game running on multithreaded.
     */
    public void StartGame(){
        // If got a custom number of simulating processors use it
        int processors = useCustomProcessors ? customProcessorsNumber : Runtime.getRuntime().availableProcessors();

        //Prints number of processors on debug mode
        if (Debug) {
            System.out.println("Number of Processors: " + processors);
        }

        //Prints first matrix
        //System.out.println("Original matrix:");
        //printMatrix();
        //System.out.println();
        
        // Get init time
        long initTime = Calendar.getInstance().getTimeInMillis();
        
        // Split threads on lines of the matrix based on machine processors
        int value = grid / processors;

        // thread vector
        Thread[] threads = new Thread[processors];

        // Create a reference to this object to use inside the Runneble object
        ParallelZombieGameOfLife pzgl = this;

        for (int currentIteration = 0; currentIteration < iterations; currentIteration++) {

            // Create neighbor hood
            int[][][] neighborhood = new int[grid][grid][8];

            // Create threads and pupulate the neiborhood with they
            for (int i = 0; i < processors; i++) {
                // Calculate next displacement on matrix
                int initI = value * i;
                // For final that is not the last matrix index
                int tmpFinalI = value * (i + 1);
                int finalI = (i == (processors - 1) && tmpFinalI < grid) ? 
                                tmpFinalI + (grid - tmpFinalI) : tmpFinalI;


                // Try to create threads
                try {
                    
                    threads[i] = new Thread(new Runnable() {
                        public void run() {
                            pzgl.runIterationParallel(initI, finalI, neighborhood);
                        }
                    });

                    threads[i].setName(initI + " to " + finalI);
                    threads[i].start();
                    
                } catch (Exception ex) {
                    System.err.println("ERROR on creating threads:\n\t" + ex.getMessage());
                    System.exit(1);
                }

            }

            
            // join for selection            
            for (Thread t : threads) {
                try {
                    t.join();
                } catch (Exception ex) {
                    System.out.println("ERROR on thread join:\n\t" + ex.getMessage());
                    System.exit(1);
                }
            }
            
            // Apply rules single thread
            for (int i = 0; i < grid; i++) {
                for (int j = 0; j < grid; j++) {
                    applyRules(i, j, neighborhood[i][j]);
                }
            }
            
            /*
            // Apply rules for each i,j with created threads
            for (int idx = 0; idx < processors; idx++) {
                // Calculate next displacement on matrix
                int initI = value * idx;
                // For final that is not the last matrix index
                int tmpFinalI = value * (idx + 1);
                int finalI = (idx == (processors - 1) && tmpFinalI < grid) ? 
                                tmpFinalI + (grid - tmpFinalI) : tmpFinalI;

                try {
                    threads[idx] = new Thread(new Runnable() {
                        
                        public void run() {
                            // apply rules threaded
                            for (int i = initI; i < finalI; i++) {
                                for (int j = 0; j < grid; j++) {
                                    applyRules(i, j, neighborhood[i][j]);
                                }
                            }
                        }
                    });
                    
                } catch (Exception ex) {
                    System.err.println("ERROR on creating threads to apply rules:\n\t" + ex.getMessage());
                    System.exit(1);
                }

            }

            
            // Join threads for applyed rules            
            for (Thread t : threads) {
                try {
                    t.join();
                } catch (Exception ex) {
                    System.out.println("ERROR on thread join after applying rules:\n\t" + ex.getMessage());
                    System.exit(1);
                }
            }
            */
            
            // @ end of each iteration prints the matrix
            if(Debug){
                System.out.println("Iteration: " + (currentIteration + 1));
                printResultantMatrix();
                System.out.println();
            }
            

            // Mult apply rules
            if (Debug) {
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
        
        // get end time of operation
        long endTime = Calendar.getInstance().getTimeInMillis();
        
        // calculate spent time to perform the operations
        this.timeSpent = endTime - initTime;
        
        
        //Prints first matrix
        //System.out.println("Final matrix:");
        //printMatrix();
        System.out.println();
        
    }

    /**
     * Prints matrix m
     */
    public void printResultantMatrix() {
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

}
