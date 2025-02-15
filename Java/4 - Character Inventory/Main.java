import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // create character array list
        ArrayList<Character> characterList = new ArrayList<>();

        // declare user input variable
        String userInput;

        do {
            // print questions and read user input
            printOptionsList();
            userInput = in.next();
            in.nextLine();
            
            // if user adds a new character
            if (userInput.equals("1")) {
                System.out.print("What is the character's name? ");
                String characterName = in.nextLine();
                
                // ensure it is not a duplicate character 
                if (isInCharacterList(characterList, characterName)) {
                    System.out.println("Error: A character with that name already exists.");
                } // end if-statement 
                else {
                    System.out.print("How many credits does the character have? ");
                    int characterCredits = in.nextInt();
                    
                    //create array list of character items and character object 
                    ArrayList<Item> c1Items = new ArrayList<>();
                    Character c1 = new Character(characterName, characterCredits, c1Items);
    
                    characterList.add(c1);
                    
                    System.out.println(c1.getName() + " added");
                } // end else-statement 

                System.out.println();
            } // end-if statement

            if (userInput.equals("2")) {
                System.out.print("Which character is adding the item? ");
                String characterName = in.nextLine();
                String itemName;
                int itemValue;
                
                // ensure selected character is in character list *This is the same for all options that require the user to select a character*
                if (isInCharacterList(characterList, characterName)) {

                    // find character with the name that matches what the user entered *This is the same for all options that require the user to select a character*
                    for (Character c : characterList) {
                        if (c.getName().equals(characterName)) {
                            System.out.print("What is the name of the item? ");
                            itemName = in.nextLine();
                            System.out.print("What is the item's value? ");
                            itemValue = in.nextInt();

                            // recall addItem method from character class
                            c.addItem(itemName, itemValue);

                            System.out.println(c.getName() + " has aquired " + itemName);
                            break;
                        } // end-if statement 
                   } // end-for loop 
                } // end-if statement
                else {

                    // print error message if character with given name is not found *This is the same for all options that require the user to select a character*
                    System.out.println("Error: No character with that name exists.");
                } // end-else statement
                
                System.out.println();
            } // end-if statement

            if (userInput.equals("3")) {
                System.out.print("Which character is dropping the item? ");
                String characterName = in.nextLine();
                String itemName;
                
                if (isInCharacterList(characterList, characterName)) {
                    for (Character c : characterList) {
                        if (c.getName().equals(characterName)) {
                            System.out.print("What is the name of the item? ");
                            itemName = in.nextLine();
                            
                            // recall dropItem method from character class to drop an item
                            if (c.dropItem(itemName)) {
                                System.out.println(c.getName() + " has dropped " + itemName);
                            } // end-if statement
                            else {
                                System.out.println(c.getName() + " could not drop " + itemName);
                            } // end-else statement                            
                        } // end-if statement        
                    } // end-for loop  
                } // end-if statement
                else {
                    System.out.println("Error: No character with that name exists.");   
                } // end-else statement
            
                System.out.println();
            } // end-if statement

            if (userInput.equals("4")) {
                System.out.print("Which character is selling the item? ");
                String characterName = in.nextLine();
                String itemName;

                if (isInCharacterList(characterList, characterName)) {
                    for (Character c : characterList) {
                        if (c.getName().equals(characterName)) {
                            System.out.print("What is the name of the item being sold? ");
                            itemName = in.nextLine();
                            
                            // recall sellItemToVendor method to sell an item to a vendor 
                            if (c.sellItemToVendor(itemName)) {
                                System.out.println(c.getName() + " has sold " + itemName + " to a vendor");    
                            } // end-if statement            
                            else {
                                System.out.println(c.getName() + " could not sell " + itemName + " to a vendor");
                            } // end-else statement                             
                        } // end-if statement        
                    } // end-for loop
                } // end-if statement
                else {
                    System.out.println("Error: No character with that name exists.");   
                } // end-else statement

                System.out.println();   
            } // end-if statement
            
            if (userInput.equals("5")) {
                System.out.print("Which character is selling the item? ");
                String sellerName = in.nextLine();
                String itemName;

                // does each check operation twice for buyer and seller (eg. check that character is in characterList) 
                if (isInCharacterList(characterList, sellerName)) {
                    System.out.print("Which character is buying the item? ");
                    String buyerName = in.nextLine();
                    
                    if (isInCharacterList(characterList, buyerName)) {
                        
                        for (Character c : characterList) {
                            if (c.getName().equals(sellerName)) {
                                System.out.print("What is the name of the item being sold? ");
                                itemName = in.nextLine();

                                for (Character d : characterList) {

                                    if (d.getName().equals(buyerName)) {

                                        // recall sellItemToCharacter method to sell an item to another character
                                        if (c.sellItemToCharacter(itemName, d)) {
                                            System.out.println(c.getName() + " has sold " + itemName + " to " + d.getName());
                                        } // end-if statement
                                        else {
                                            System.out.println(c.getName() + " could not sell " + itemName + " to " + d.getName());
                                        } // end-else statement 
                                    } // end-if statement 
                                } // end-for loop
                            } // end-if statement
                        } // end-for loop

                    } // end-if statement
                    else {
                        System.out.println("Error: No character with that name exists.");
                    } // end-else statement

                } // end-if statement
                else {
                    System.out.println("Error: No character with that name exists.");                    
                } // end-else statement 

                System.out.println();                
            } // end-if statement

            if (userInput.equals("6")) {
                
                // sort list of characters lexicographically
                Collections.sort(characterList);

                // print each characterList
                for (Character c : characterList) {
                    System.out.println(c);
                } // end-for loop

                System.out.println();
            } // end-if statement

            if (userInput.equals("7")) {
                System.out.print("Which character's inventory would you like to see? ");
                String characterName = in.nextLine();

                if (isInCharacterList(characterList, characterName)) {
                    for (Character c : characterList) {
                        if (c.getName().equals(characterName)) {
                            
                            // sort character's items list by value
                            Collections.sort(c.getItems());
                            for (Item i : c.getItems()) {
                                System.out.println(i);
                            } // end-for loop
                        } // end-if statement 
                   } // end-for loop  
                } // end-if statement
                else {
                    System.out.println("Error: No character with that name exists.");
                } // end-else statement

                System.out.println();
            } // end-if statement

            if (userInput.equals("8")) {
                
                // sort list of characters lexicographically 
                Collections.sort(characterList);

                for (Character c : characterList) {
                    System.out.println(c);
                    
                    //sort character's items list by value 
                    Collections.sort(c.getItems());
            
                    for (Item i : c.getItems()) {
                        System.out.println(i);
                    } // end-for loop 
                    System.out.println(); 
                } // end-for loop

                System.out.println();
            } // end-if statement

            if (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && 
            !userInput.equals("4") && !userInput.equals("5") && !userInput.equals("6") && 
            !userInput.equals("7") && !userInput.equals("8") && !userInput.equals("9")){
                System.out.println("Error: Invalid Input");
                System.out.println();
            } // end-if statement

        // quit if user enters '9'
        } while (!userInput.equals("9")); //end-do while loop

        in.close();
    } // end-main method

    // prints list of options
    public static void printOptionsList() {
        System.out.println("1. Create a Character");
        System.out.println("2. Character adds an item");
        System.out.println("3. Character drops an item");
        System.out.println("4. Character sells an item to a vendor");
        System.out.println("5. Character sells an item to another character");
        System.out.println("6. List characters");
        System.out.println("7. List a character's inventory by value");
        System.out.println("8. List all character's inventories by value");
        System.out.println("9. Quit");
        System.out.print("What would you like to do? ");
    } // end-printOptionsList method

    // returns true if the selected character has a name that matches the name of a character in the character list
    public static boolean isInCharacterList(ArrayList<Character> characterList, String characterName) {
        for (Character c : characterList) {
            if (characterName.equals(c.getName())) {
                return true;
            } // end-if statement
        } // end-for loop  
        
        // returns false under any other circumstances
        return false;
    } // end-isInCharacterList method  
}
