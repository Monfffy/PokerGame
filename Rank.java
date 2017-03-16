
/**
 * @author 
 * This enum class represents the rank of cards
 * It has RankName for print; RankVal for compare and sort
 */
public enum Rank {
    TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), 
    SEVEN(7, "7"), EIGHT(8, "8"), NINE(9,"9"), TEN(10, "10"), 
    JACK(11, "Jack"), QUEEN(12, "Queen"), KING(13, "King"), ACE(14, "Ace");
    
    private String RankName; //For print
    private int RankVal; //For compare and sort of cards

    Rank(int RankVal, String RankName) {
        this.RankVal = RankVal;
        this.RankName = RankName;
    }

    public int getRankVal() {
        return this.RankVal;
    }

    public String getRankName() {
        return this.RankName;
    }
};
