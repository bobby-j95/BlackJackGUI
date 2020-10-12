//Robert Johns
//BlackjackGame.java
//October 21, 2017
import java.io.*;
import java.util.*;

public class BlackjackGame {
	static Deck deck; // A deck of cards. A new deck for each game.
	static int betValue;
	static Hand dealerHand = new Hand(); // The dealer's hand.
	static Hand playerHand = new Hand(); // The user's hand.
	public static void setValues(int d, int b){
		deck = new Deck(d);
		betValue=b;
	}
	static boolean playBlackjack() {
		Scanner s= new Scanner(System.in);
		deck.shuffle();
		dealerHand.addCard(deck.dealCard());
		playerHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());
		playerHand.addCard(deck.dealCard());
		System.out.println();
		System.out.println();
		if (dealerHand.handValue() == 21) { //Checks if dealer has blackjack initially
			System.out.println("Dealer has the " + dealerHand.getCard(0) + " and the " + dealerHand.getCard(1) + ".");
			System.out.println("User has the " + playerHand.getCard(0) + " and the " + playerHand.getCard(1) + ".");
			System.out.println();
			System.out.println("Dealer has Blackjack. Dealer wins.");
			return false;
		}
		if (playerHand.handValue() == 21){ //Checks if player has blackjack initially
			System.out.println("Dealer has the " + dealerHand.getCard(0) + " and the " + dealerHand.getCard(1) + ".");
			System.out.println("User has the " + playerHand.getCard(0) + " and the " + playerHand.getCard(1) + ".");
			System.out.println();
			System.out.println("You have Blackjack. You win.");
			return true;
		}
		while (true) { //shows the users cards and offers options
			System.out.println();
			System.out.println();
			System.out.println("Your cards are:");
			for(int i = 0; i < playerHand.getNumberCards(); i++){
				System.out.println(" " + playerHand.getCard(i));
			}
			System.out.println("Your total is " + playerHand.handValue());
			System.out.println();
			System.out.println("Dealer is showing the " + dealerHand.getCard(0));
			System.out.println();
			System.out.println("Options: Hit (H), Stand (S), Doubledown (D), Exit (X) ");
			char userAction; // User's response, 'H' or 'S' or 'D' or 'X'.
			do {
				userAction = Character.toUpperCase(s.next().charAt(0));
				if (userAction != 'H' && userAction != 'S'){
					System.out.println("Options: Hit (H), Stand (S), Doubledown (D), Exit (X) ");
				}
			}while (userAction != 'H' && userAction != 'S' && userAction != 'D' && userAction != 'X');

			/* If the user Hits, the user gets a card. If the user Stands,
			the loop ends (and it's the dealer's turn to draw cards).
			*/
			if (userAction == 'S'){ //when the user wants to stay
				break;
			}else if (userAction == 'H'){ //if the user Hits, checks if they bust.
				Card newCard = deck.dealCard();
				playerHand.addCard(newCard);
				System.out.println();
				System.out.println("User hits.");
				System.out.println("Your card is the " + newCard);
				System.out.println("Your total is now " + playerHand.handValue());
				if (playerHand.handValue() > 21) {
					System.out.println();
					System.out.println("You busted by going over 21. You lose.");
					System.out.println("Dealer's other card was the " + dealerHand.getCard(1));
					return false;
				}
			}else if(userAction == 'D'){
				betValue=betValue*2;
				Card newCard = deck.dealCard();
				playerHand.addCard(newCard);
				System.out.println();
				break;
			}else if(userAction == 'X'){
				return false;
			}

		} // end while loop
		System.out.println();
		System.out.println("User stands.");
		System.out.println("Dealer's cards are");
		System.out.println(" " + dealerHand.getCard(0));
		System.out.println(" " + dealerHand.getCard(1));
		while (dealerHand.handValue() <= 16){
			Card newCard = deck.dealCard();
			System.out.println("Dealer hits and gets the " + newCard);
			dealerHand.addCard(newCard);
			if (dealerHand.handValue() > 21){
				System.out.println();
				System.out.println("Dealer busted by going over 21. You win.");
				return true;
			}
		}
		System.out.println("Dealer's total is " + dealerHand.handValue());
		System.out.println();
		if (dealerHand.handValue() == playerHand.handValue()){ //if both dealer and player have an equal hand
			System.out.println("Dealer wins on a tie. You lose.");
			return false;
		}else if (dealerHand.handValue() > playerHand.handValue()){ //if dealer was closer to 21
			System.out.println("Dealer wins, " + dealerHand.handValue() + " points to " + playerHand.handValue() + ".");
			return false;
		}else{ //if player was closer to 21
			System.out.println("You win, " + playerHand.handValue() + " points to " + dealerHand.handValue() + ".");
			return true;
		}
	}
	public int getPocket(){
		return betValue;
	}
}