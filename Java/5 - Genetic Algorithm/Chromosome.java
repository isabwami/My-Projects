import java.util.Random;
import java.util.ArrayList;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome>{

    private static Random rng;
    
    // No argument constructor
    public Chromosome() { }

    // Primary Constructor
    public Chromosome(ArrayList<Item> items) {
        // declare random number object
        rng = new Random();
        int randNum;
        
        // generate a random number and change each item's included value based on the result 
        for (Item i : items) {
            i = new Item(i);
            this.add(i);
            
            randNum = rng.nextInt(9) + 1; // generate random number between 1 and 10

            if (randNum <= 5) {
                i.setIncluded(true); // if less than 6 set true
            }
            else {
                i.setIncluded(false); // if greater than 5 set false
            }
        }
    }

    // crossover method
    public Chromosome crossover(Chromosome other) {
        // create new child and pass in the other chromosome since it extends ArrayList<Item>
        Chromosome child = new Chromosome(other);        
        int randNum; // random number 
        int n;  // iterator

        // generate random number and determine which included value the child recieves for each item depending on the result
        for (Item i : child) {
            n = 0;
            randNum = rng.nextInt(9) + 1; // generate random number between 1 and 10
            
            if (randNum <= 5) {
                i.setIncluded(this.get(n).isIncluded()); // if less than 6 use parent 1's included value
            }
            else {
                i.setIncluded(other.get(n).isIncluded()); // if greater than 5 use parent 2's included value
            }
            n++;
        }

        return child;
    }

    // mutate method
    public void mutate() {
        int randNum; // random number

        // generate a random number and determine whether the chromosome will be mutated based on the result
        for (Item i : this) {
            randNum = rng.nextInt(9) + 1; // generate random number between 1 and 10
            if (randNum == 1) { 
                i.setIncluded(!i.isIncluded()); // if 1 mutate the current item 
            } 
        }
    }
    
    // get fitness method
    public int getFitness() {
        double totalWeight = 0; // sum of all item weights 
        int totalValue = 0; // sum of all item values
        int fitness; // fitness value

        // if the item is included, add its weight and value to the total weight and total value respectively
        for (Item i : this) {
            if (i.isIncluded()) {
                totalWeight += i.getWEIGHT();
                totalValue += i.getVALUE();
            }
        }
        
        if (totalWeight < 10) {
            fitness = totalValue; // if total weight is less than 10, the fitness is the sum of all item values
        }
        else {
            fitness = 0; // if total weight is greater than 10, the fitness is 0
        }

        return fitness;
    }

    // compare to method
    public int compareTo(Chromosome other) {
        if (this.getFitness() > other.getFitness()) {
            return -1; // list before if this chromosome has a greater fitness 
        }
        else if (this.getFitness() < other.getFitness()) {
            return 1; // list after if this chromosome has a lesser fitness
        }
        else {
            return 0;
        }
    }

    // to stirng method
    public String toString() {
        String s = ""; // start with an empty string

        // add each included item to the string 
        for (Item i : this) {
            if (i.isIncluded()) {
                s += i.toString() + "\n";
            }
        }
        s += "Fitness: " + this.getFitness(); // add the fitness

        return s;

    }
}
