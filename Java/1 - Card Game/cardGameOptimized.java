import java.util.Scanner;

public class cardGameOptimized {
    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    

//Identify cards

        //ask user to input cards
        System.out.print("Enter 3 cards: ");
        
        //cards 
        String c1 = in.next();
        String c2 = in.next();
        String c3 = in.next();
        
        in.close();

        //ranks
        String card1Rank = c1.substring(0 , c1.length() - 1);
        String card2Rank = c2.substring(0 , c2.length() - 1);
        String card3Rank = c3.substring(0 , c3.length() - 1);

        //declare c1Rank as integer
        int c1Rank;
        int c2Rank;
        int c3Rank;        
            
        //check if rank is digit
        //"Character.isDigit(c1.charAt(0)" reference https://www.techiedelight.com/determine-string-ends-with-number-java/
        boolean c1IsDigit = Character.isDigit(c1.charAt(0));
        boolean c2IsDigit = Character.isDigit(c2.charAt(0));
        boolean c3IsDigit = Character.isDigit(c3.charAt(0));

        //suits
        int c1Length = c1.length();
        int c2Length = c2.length();
        int c3Length = c3.length();

        char c1Suit = c1.charAt(c1Length - 1);
        char c2Suit = c2.charAt(c2Length - 1);
        char c3Suit = c3.charAt(c3Length - 1);


//Check validity of cards

        //suit validity

            //card 1 Suit
            suitValidity(c1Suit);

            //card 2 Suit
            suitValidity(c2Suit);

            //card 3 Suit
            suitValidity(c3Suit);    

        //rank validity

            //if card 1 rank is a digit
            if (c1IsDigit) {

                //integer conversion
                c1Rank = Integer.parseInt(card1Rank);

                //rank parameters
                if (c1Rank < 2 || c1Rank > 10) {
                    System.out.println(c1Rank + " is not a valid rank.");
                    return;
                }
            }

            //if card 1 rank is a letter
            else {
                if (c1.charAt(0) == 'J' || c1.charAt(0) == 'j') {
                    c1Rank = (int) 11;
                }
                
                else if (c1.charAt(0) == 'Q' || c1.charAt(0) == 'q') {
                    c1Rank = (int) 12;
                }

                else if (c1.charAt(0) == 'K' || c1.charAt(0) == 'k') {
                    c1Rank = (int) 13;
                }

                else if (c1.charAt(0) == 'A' || c1.charAt(0) == 'a') {
                    c1Rank = (int) 14;
                }
                else {
                    System.out.println(card1Rank + " is not a valid rank.");
                    return;
                }
            }

            //if card 2 rank is a digit
            if (c2IsDigit) {

                //integer conversion
                c2Rank = Integer.parseInt(card2Rank);

                //rank parameters
                if (c2Rank < 2 || c2Rank > 10) {
                    System.out.println(c2Rank + " is not a valid rank.");
                    return;
                }
            }

            //if card 2 rank is a letter
            else {
                if (c2.charAt(0) == 'J' || c2.charAt(0) == 'j') {
                    c2Rank = (int) 11;
                }
                
                else if (c2.charAt(0) == 'Q' || c2.charAt(0) == 'q') {
                    c2Rank = (int) 12;
                }

                else if (c2.charAt(0) == 'K' || c2.charAt(0) == 'k') {
                    c2Rank = (int) 13;
                }

                else if (c2.charAt(0) == 'A' || c2.charAt(0) == 'a') {
                    c2Rank = (int) 14;
                }
                else {
                    System.out.println(card2Rank + " is not a valid rank.");
                    return;
                }
            }            

            //if card 3 rank is a digit
            if (c3IsDigit) {

                //integer conversion
                c3Rank = Integer.parseInt(card3Rank);

                //rank parameters
                if (c3Rank < 2 || c3Rank > 10) {
                    System.out.println(c3Rank + " is not a valid rank.");
                    return;
                }
            }
            
            //if card 1 rank is a letter
            else {
                if (c3.charAt(0) == 'J' || c3.charAt(0) == 'j') {
                    c3Rank = (int) 11;
                }
                
                else if (c3.charAt(0) == 'Q' || c3.charAt(0) == 'q') {
                    c3Rank = (int) 12;
                }

                else if (c3.charAt(0) == 'K' || c3.charAt(0) == 'k') {
                    c3Rank = (int) 13;
                }

                else if (c3.charAt(0) == 'A' || c3.charAt(0) == 'a') {
                    c3Rank = (int) 14;
                }
                else {
                    System.out.println(card3Rank + " is not a valid rank.");
                    return;
                }
            }


//determine which card should be played   

        //difference between card 2 and card 1 and difference between card 3 and card 1
        int c2Gap = c2Rank - c1Rank;
        int c3Gap = c3Rank - c1Rank; 
        
        //when player 2 wins
        if (c2Rank > c1Rank && c3Rank > c1Rank) {
            if (c2Gap > c3Gap) {
                System.out.println("Player 2 plays the " + c3.toUpperCase() + ".");
                System.out.println("Player 2 wins!");
            }
            
            if (c3Gap > c2Gap) {
                System.out.println("Player 2 plays the " + c2.toUpperCase() + ".");
                System.out.println("Player 2 wins!");
            }
        }

        else if (c2Rank > c1Rank || c3Rank > c1Rank) {

            if (c2Gap > c3Gap) {
                System.out.println("Player 2 plays the " + c2.toUpperCase() + ".");
                System.out.println("Player 2 wins!");
            }

            if (c3Gap > c2Gap) {
                System.out.println("Player 2 plays the " + c3.toUpperCase() + ".");
                System.out.println("Player 2 Wins!");
            }

        }
        
        //when player 1 wins
        else if (c1Rank > c2Rank && c1Rank > c3Rank) {

            if (c2Gap > c3Gap) {
                System.out.println("Player 2 plays the " + c3.toUpperCase() + ".");
                System.out.print("Player 1 wins!");
            }

            else if (c3Gap > c2Gap) {
                System.out.println("Player 2 plays the " + c2.toUpperCase() + ".");
                System.out.println("Player 1 wins!");
            }

        }

        //when player 1 and 2 have the same highest ranked card
        else {

            if (c2Gap >= c3Gap) {
                System.out.println("Player 2 plays the " + c3.toUpperCase() + ".");
                System.out.print("Player 1 wins!");
            }

            else if (c3Gap >= c2Gap) {
                System.out.println("Player 2 plays the " + c2.toUpperCase() + ".");
                System.out.println("Player 1 wins!");
            }

        }

    }

    public static void suitValidity(char suit) {
        if (suit != 'H' && suit != 'C' && suit != 'D' && suit != 'S' && suit != 'h' && suit != 'c' && suit != 'd' && suit != 's') {
            System.out.println(suit + " is not a valid suit");
            System.exit(0);
        }
    }

}
