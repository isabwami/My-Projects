import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Archive {
    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner fileIn = new Scanner(new File("items.txt"));
        
        String itemInfo;
        while(fileIn.hasNextLine()) {
            itemInfo = fileIn.nextLine();
            String[] itemInfoArray = itemInfo.split(", ");
            System.out.println(itemInfoArray[2]);
        }

        double n = 1.5;
        int round = (int)(Math.round(n));

        System.out.println(round);

    }
}
