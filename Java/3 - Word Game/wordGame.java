import java.util.*;
import java.io.File;

public class wordGame {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        File wordFile = new File("words.txt");
        Scanner fileIn = new Scanner(wordFile);
        final int REQUIRED_WORD_LENGTH = 7;
        int score = 0;

    //choose a word with 7 unique characters
        //create new array list for words list
        ArrayList<String> wordsList = new ArrayList<>();

        //store words in array list
        while (fileIn.hasNextLine()) {
            wordsList.add(fileIn.nextLine()); 
        }

        //declare target word variable
        String targetWord;

        //check target word conditions 
        do {
            Collections.shuffle(wordsList);
            targetWord = wordsList.get(0);    
        } while (targetWord.length() != REQUIRED_WORD_LENGTH || uniqueCharTest(targetWord) == false);

    //manipulate chosen target word
        //create new array list for target word characters 
        ArrayList<Character> targetWordChars = new ArrayList<>();

        for (Character x : targetWord.toCharArray()) {
            targetWordChars.add(x);
        }
        
        //randomize target word character order
        Collections.shuffle(targetWordChars);

        //format and print target word characters 
        for (Character y : targetWordChars) {
            System.out.print(y + "\t");
        }
        System.out.println();

        //print score
        System.out.println("Score: " + score);

    //configure gameplay
        //declare user input variable and create array list to store valid word inputs
        String userInput;
        ArrayList<String> userWords = new ArrayList<>();

        //loop gameplay until user types "bye"
        do {
            //read user input
            userInput = in.next().toLowerCase();
            //calculate score based on input
            if (!userInput.equalsIgnoreCase("ls") && !userInput.equalsIgnoreCase("mix")) {
                //words 4 characters long
                if (wordsList.contains(userInput) && targetWordCharParameter(targetWord, userInput) == true && userInput.length() == 4 && !userWords.contains(userInput)) {
                    score = score + 1;
                    userWords.add(userInput);
                    System.out.println("Score: " + score);
                }    
                //words greater than 4 characters long
                else if (wordsList.contains(userInput) == true && targetWordCharParameter(targetWord, userInput) == true && userInput.length() >= 4 && !userWords.contains(userInput)) {
                    score = score + userInput.length();
                    userWords.add(userInput);
                    System.out.println("Score: " + score);
                }
            }
            //reorder letters when user types "mix"
            if (userInput.equalsIgnoreCase("mix")) {
                Collections.shuffle(targetWordChars);
                for (Character z : targetWordChars) {
                    System.out.print(z + "\t");
                }
                System.out.println();
                System.out.println("Score: " + score);
            } 
            //show all valid user words when user types "ls"
            else if (userInput.equalsIgnoreCase("ls")) {
                for (int i=0; i<userWords.size(); i++) {
                    System.out.println(userWords.get(i));
                }
                System.out.println("Score: " + score);
            }
        } while (!userInput.equalsIgnoreCase("bye"));

        //print score and end program when user types "bye"
        System.out.println("Score: " + score);

        in.close();
        fileIn.close();
    }
    
    //chekc if target word has unique characters
    public static boolean uniqueCharTest(String testWord) {
        //create array to store characters in target word
        final int REQUIRED_WORD_LENGTH = 7;
        char[] wordChars = new char[REQUIRED_WORD_LENGTH];
        wordChars = testWord.toCharArray();
        Arrays.sort(wordChars);

        //check if any there are duplicate characters
        for (int i=0; i<wordChars.length-1; i++) {
            if (wordChars[i] == wordChars[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //check if characters in user word are present in the target word
    public static boolean targetWordCharParameter(String targetWord, String userWord) {
        //create array list to store user word characters
        ArrayList<Character> userWordChars = new ArrayList<>();
        for (Character x : userWord.toCharArray()) {
            userWordChars.add(x);
        }
        //create array list to store target word characters
        ArrayList<Character> targetWordChars = new ArrayList<>();
        for (Character y : targetWord.toCharArray()) {
            targetWordChars.add(y);
        }
        //check if characters in user word are in target word
        for (Character z : userWordChars) {
            if (!targetWordChars.contains(z)) 
            return false;
        }
        return true;
    }
}   