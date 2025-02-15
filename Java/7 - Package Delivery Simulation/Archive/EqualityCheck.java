import java.util.*;
import java.io.*;

public class EqualityCheck {

    public static void main(String[] args) throws Exception{

        Scanner in = new Scanner(new File("sample_output.txt"));
        Scanner myIn = new Scanner(new File("my_output.txt"));
        
        ArrayList<String> sampleOutput = new ArrayList<>();
        ArrayList<String> myOutput = new ArrayList<>();
                

        while (in.hasNextLine()) {
            String s = in.nextLine();
            sampleOutput.add(s);
        }

        while (myIn.hasNextLine()) {
            String s = myIn.nextLine();
            myOutput.add(s);
        }

        int i = 0;
        for (String s : sampleOutput) {
            if (!sampleOutput.get(i).equals(myOutput.get(i))) {
                System.out.println(s);
            }
            i++;
        }
        
        
    }

}
