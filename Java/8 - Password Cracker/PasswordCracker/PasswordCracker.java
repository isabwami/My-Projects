import java.util.ArrayList;
import java.io.File;

/*
Zephyrus M16 Core i9 11900H
    4 threads - 713094 milliseconds (11 minutes 54 seconds)
    3 threads - 3236217 milliseconds (55 minutes 19 seconds)
*/

/*
MacBook Pro 16 M2 Max
    4 threads - 195270 milliseconds (3 minutes 15 seconds)
    3 threads - 732529 milliseconds (12 minutes 12 seconds)
*/

/*
Passwords
    protected3 - <may>
    protected5 - <iamin>
*/

public class PasswordCracker {
    
    public static void main(String[] args) throws Exception {

        // select number of threads
        int numThreads = 8;

        // generate passwords
        long passGenStartTime = System.currentTimeMillis();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<String> passwords = genPass(alphabet, 5); // NOTE: use the second input to change the number of characters in the password (3 or 5)

        // print password generation time
        long passGenDuration = (System.currentTimeMillis() - passGenStartTime);
        System.out.println("Passwords generated in " + passGenDuration + " milliseconds");

        long passCrackStartTime = System.currentTimeMillis();
        
        // create parent directory for threads
        String dirName = "./ThreadContents";
        File dir = new File(dirName);

        if (!dir.exists()) {
            dir.mkdirs();
        } // end if-statement

        // create threads
        ArrayList<CrackThread> threads = new ArrayList<>();
        int blockSize = passwords.size() / numThreads;

        for (int i=0; i<numThreads; i++) {
            threads.add(new CrackThread(passwords, i*blockSize, (i+1) * blockSize, i));
        } // end for-loop

        // start and join threads
        for (Thread t : threads) {
            t.start();
        } // end for-loop
        for (Thread t : threads) {
            t.join();
        } // end for-loop

        // print correct password and cleanup remaining files created by threads
        System.out.println("Password is <" + CrackThread.correctPass + ">");

        cleanUp(new File(dirName));

        // print password crack time
        long passCrackDuration = (System.currentTimeMillis() - passCrackStartTime);
        System.out.println("Password found in " + passCrackDuration + " milliseconds");
    } // end main() method

    public static ArrayList<String> genPass(String alphabet, int k) {
        ArrayList<String> passwords = new ArrayList<>();
        char[] characters = alphabet.toCharArray();

        if (k==1) {
            for (int i=0; i<characters.length; i++) {
                passwords.add("" + characters[i]);
            } // end for-loop
            return passwords;    
        } // end if-statement

        for (int i=0; i<characters.length; i++) {
            char current = characters[i];
            ArrayList<String> combinations = genPass(alphabet, k-1);
            
            for (String s : combinations) {
                passwords.add(current + s);
            } // end for-loop
        } // end for-loop

        return passwords;
    } // end genPass() method

    // this method was created using methods listed on the File javadoc - https://docs.oracle.com/javase/7/docs/api/java/io/File.html 
    public static void cleanUp(File dir) throws Exception {
        File directory = dir;
    
        File[] files = directory.listFiles();
    
        if (files.length > 0) {    
            for (File f : files) {
                if (f.isDirectory()) {
                    cleanUp(f); 
                } // end if-statement
                f.delete();
            } // end for-loop
        } // end if-statement
    
        if (directory.listFiles().length == 0) {
            directory.delete();
        } // end if-statement
    } // end cleanUp() method
} // end PasswordCrackerMultiThreaded class