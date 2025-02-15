import java.util.ArrayList;
import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;

public class CrackThread extends Thread {

    ArrayList<String> passwords;
    int startAt;
    int endAt;
    int threadID;
    public volatile static boolean passFound = false;
    public volatile static String correctPass;

    public CrackThread(ArrayList<String> passwords, int startAt, int endAt, int threadID) {
        this.passwords = passwords;
        this.startAt = startAt;
        this.endAt = endAt;
        this.threadID = threadID;
    } // end CrackThread constructor

    @Override
    public void run() {

        try {
            // create zip file copy and zip file object
            // NOTE: Use these two lines to change the zip file that is to be cracked (protected3 or protected5)
            Files.copy(Path.of("./protected5.zip"), Path.of("./ThreadContents/Thread_" + threadID + "_Copy")); 
            ZipFile zipFile = new ZipFile("protected5.zip"); 

            // test each assigned password in given ArrayList
            for (int i=startAt; i<endAt && (!passFound); i++) {
                String currPassword = passwords.get(i);
                //System.out.println("Thread #" + threadID + " testing: " + currPassword); // NOTE: This line can be un-commented to see each password being tested

                try {
                    zipFile.setPassword(currPassword);
                    zipFile.extractAll("./ThreadContents/contents_" + threadID);
                    // ensure only one thread changes the volatile variables
                    synchronized (this) {
                        correctPass = currPassword;
                        passFound = true;
                    } // end synchronized block
                    return;
                } catch (ZipException ze) {
                    continue;
                } catch (Exception e){
                    e.printStackTrace();
                } // end try-catch block
            } // end for-loop 

            // delete thread contents
            Files.delete(Path.of("./ThreadContents/Thread_" + threadID + "_Copy"));

        } catch (Exception e1) {
            e1.printStackTrace();
        } // end try-catch block
    } // end run() method
} // end CrackThread class