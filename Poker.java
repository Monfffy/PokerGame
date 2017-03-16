import java.util.Arrays;

/**
 * @author 
 * This class contain the main method of the PokerGame Project.
 * The PokerGame project consider the hand of 5-card poker game.
 * There are 9 classification of hands with different value.
 * This program could find the classification of input of cards
 * and decide the winner if input more than one hand.
 */
public class Poker {
    public static final int N = 5;// Number of cards in one hand

    //The main method is mainly deal with input and output of the program
    public static void main(String[] args) {
        
        //Check the input of cards should be multiple of 5 and more than 0
        if (args.length % 5 == 0 && args.length > 0) {
            
            int player = args.length / N; //count the player based on input
            int playerNo; //store the number of player for print
            int countCard = args.length; //count the number of card 

            Suit[] CardSuit = new Suit[countCard]; //store the suit of cards
            Rank[] CardRank = new Rank[countCard]; //store the rank of cards
            Card[] card = new Card[countCard]; //store all cards
            Card[] cardinhand = new Card[N]; //store cards in one hand
            PokerHand[] hand = new PokerHand[player]; //store all players' hands
            
            //Deal with print of players draw
            int[] drawPlayer = new int[player]; //store draw players number 
        /*set default value in the array as bigger than the number of player, 
            so that will be sorted to the last if not updated*/
            Arrays.fill(drawPlayer, player + 1);  
            int countDraw = 0; //count the number of draw players
            
            //Get the input: rank of the card
            for (int i = 0; i < countCard; i++) {
                switch (args[i].charAt(0)) {
                case ('2'):
                    CardRank[i] = Rank.TWO;
                    break;
                case ('3'):
                    CardRank[i] = Rank.THREE;
                    break;
                case ('4'):
                    CardRank[i] = Rank.FOUR;
                    break;
                case ('5'):
                    CardRank[i] = Rank.FIVE;
                    break;
                case ('6'):
                    CardRank[i] = Rank.SIX;
                    break;
                case ('7'):
                    CardRank[i] = Rank.SEVEN;
                    break;
                case ('8'):
                    CardRank[i] = Rank.EIGHT;
                    break;
                case ('9'):
                    CardRank[i] = Rank.NINE;
                    break;
                case ('T'):
                case ('t'):
                    CardRank[i] = Rank.TEN;
                    break;
                case ('J'):
                case ('j'):
                    CardRank[i] = Rank.JACK;
                    break;
                case ('Q'):
                case ('q'):
                    CardRank[i] = Rank.QUEEN;
                    break;
                case ('K'):
                case ('k'):
                    CardRank[i] = Rank.KING;
                    break;
                case ('A'):
                case ('a'):
                    CardRank[i] = Rank.ACE;
                    break;
                default:
                    System.out.println
                    ("Error: invalid card name '" + args[i] + "'");
                    System.exit(i);
                    break;
                }
                //Get the input: suit of the card
                switch (args[i].charAt(1)) {
                case ('C'):
                case ('c'):
                    CardSuit[i] = Suit.Clubs;
                    break;
                case ('D'):
                case ('d'):
                    CardSuit[i] = Suit.Diamonds;
                    break;
                case ('H'):
                case ('h'):
                    CardSuit[i] = Suit.Hearts;
                    break;
                case ('S'):
                case ('s'):
                    CardSuit[i] = Suit.Spades;
                    break;
                default:
                    System.out.println
                    ("Error: invalid card name '" + args[i] + "'");
                    System.exit(i);
                    break;
                }
                //Set all cards in the array
                card[i] = new Card(CardRank[i], CardSuit[i]);
            }

            //Use all cards to set cards for each player
            for (int i = 0; i < player; i++) {
                for (int j = 0; j < N; j++) {
                    cardinhand[j] = card[i * 5 + j];
                }
                playerNo = i + 1;
                //Set cards and player number of each hand
                hand[i] = new PokerHand(cardinhand, playerNo);
                System.out.print("Player " + playerNo + ": ");
                //Find the classification of each hand and print
                switch (hand[i].FindClass()) {
                case Straight_flush:
                    System.out.println
                    (hand[i].getR1().getRankName() + "-high straight flush");
                    break;
                case Four_of_a_kind:
                    System.out.println
                    ("Four " + hand[i].getR1().getRankName() + "s");
                    break;
                case Full_house:
                    System.out.println
                    (hand[i].getR1().getRankName() + "s full of " + 
                     hand[i].getR2().getRankName() + "s");
                    break;
                case Flush:
                    System.out.println
                    (hand[i].getR1().getRankName() + "-high flush");
                    break;
                case Straight:
                    System.out.println
                    (hand[i].getR1().getRankName() + "-high straight");
                    break;
                case Three_of_a_kind:
                    System.out.println
                    ("Three " + hand[i].getR1().getRankName() + "s");
                    break;
                case Two_pair:
                    System.out.println
                    (hand[i].getR1().getRankName() + "s over " + 
                     hand[i].getR2().getRankName() + "s");
                    break;
                case One_pair:
                    System.out.println
                    ("Pair of " + hand[i].getR2().getRankName() + "s");
                    break;
                case High_cards:
                    System.out.println
                    (hand[i].getR1().getRankName() + "-high");
                    break;
                }
            }

            Arrays.sort(hand);//sort the comparable object hand

            // Find the player number that draw with the highest hand player
            for (int i = player - 2; i >= 0; i--) {
                if (hand[player - 1].compareTo(hand[i]) == 0) {
                    drawPlayer[countDraw] = hand[i].getPlayerNo();
                    countDraw++; // count the number of draw players
                }
            }

            Arrays.sort(drawPlayer); // ascending sort the player number 
            //print result after compare and sort
            if (player > 1) {
                if (countDraw > 1)

                {
                    System.out.print("Players ");
                    for (int i = 0; i < countDraw - 1; i++) {
                        System.out.print(drawPlayer[i] + ", ");
                    }
                    System.out.println(
                            hand[player - 2].getPlayerNo() + " and " + 
                            hand[player - 1].getPlayerNo() + " draw.");
                } else if (countDraw == 1)
                    System.out.println
                    ("Players " + hand[player - 2].getPlayerNo() + 
                     " and "+ hand[player - 1].getPlayerNo() + " draw.");
                else
                    System.out.println
                    ("Player " + hand[player - 1].getPlayerNo() + " wins.");
            }
        } else
            System.out.println
            ("Error: wrong number of arguments; must be a multiple of 5");
        System.exit(args.length);
    }
}