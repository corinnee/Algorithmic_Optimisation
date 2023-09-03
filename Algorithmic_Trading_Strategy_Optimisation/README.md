# Trading Strategies Optimisation with Genetic Algorithms

Welcome to the Trading Strategies Optimization subrepository! This  contains code and files related to an assessment for the 'Computational Intelligence in Business Economics and Finance' module at UKC. The project is for optimising trading strategies based on technical analysis using Genetic Algorithms.

## Table of Contents
- [Project Description](#project-description)
   - [Part A](#part-a-technical-indicators-in-r)
   - [Part B](#part-b-ga)
- [Files and Program Descriptions](#files-and-program-descriptions)
- [Getting Started](#getting-started)
- [Running the R Script](#running-the-r-script)
- [Running the Java Code](#running-the-java-code)

## Project Description

This project involves implementing technical indicators and trading signals to optimize trading strategies for Unilever stock. The goal is to use various technical indicators to generate buy/sell signals and determine the optimal trading actions over a historical period.

The project comprises two parts. Part A involves implementing technical indicators, including Exponential Moving Average (EMA), Trade Break Out Rule (TBR), Volatility (VOL), and Momentum (MOM), using R programming. Part B utilizes a Genetic Algorithm (GA) approach implemented in Java to optimize trading signals based on the technical indicators.

#### Technologies/Tools Used:

- R Programming: Used for Part A, implementing technical indicators and trading signal generation.
- Java: Used for Part B, implementing the Genetic Algorithm optimization approach.
- CSV File Handling: Data is loaded from CSV files containing historical stock data and technical indicator signals.
- Genetic Algorithm: Implemented to optimize trading signal weights for improved performance.

### Part A - Technical Indicators in R

Technical indicators such as EMA, TBR, VOL, and MOM are calculated in R and added to the Unilever stock dataset.
EMA and other indicators are calculated using custom functions.
Trading signals are generated based on conditions derived from the technical indicators.

### Part B - GA

The GA approach aims to optimize trading signal weights using genetic operators.
The population is initialized with randomly generated weight values for different technical indicators.
The fitness function evaluates the profitability of trading based on the generated signals.
Tournament selection is used to select parents for crossover and mutation.
Crossover and mutation operators are applied to create new individuals with potential improved fitness.
Elite individuals are automatically carried over to the next generation.
The algorithm runs through multiple generations, optimizing trading strategies over time

## Files and Program Descriptions

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
   git clone https://github.com/corinnee/Financial_Modelling_and_Optimisation.git
   ```
2. Navigate to the Trading-Strategies-Optimization subdirectory within the cloned repository.
   
4. Explore the source code and files provided to understand the implementation of trading strategy optimization using Genetic Algorithms.

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
