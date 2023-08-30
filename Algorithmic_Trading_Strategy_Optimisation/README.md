# Trading Strategies Optimisation with Genetic Algorithms

Welcome to the Trading Strategies Optimization subrepository! This repository contains code and files related to an assessment for the 'Computational Intelligence in Business Economics and Finance' module at UKC. The project is for optimising trading strategies based on technical analysis using Genetic Algorithms.

## Contents

- `cb830 Optimising Trading Strategies Based on Technical Analysis.pptx`: A PowerPoint presentation explaining the outcomes of the Genetic Algorithm-based trading strategies optimization.

- `GA.java`: Java source code for the Genetic Algorithm implementation. This code defines classes and methods to perform the optimisation process for trading strategies.

- `kValues.txt`: A text file containing the k values for trading strategies.

- `Main.java`: Java source code for the main program. This code initiates the Genetic Algorithm optimization process.

- `partA_technical_indicators.R`: R script for calculating technical indicators and generating trading signals.

- `plots.R`: R script for creating plots based on the trading strategy results.

- `Unilever.csv`: A CSV file containing a table of trading-related data, including Daily Closing Price, Exponential Moving Averages, Trade Break Out Rule, Volatility, Momentum, and trading action signals.

- `unilever_orignial.csv`: A CSV file containing the original daily closing price data.

## Getting Started

1. Clone this repository to your local machine using the following command:

   ```bash
   git clone https://github.com/your-username/Trading-Strategies-Optimization.git
   ```
2. Navigate to the cloned repository:
  ```bash
  cd Trading-Strategies-Optimization
  ```
3. Explore the source code and files provided to understand the implementation of trading strategy optimization using Genetic Algorithms.

## Running the R Script

The R script `partA_technical_indicators.R` is used to calculate technical indicators and generate trading signals based on the data in the `Unilever.csv` file. Here's how you can run the R script:

1. Make sure you have R installed on your computer. You can download and install R from the [R Project website](https://www.r-project.org/).

2. Open a terminal or command prompt.

3. Navigate to the directory where your repository is located. Use the `cd` command to navigate to the repository's directory.

4. Run the R script using the following command:

   ```bash
   Rscript partA_technical_indicators.R
   ```

5. The script will process the data in `Unilever.csv`, calculate technical indicators, and generate trading signals based on the implemented strategies. The resulting data will be saved to the same file, `Unilever.csv`, with updated columns for trading action signals.

## Running the Java Code
The Java code in `GA.java` and `Main.java` is used to implement a Genetic Algorithm for optimizing trading strategies. Here's how you can run the Java code:

1. Make sure you have Java JDK (Java Development Kit) installed on your computer. You can download and install the latest Java JDK from the [Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or use a distribution like OpenJDK.

2. Open a terminal or command prompt.

3. Navigate to the directory where your repository is located. Use the `cd` command to navigate to the repository's directory.

4. Compile the Java code. Run the following command to compile the `GA.java` file:

   ```bash
   javac GA.java
   ```

5. Run the main program. Execute the following command to run the main program defined in the `Main.java` file:

   ```bash
   java Main
   ```

6. The Java program will start executing the Genetic Algorithm-based optimization process for trading strategies. The program will provide output related to the optimization process, and you should see the results printed in the terminal.
