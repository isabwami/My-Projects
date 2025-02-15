import java.util.Scanner;

public class memoryGame {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        //declare and initialize score variable
        int score = 0;

    //print 3 random numbers        
        //declare random number variables
        int randNum1;
        int randNum2;
        int randNum3;
        
        //loop entire program 10 times 
        for (int i=0; i<10; i++) {
            //ensure numberes are unique
            do {
                randNum1 = (int) ((Math.random() * 100) + 1);
                randNum2 = (int) ((Math.random() * 100) + 1);
                randNum3 = (int) ((Math.random() * 100) + 1);
            }
            while (randNum1 == randNum2 || randNum1 == randNum3 || randNum2 == randNum3);
            
            //print 3 random numbers
            System.out.println("The numbers for this round are: " + randNum1 + " " + randNum2 + " " + randNum3);
            
            //wait 3 seconds
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
             
            //print 100 blank lines
            for(int j=0; j<=100; j++) {
                System.out.println();
            }
            

        //prepare question answers
            //declare/initialize answer variables
            int minNum;
            int midNum;
            int maxNum;
            int sum = randNum1 + randNum2 + randNum3;

            //determine smallest number 
            if (randNum1 < randNum2 && randNum1 < randNum3) {
                minNum = randNum1;
            }
            else if (randNum2 < randNum1 && randNum2 < randNum3) {
                minNum = randNum2;
            }
            else {
                minNum = randNum3;
            }
            
            //determine median number
            if ((randNum1 < randNum2 && randNum1 > randNum3) || (randNum1 > randNum2 && randNum1 < randNum3)) {
                midNum = randNum1;
            }
            else if ((randNum2 < randNum1 && randNum2 > randNum3) || (randNum2 > randNum1 && randNum2 < randNum3)) {
                midNum = randNum2;
            }
            else {
                midNum = randNum3;
            }

            //determine largest number 
            if (randNum1 > randNum2 && randNum1 > randNum3) {
                maxNum = randNum1;
            }
            else if (randNum2 > randNum1 && randNum2 > randNum3) {
                maxNum = randNum2;
            }
            else {
                maxNum = randNum3;
            }        

        
        //choose, ask, and evaluate question
            //declare and initialize variable that randomly chooses a question 
            int randQuestNum = (int) (Math.random() * 4);

            //smallest number
            if (randQuestNum == 0) {
                System.out.print("Enter the smallest number: ");
                int smallResult = in.nextInt();
                
                if (smallResult == minNum) {
                    System.out.println("Correct, Great Job!");
                    score += 1;
                } 
                else {
                    System.out.println("Incorrect, the correct answer is: " + minNum);
                }
            }
            
            //median number
            else if (randQuestNum == 1) {
                System.out.print("Enter the median number: ");
                int midResult = in.nextInt();
                
                if (midResult == midNum) {
                    System.out.println("Correct, Great Job!");
                    score += 1;
                } 
                else {
                    System.out.println("Incorrect, the correct answer is: " + midNum);
                }
            }

            //largest number
            else if (randQuestNum == 2) {
                System.out.print("Enter the largest number: ");
                int largeResult = in.nextInt();
                
                if (largeResult == maxNum) {
                    System.out.println("Correct, Great Job!");
                    score += 1;
                } 
                else {
                    System.out.println("Incorrect, the correct answer is: " + maxNum);
                }
            }
            
            //sum
            else {
                System.out.print("Enter the sum of the numbers: ");
                int sumResult = in.nextInt();
                
                if (sumResult == sum) {
                    System.out.println("Correct, Great Job!");
                    score += 1;
                } 
                else {
                    System.out.println("Incorrect, the correct answer is: " + sum);
                }
            }

        }

        System.out.println();
        System.out.println("You got " + score + " out of 10 correct.");
        in.close();
    }
}
