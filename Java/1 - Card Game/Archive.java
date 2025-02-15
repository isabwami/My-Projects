public class Archive {
/* 

   //convert strings to integers
    char c1RankChar = card1.charAt(0);
    char c2RankChar = card2.charAt(0);
    char c3RankChar = card3.charAt(0);

    String card1Rank = Character.toString(c1RankChar);
    String card2Rank = Character.toString(c2RankChar);
    String card3Rank = Character.toString(c3RankChar);

    int c1Rank = Integer.parseInt(card1Rank);
    int c2Rank = Integer.parseInt(card2Rank);
    int c3Rank = Integer.parseInt(card3Rank);

    //check validity of cards
    if (c1Rank < 2 || c1Rank > 10) {
        System.out.println(c1Rank + " is not a valid rank");
    }

    if (c2Rank < 2 || c2Rank > 10) {
        System.out.println(c2Rank + " is not a valid rank");
    }
    
    if (c3Rank < 2 || c3Rank > 10) {
        System.out.println(c3Rank + " is not a valid rank");
    }



//if rank is letter
            char jackSymbol = 'J';
            char queenSymbol = 'Q';
            char kingSymbol = 'K';
            char aceSymbol = 'A';
            
            //jack
            if (letterRank == 'J' || letterRank == 'j') {
                int jack = (int) jackSymbol - 63;
            }
            
            //queen
            else if (letterRank == 'Q' || letterRank == 'q') {
                int queen = (int) queenSymbol - 69;
            }

            //king
            else if (letterRank == 'K' || letterRank == 'k') {
                int king = (int) kingSymbol - 62;
            }

            //ace
            else if (letterRank == 'A' || letterRank == 'a') {
                int ace = (int) aceSymbol - 51;
            }
            
            else {
                System.out.println(card1Rank + " is not a valid rank.");
            }    



//convert card2 string to integer and check condition for single digit number
        if (card2.length() == 2) {

            char c2RankChar = card2.charAt(0);
            String card2Rank = Character.toString(c2RankChar);
            int c2Rank = Integer.parseInt(card2Rank);
            
            if (c2Rank < 2) {
                System.out.println(c2Rank + " is not a valid rank");
            }   
        }
//convert card2 string to integer and check condition for double digit number (10)
        else if(card2.length() == 3) {
            String c2RankString = card2.substring(0 , 2);
            int c2Rank = Integer.parseInt(c2RankString);
            
            if (c2Rank != 10 ) {
                System.out.println(c2Rank + " is not a valid rank");
            }   
        }


//convert card3 string to integer and check condition for single digit number
        if (card3.length() == 2) {

            char c3RankChar = card3.charAt(0);
            String card3Rank = Character.toString(c3RankChar);
            int c3Rank = Integer.parseInt(card3Rank);
            
            if (c3Rank < 2) {
                System.out.println(c3Rank + " is not a valid rank");
            }   
        }
//convert card3 string to integer and check condition for double digit number (10)
        else if(card3.length() == 3) {
            String c3RankString = card3.substring(0 , 2);
            int c3Rank = Integer.parseInt(c3RankString);
            
            if (c3Rank != 10 ) {
                System.out.println(c3Rank + " is not a valid rank");
            }   
        }

*/    
}
