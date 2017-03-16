import java.util.Arrays;

/**
 * @author
 * This class represents a poker hand
 * It contains enum class Classify represents the classification of the hand,
 * five cards form the hand, player number,
 * and special ranks used to print and compare for the hand
 */
public class PokerHand implements Comparable<PokerHand>{
    public static final int N =5;//Number of cards in one hand    
    //Classification of the hand
    public enum Classify
    {
        Straight_flush(9),Four_of_a_kind(8),Full_house(7),
        Flush(6),Straight(5),Three_of_a_kind(4),
        Two_pair(3),One_pair(2),High_cards(1);
        private int v;// Value of classification, used to compare hands
        Classify(int v)
        {this.v=v;}
        public int getV()
        {return this.v;}
    };
    
    private Card[] card = new Card[N]; //store the five cards
    private int playerNo; //store the player number
    private int[] CardVal = new int[N]; //store the rank value of cards
    private Rank[] CardRank = new Rank[N]; //store the rank of cards
    private Classify classify; //classification the hand
    //special ranks in the hand, used to print and compare
    private Rank R1; 
    private Rank R2;
    private Rank R3;
    
    //Contractor of the poker hand
    public PokerHand(Card[] card,int playerNo)
    {
        this.playerNo = playerNo;
        this.card = card;
        
        for (int i=0;i<N;i++)
        {
            CardVal[i] = card[i].getRank().getRankVal();
            CardRank[i] = card[i].getRank();
        }
        Arrays.sort(CardRank);// Sort rank value of cards
        Arrays.sort(CardVal); // Sort rank of cards 
        this.R1 = CardRank[N-1]; //Default set to the highest rank
        this.R2 = CardRank[N-2]; //Default set to the second rank
        this.R3 = CardRank[N-3]; //Default set to the third rank
    }
    
    public Rank getR1()
    {
        return this.R1;
    }
        
    public Rank getR2()
    {
        return this.R2;
    }
    
    public Rank[] getCardRank()
    {
        return this.CardRank;
    }
    
    public Classify getClassify()
    {
        return this.classify;
    }
    
    public int getPlayerNo()
    {
        return playerNo;
    }
    
    //Find straight(continuous ranks) in the hand
    public boolean FindStraight(Card[] card)
    {
        for (int i=0;i<N-1;i++)
        {
            if (CardVal[i+1]-CardVal[i]!=1)
                return false;
        }
        return true;
    }
    
    //Find flush(same suits) in the hand
    public boolean FindFlush(Card[] card)
    {
        for (int i=0;i<N-1;i++)
        {
            if ((card[i].getSuit().equals(card[i+1].getSuit()))!=true)
            return false;
        }
        return true;
    }
    
    //Find four of a kind(four cards with same rank) in the hand
    public boolean FindFourKind(Card[] card)
    {
        for (int i=0;i<N-3;i++)
        {
            if( (CardVal[i]==CardVal[i+1]) &&
                (CardVal[i]==CardVal[i+2]) &&
                (CardVal[i]==CardVal[i+3]) )
            {
                if(i==0) 
                    {
                    this.R1=CardRank[0]; //update R1 as the same four rank
                    this.R2=CardRank[N-1]; //update R2 as the other rank
                    }
                if(i==1) 
                    {
                    this.R1=CardRank[1];
                    this.R2=CardRank[0];
                    }
                return true;
            }
        }
        return false;
      }
    
    //Find full house(three cards in same rank and one pair) in the hand
    public boolean FindFullHouse(Card[] card)
    {
        if( (CardVal[0]==CardVal[1]) &&
            (CardVal[0]==CardVal[2]) &&
            (CardVal[2]!=CardVal[3]) &&
            (CardVal[3]==CardVal[4]) )
        {
            this.R1 = CardRank[0]; //update R1 as the same three rank
            this.R2 = CardRank[3]; //update R2 as the rank of pair
            return true;
        } 
        else if((CardVal[0]==CardVal[1]) &&
                (CardVal[0]!=CardVal[2]) &&
                (CardVal[2]==CardVal[3]) &&
                (CardVal[2]==CardVal[4]) )
        {
            this.R1=CardRank[2];
            this.R2=CardRank[0];
            return true;
        }        
        else return false;    
    }
    
    //Find three of a kind(three cards in same rank) in the hand
    public boolean FindThreeKind(Card[] card)
    {
        for (int i=0;i<N-2;i++)
        {
            if( (CardVal[i]==CardVal[i+1]) &&
                (CardVal[i]==CardVal[i+2]) )
            {
                this.R1=CardRank[i]; //update R1 as the same rank of three cards
                if(i==0) {
                    this.R2=CardRank[4]; //update R2 as the biggest rank in rest 
                    this.R3=CardRank[3]; //update R3 as the last rank
                }
                if(i==1){
                    this.R2=CardRank[4];
                    this.R2=CardRank[0];
                }
                if(i==2){
                    this.R2=CardRank[1];
                    this.R3=CardRank[0];
                }
                return true;
            }
        }
        return false;
    }
    
    //Find the number of pair in the hand
    public int FindPair(Card[] card)
    {
        int n=0;
        for(int i=0;i<N-1;i++)
        {
            if (CardVal[i]==CardVal[i+1])
            {
                n++;
                if(n==1)
                {
    //update R2 as highest rank if one pair; second high rank in pair if two
                this.R2=CardRank[i]; 
                } 
                if(n==2)
                {
                this.R1=CardRank[i]; // update R1 as highest in pair if two
                } 
            }
        }
        if(n==2)
        {
            for(int i=0;i<N;i++)
            {    
                if((CardVal[i]!=this.R1.getRankVal())&&
                        (CardVal[i]!=this.R2.getRankVal()))
                this.R3=CardRank[i];//update R3 as the last rank if two pairs 
            }
        }
        return n;
    }
    
    //Find the classify of the hand
    public Classify FindClass()
    {
        if(FindStraight(card)==true&&FindFlush(card)==true)
        {
            classify=Classify.Straight_flush;
        }    
        else if(FindFourKind(card)==true)
        {
            classify=Classify.Four_of_a_kind;
        }
        else if(FindFullHouse(card)==true)
        {
            classify=Classify.Full_house;
        }    
        else if(FindStraight(card)==false&&FindFlush(card)==true)
        {
            classify=Classify.Flush;
        }
        else if(FindStraight(card)==true&&FindFlush(card)==false)
        {
            classify=Classify.Straight;
        }        
        else if(FindThreeKind(card)==true)
        {
            classify=Classify.Three_of_a_kind;
        }
        else if(FindPair(card)==2)
        {
            classify=Classify.Two_pair;
        }
        else if(FindPair(card)==1)
        {
            classify=Classify.One_pair;
        }
        else 
        {
            classify=Classify.High_cards;
        }
        return classify;
        }
    
    //override the compareTo method of the class so that comparable
    @Override
    public int compareTo(PokerHand hand) {
        int n;
        //compare the value of classification first
        if (this.getClassify().getV()>hand.getClassify().getV())
        n = 1;
        //compare when they are same classification
            else if (this.getClassify().getV()==hand.getClassify().getV())
            {
                switch (this.getClassify())
                {
                case Straight_flush:
                case Straight:
                    if(this.R1.getRankVal()>hand.R1.getRankVal())
                        n = 1;
                    else if(this.getR1().getRankVal()==hand.R1.getRankVal())
                    {
                        n = 0;
                    }
                    else n = -1;
                    break;
                case Four_of_a_kind:
                case Full_house:
                    if(this.R1.getRankVal()>hand.R1.getRankVal())
                        n = 1;
                    else if(this.R1.getRankVal()==hand.R1.getRankVal())
                    {
                        if(this.R2.getRankVal()>hand.R2.getRankVal())
                            n = 1;
                        else if (this.R2.getRankVal()==hand.R2.getRankVal())
                            n = 0;
                        else n = -1;
                    }
                    else n = -1;
                    break;
                case Flush:
                case High_cards:
                    n = 0;
                    for(int i=(N-1);i>=0;i--)
                    {
        if(this.CardRank[i].getRankVal()>hand.CardRank[i].getRankVal())
                        {
                            n = 1;
                            break;
                        }
        else if(this.CardRank[i].getRankVal()==hand.CardRank[i].getRankVal())
                        continue;
                    else 
                    {
                        n = -1;
                        break;
                    }
                    }
                    break;
                case Three_of_a_kind:
                case Two_pair:
                    if(this.R1.getRankVal()>hand.R1.getRankVal())
                        n = 1;
                    else if(this.R1.getRankVal()==hand.R1.getRankVal())
                    {
                        if(this.R2.getRankVal()>hand.R2.getRankVal())
                            n = 1;
                        else if (this.R2.getRankVal()==hand.R2.getRankVal())
                        {
                            if(this.R3.getRankVal()>hand.R3.getRankVal())
                                n = 1;
                            else if (this.R3.getRankVal()==hand.R3.getRankVal())
                                n = 0;
                            else n = -1;
                        }
                        else n = -1;
                    }
                    else n = -1;
                    break;
                case One_pair:
                    if(this.R2.getRankVal()>hand.R2.getRankVal())
                        n = 1;
                    else if(this.R2.getRankVal()==hand.R2.getRankVal())
                    {
                        if(this.R1.getRankVal()>hand.R1.getRankVal())
                            n = 1;
                        else if (this.R1.getRankVal()==hand.R1.getRankVal())
                            n = 0;
                        else n = -1;
                    }
                    else n = -1;
                    break;
                default: n = 0;
                break;
                }
            }
            else n = -1;
            return n;
    }
 }
