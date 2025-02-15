import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;
import java.util.ArrayList;

public class PasswordCracker {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<String> passwords = genPass(alphabet, 3);

        
        for (int i=0; i<passwords.size(); i++) {
            String currPassword = passwords.get(i);
            try {
                ZipFile zipFile = new ZipFile("protected3.zip");
                zipFile.setPassword(currPassword);
                zipFile.extractAll("contents");
                //System.out.println("Successfully cracked!");
                System.out.println("Password is <" + currPassword + ">");
                break;
            } catch (ZipException ze) {
                continue;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        
        long duration = (System.currentTimeMillis() - startTime);
        System.out.println("Completed in " + duration + " milliseconds");
    }
    
    public static ArrayList<String> genPass(String alphabet, int k) {
        ArrayList<String> passwords = new ArrayList<>();
        char[] characters = alphabet.toCharArray();

        if (k==1) {
            for (int i=0; i<characters.length; i++) {
                passwords.add("" + characters[i]);
            }
            return passwords;    
        }
        
        for (int i=0; i<characters.length; i++) {
            char current = characters[i];
            ArrayList<String> combinations = genPass(alphabet, k-1);
            
            for (String s : combinations) {
                passwords.add(current + s);
            }
        }

        return passwords;
    }
}
