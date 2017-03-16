
/**
 * @author 
 * This class is used to represent poker cards
 * Each card has rank and suit
 */
public class Card {

    private Rank rank;
    private Suit suit;;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    };

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

}