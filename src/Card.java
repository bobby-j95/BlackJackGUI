import org.junit.Test;

//Robert Johns
//Card.java
//October 21, 2017
public class Card {
    public final static int SPADES = 0;       // Codes for the 4 suits.
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;
    public final static int ACE = 1;          // Vales for the non numerical cards
    public final static int KING = 11;        
    public final static int QUEEN = 12;       
    public final static int JACK = 13;
    private final int suit;   // The suit of the card
    private final int value;  // The value of the card                       
    public Card(int cardValue, int cardSuit) {
        value = cardValue;
        suit = cardSuit;
    } 
    public int getSuit() { //Return suit value
        return suit;
    }
    public int getValue() { //Return card value
        return value;
    }
    public String getStringSuit() { ////return card suit unless card is invalid
        switch(suit){
        	case DIAMONDS:
        		return "D";
        	case CLUBS:
        		return "C";
        	case SPADES:
        		return "S";
        	case HEARTS: 
        		return "H";
        	default: 
        		return "??";
        }
    }
    public String getStringValue() { //return card value unless card is invalid
        switch(value){
           case 1:
        	   return "A";
           case 2:
        	   return "2";
           case 3: 
        	   return "3";
           case 4: 
        	   return "4";
           case 5:  
        	   return "5";
           case 6: 
        	   return "6";
           case 7: 
        	   return "7";
           case 8: 
        	   return "8";
           case 9:  
        	   return "9";
           case 10: 
        	   return "10";
           case 11: 
        	   return "K";
           case 12: 
        	   return "Q";
           case 13:
        	   return "J";
           default: 
        	   return "??";
        }
    }
    public String toString() { //return card
        return getStringValue() + getStringSuit();
    }
}