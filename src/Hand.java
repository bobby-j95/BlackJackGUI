//Robert Johns
//Hand.java
//October 21, 2017
import java.util.Vector;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
public class Hand {
	@SuppressWarnings("rawtypes")
	private Vector hand; //the hand for the player and dealer
	@SuppressWarnings("rawtypes")
	public Hand(){
		hand=new Vector(); //creates a hand that is empty
	}
	@SuppressWarnings("unchecked")
	public void addCard(Card c){
		if(c!=null){
			hand.addElement(c); //adds card to hand
		}
	}
	public void clear(){
		hand.removeAllElements(); //clears entire hand
	}
	public Card getCard(int cardPosition){ //gets card at position
		return (Card)hand.elementAt(cardPosition);
	}
	public int getNumberCards(){ //gets the number of cards in hand
		return hand.size();
	}
	public void removeCard(int cardPosition){ //removes card at position
		if(cardPosition>=0 && cardPosition <hand.size()){
			hand.removeElementAt(cardPosition);
		}
	}
	public int handValue() { //gives the values of the hand
		int val=0;      // The value of the hand
		boolean ace=false;  // Checks if hand has ace
		int cardsInHand=hand.size();    // Number of cards in the hand.
		for (int i = 0; i < cardsInHand; i++) {
			Card card;    // The i-th card; 
			int cardVal;  // The blackjack value of the i-th card.
			card = getCard(i);
			cardVal = card.getValue();  // The assigned values
			if (cardVal>10) {// For all nobility cards
				cardVal = 10;   
			}
			if (cardVal==1) { //true if there is an ace in hand
				ace = true;     
			}
			val=val+cardVal; //adds the and values
		}
		if (ace==true  &&  val+10<=21){ //Chooses between the two ace values
			val=val+10;
		}
		return val;
	}
}
