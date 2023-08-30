
import java.util.Random;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

 

/**
 * Write a description of class GA here.
 *
 * @author Corinne Batho-Newton (cb830)
 * @version 15/12/2021
 */
public class GA
{
    /**
     * Random number generation.
     */
    private Random random = new Random();

    /**
     * Pool Size
     */
    private static final int POP_SIZE = 100;
    
    /**
     * Max Generations
     */
    private static final int MAX_GEN = 300;
    
    /**
     * The Current Population
     */
    private double[][] population = new double[POP_SIZE][];
    
    /**
     * Fitness values of each individual in pool
     */
    private double[] fitness = new double[POP_SIZE];
    
    /**
     * K is the value for the competition size in tournament seletion.
     */
    private static final int K = 6;
    
    /**
     * Max random amount traded
     */
    //private static final int MAX_RAND_2_TRADE = 5000;
    
    /**
     * Elite Count is the number of Elite values with top fitness, that 
     * automatically go through to the next generation.
     * Currently set to 3% of the population size.
     */
    private static final int ELITE_COUNT = 0;
    
     /**
     * Probability of mutation
     */
    private static final double MUTATION_PROB = 0.1;
    
    /**
     * Probability of crossover
     */
    private static final double CROSSOVER_PROB = 0.9;


    /**
     * Run GA.
     */
    public void main(String filename)throws IOException
    {
        load(filename);
        
        initalise();
        
        evaluate();
        
        /**
         * Termination criteria [5%]
         * 
         * When max generations is hit, the evolutionary process ends.
         */
        
        for (int i = 0; i < MAX_GEN; i++){
            // New population to be generated
            double [][] newPop = new double[POP_SIZE][population[1].length];
            // index of current individual to be generated.
            int current = 0;

            // Select elite individuals            
            int[] elite = findElite();
            
            for (int j = 0; j < ELITE_COUNT; j++){
                newPop[current] = population[elite[j]];
                current ++;
            }
            
            while (current < POP_SIZE){
                double prob = random.nextDouble();
                
                // Should mutation happen? if there is only space for 1 
                // more individual, mutation must be performed.
                if (prob < MUTATION_PROB || (POP_SIZE - current) == 1)
                {
                    double[] offspring = mutation(select());
                    newPop[current] = offspring;
                    current += 1;
                }
                else{
                    int A = select();
                    int B = select();
                    
                    double[][] offspring = crossover(A, B);
                    newPop[current] = offspring[0];
                    current += 1;
                    newPop[current] = offspring[1];
                    current += 1;
                }
                
            }
            
            population = newPop;
            
            // Evaluate new population
            evaluate();
            
            //printFittest(findFittest());
        }
        printFittest(findFittest());
    }

    /**
     * Individual Representation [10%]
     * - Initalise the population.
     * 
     * For correct representation, one weight value between 0 and 1 per 
     * trading signal
     * The population should be randomly initialized with values between
     * [0, 1], and the final gene should hold the max amount to be traded.
     * 
     */

    public void initalise()
    {
        // initalise individuals
        for (int i = 0; i < POP_SIZE; i++){
            population[i] = new double [4];
            for (int j = 0; j < population[i].length; j++){
                population[i][j] = random.nextDouble();
            }
            //population[i][4] = random.nextInt(MAX_RAND_2_TRADE);
        }
    }
    
    /**
     * Fitness Function [15%]
     * 
     * For correct evaluation of candidate solutions. The fitness 
     * function should operate with an initial budget of £3000 and stock 
     * amount of 0. For each value on the training data, it should 
     * generate a trading signal.
     * 
     * - Every BUY action should deduct the amount from the budget and 
     *   only be performed if there is sufficient budget, and increase 
     *   the stock amount
     *   
     * - Every SELL action should deduct the stock amount and only be 
     *   performed if there is sufficient stocks, and increase the budget
     *   accordingly.
     *   
     * - HOLD actions have no effect.
     * 
     * The fitness of a solution is the total cash balance at the end of
     * trading (budget + stock), where the stocks should be converted to
     * cash using the last value of the training period;
     */
    private void evaluate()
    {
        for (int i = 0; i < POP_SIZE; i++){
            fitness[i] = Math.floor(trade(population[i])*100)/100;
            //System.out.println("Fitness ["+i+"] = "+ fitness[i]);
        }
    }
    
    /**
     * Selection Method [5%]
     * 
     * The GA should use tournament selection.
     * 
     * @return the index of the selected parent using tournament selection.
     */
    private int select()
    {
        int tournament[] = new int[K];
        int parent = -1;
        double max = Integer.MIN_VALUE;
        
        // select random IDs for tournament.
        for (int i = 0; i < K; i++){
            tournament[i] = random.nextInt(POP_SIZE);
        }
        
        // Find population index of individual in tournament with max 
        // fitness
        for(int i = 0; i < K; i ++){
            int ID = tournament[i];
            if(max < fitness[ID]){
                max = fitness[ID];
                parent = tournament[i];
            }
            else if (max == fitness[ID]){
                if(parent == -1){
                    max = fitness[ID];
                    parent = tournament[i];
                }
                else{
                    boolean z = random.nextBoolean();
                    if (z == true){
                        max = fitness[ID];
                        parent = tournament[i];
                    }
                }                
            }
        }
        
        if (parent < 0){
            parent = 0;
        }
        return parent;
    }
    
    /**
     * Genetic Operators [5%]
     * 
     * The GA should use one mutation and one crossover operator of your 
     * choice. The mutation operator should only generate new real values
     * between 0 and 1;
     *
     * Mutation : Swap Mutation.
     *            Two positions are randomly chosen and values are swapped.
     */
    private double[] mutation(int index)
    {
        double[] parent = population[index];
        double[] offspring = parent.clone();
        // Select random Points to Scramble
        int a = random.nextInt(4);
        int b = random.nextInt(4);
        
        // Scramble
        offspring[a] = parent[b];
        offspring[b] = parent[a];
        
        // 50% chance of amount to be traded to be mutated
        // boolean mutate = random.nextBoolean();
        // if (mutate == true){
        //     offspring[4] = random.nextInt(MAX_RAND_2_TRADE); 
        // }
        return offspring;
    }
    /**
     * Crossover : Uniform Crossover.
     *             50/50 chance of each gene being flipped.
     */
    private double[][] crossover(int indexA, int indexB)
    {
        double[] parentA = population[indexA];
        double[] parentB = population[indexB];
        double[][] offspring = new double[2][population[indexA].length];
        // crossover method
        for (int i = 0; i < population[indexA].length; i++){
            double flip = random.nextDouble();
            if (flip <=  CROSSOVER_PROB){
                offspring[0][i] = parentB[i];
                offspring[1][i] = parentA[i];
            }
            else{
                offspring[0][i] = parentA[i];
                offspring[1][i] = parentB[i];
            }
        }
        return offspring;
    }

    /**
     * Elite individuals
     * 
     * @return the best n indivials in the population.
     */
    private int[] findElite(){
        int[] elite = new int[ELITE_COUNT];
        double[] current = (double[]) fitness.clone();

        for (int y = 0; y<ELITE_COUNT; y++){
            int best = 0;
            for (int z=1; z < POP_SIZE; z++){
                if(current[best] < current[z]){
                    best = z;
                }
            }

            elite[y] = best;
            current[best] = 0;
        }
        return elite;
    }
    
    /**
     * Fittest Individual
     * 
     * @return the index of the best individual.
     */
    private int findFittest()
    {
        int best = 0;
        
        for (int i=1; i < POP_SIZE; i++){
            if (fitness[best] > fitness[i]){
                best = i;
            }
        }
        return best;
    }
    
    /**
     * Print fittest
     * 
     * Prints the current fittest individual
     */
    private void printFittest(int best){
        System.out.println("");
        System.out.println("Best Individual (index "+best+")");
        System.out.println("Total Cash balance : "+fitness[best]);
        System.out.println("Technical Indicator Weights:");
        System.out.println("EMA Action["+population[best][0]+
                           "], TBR Action["+population[best][1]+
                           "], VOL Action["+population[best][2]+
                           "], MOM Action["+population[best][3]+"]");
        System.out.println("");
    }
    
    //-------------------------------------------------------------------//
    //  Trader simulator                                                 //
    //-------------------------------------------------------------------//  
    /**
     * csv file holds the following technical indicators...
     * 
     *  0: Daily Closing Price
     *  1: 12-Day Exponential Moving Average
     *  2: 26-Day Exponential Moving Average
     *  3: 24-Day Trade Break Out Rule
     *  4: 29-Day Volatility
     *  5: 25-day Momentum
     *  6: EMA Action
     *  7: TBR Action
     *  8: VOL Action
     *  9: MOM Action
     * 
     * Where the numbers within action indicate...
     *  1 = buy
     *  2 = sell
     *  0 = hold
     */
    private double[][] data = new double[0][0];
    
    /**
     * Initial budget.
     */
    private double budget = 3000;
    
    /**
     * Initial stock.
     */
    private int stock = 0;
    
    /**
     * Load csv file in memory and skip column names line.
     * 
     * @param file name
     */
    
    public void load(String filename) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArrayList<double[]> lines = new ArrayList<>();
        String line = null;
        reader.readLine(); // skips row 1 (col headers)
        
        while((line = reader.readLine()) != null){
            String[] split = line.split(",");
            
            double[] values = new double[split.length];
            
            for (int i = 0; i < values.length; i++){
                values[i] = "N/A".equals(split[i])
                                 ? Double.NaN
                                 : Double.valueOf(split[i]);
            }
            lines.add(values);
        }
        data = lines.toArray(data);
    }

    /**
     * Simulate Trading Session
     * 
     * @return final cash balance.
     */
    public double trade(double[] weights){
        //int amountTraded = 0;
        int i = 0;
        while (i < data.length){
            //----------------------------------------------------------//
            //        Calculate Strength of each trading signal.        //
            //----------------------------------------------------------//
            double[] signalStrengths = new double[3];
            double max = Integer.MIN_VALUE;
            int index = -1;
            
            // Find weights of each signal
            for(int j = 0; j < population[j].length-1; j++){
                if(data[i][6+j] != Double.NaN && data[i][6+j] == 1){
                    signalStrengths[1] += weights[j];
                }
                else if(data[i][6+j] != Double.NaN && data[i][6+j] == 2){
                    signalStrengths[2] += weights[j];
                }
                else if(data[i][6+j] != Double.NaN && data[i][6+j] == 0){
                    signalStrengths[0] += weights[j];
                }               
                // System.out.println("signal id: "+j);
                // System.out.println("Action Stored: "+data[i][6+j]);
                // System.out.println("current weights (hold,buy,sell)"+
                                    // signalStrengths[0]+","+
                                    // signalStrengths[1]+","
                                    // +signalStrengths[2]);
            }
            
            // Return index of max signal strength.
            for(int j = 0; j < signalStrengths.length; j ++){
                if(max < signalStrengths[j]){
                    max = signalStrengths[j];
                    index = j;
                }
            }
            
            if(index == 1){
                int amount = (int)(budget / data[i][0]);
                //amountTraded ++;
                stock += amount;
                budget -= amount * data[i][0];
            }
            else{
                //amountTraded ++;
                budget += stock * data[i][0];
                stock = 0;
            }
            //System.out.println("Amount Traded: "+amountTraded);
            i++;
        }
        
        // System.out.printf("Total Cash: "+
        // budget + (stock * data[data.length-1][0]));
        
        // return total cash after trading session, with stock sold
        // at last known closing price.
        return budget + (stock * data[data.length-1][0]);
    }
    
}
