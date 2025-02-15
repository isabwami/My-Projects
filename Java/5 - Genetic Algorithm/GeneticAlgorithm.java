import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.File;

public class GeneticAlgorithm {
    
    // main method
    public static void main(String[] args) throws FileNotFoundException {
        Random rng = new Random();

        // create array lists for the items, population, and next generation
        ArrayList<Item> itemsList = readData("items.txt");
        ArrayList<Chromosome> population = initializePopulation(itemsList, 10); // STEP 1
        ArrayList<Chromosome> nextGeneration;
        
        // Genetic Algorithm (steps 2-6)
        int randNum;
        //Chromosome child;
        for (int i=0; i<20; i++) {            
            
            // add the current population to the next generation and perform the crossover
            Collections.shuffle(population);
            nextGeneration = new ArrayList<>(population); // STEP 2

            for (int j=0; j<population.size(); j+=2) {
                nextGeneration.add(new Chromosome(population.get(j).crossover(population.get(j+1)))); // STEP 3
            }

            // randomly choose ten percent of the next generation to mutate
            int numMutated = (int)(Math.round(nextGeneration.size() * 0.1));

            for (int j=0; j<numMutated; j++) {
                randNum = rng.nextInt(numMutated);
                nextGeneration.get(randNum).mutate(); // STEP 4
            }

            // sort nextGeneration
            Collections.sort(nextGeneration); // STEP 5

            // clear current populationadd top ten in next generation to new population
            population.clear();

            for (int j=0; j<10; j++) {
                population.add(nextGeneration.get(j)); // STEP 6
            }
            
        } // STEP 7
        
        // sort the population and print the fittest
        Collections.sort(population);
        System.out.println("The most fit chromosome is:\n\n" + population.get(0)); // STEP 8
    }
    
    // read data method
    public static ArrayList<Item> readData(String filename) throws FileNotFoundException {
        
        // create scanner and arraylist objects
        Scanner fileIn = new Scanner(new File(filename));
        ArrayList<Item> items = new ArrayList<>();
        
        // declare string variable that will store each line in the file (each line corresponds with one item)
        String itemInfo;
        while(fileIn.hasNextLine()) {
            itemInfo = fileIn.nextLine();
            // split the line of text based on the regular expression ", " and store the resulting strings in a string array 
            // 'split()' - https://www.programiz.com/java-programming/library/string/split 
            String[] itemInfoArray = itemInfo.split(", ");

            // declare and initialize the required information for the item's fields based on the results of the split() method 
            String name = itemInfoArray[0];
            double weight = Double.parseDouble(itemInfoArray[1]);
            int value = Integer.parseInt(itemInfoArray[2]);
            
            // create a new item and add it to the list of items
            Item i = new Item(name, weight, value);
            items.add(i);
        }
        
        return items;
    }

    // initialize population method
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {
        // create a new array list that will contain the population
        ArrayList<Chromosome> population = new ArrayList<>();
        
        // create and add 'populationSize' chromosomes to the population array list
        for (int i=0; i<populationSize; i++) {
            Chromosome c = new Chromosome(items);
            population.add(c);
        }
        
        return population;
    }
}