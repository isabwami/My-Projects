import java.util.*;
//import java.io.*;

public class Archive {
    public static void main(String[] args) throws Exception {
        
        /*
        Scanner in = new Scanner(System.in);
        File wordsList = new File("words.txt");
        Scanner fileIn = new Scanner(wordsList);

        int wordCount = 0;
        while (fileIn.hasNextLine()) {
            wordCount += 1;
            fileIn.nextLine();
        }
        fileIn.close();
        
        System.out.println(wordCount);
        in.close();
        */
    
        /* 
        int wordCount = 0;
        while (fileIn.hasNextLine()) {
            wordCount += 1;
            fileIn.nextLine();
        }
        String[] wordList = new String[wordCount];
        
        for (int i=0; i<wordCount-1; i++) {
            if (fileIn.hasNextLine()) {
                wordList[i] = fileIn.nextLine();
            }
        }
        
        int randNum = (int) ((Math.random() * wordCount) + 1);

        System.out.println(wordList[randNum]);
        

        fileIn.close();
        */

        /*
             ArrayList<Character> wordChars = new ArrayList<>();
        for (Character c : testWord.toCharArray()) {
            wordChars.add(c);
        }
        

        for (int i=0; i<wordChars.size(); i++) {
            if (wordChars.get(i) == wordChars.get(i+1)) {
                return false;
            }
        }
        */
        String testString = "flowing";

        ArrayList<Character> testList = new ArrayList<>();

        for (Character x : testString.toCharArray()) {
            testList.add(x);
        }

        System.out.println(testList);

        Collections.sort(testList);

        System.out.println(testList);
    }
}
