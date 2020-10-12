//Robert Johns
//BlackjackUI.java
//December 6, 2017
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

class BlackjackGUI extends Applet {

	   public void initialization() {
	   
	         // The init() method lays out the applet using a BorderLayout.
	         // A BlackJackCanvas occupies the CENTER position of the layout.
	         // On the bottom is a panel that holds three buttons.  The
	         // HighLowCanvas object listens for ActionEvents from the buttons
	         // and does all the real work of the program.
	         
	      setBackground( new Color(130,50,40) );
	      setLayout( new BorderLayout(3,3) );
	      
	      BlackjackCanvas board = new BlackjackCanvas();
	      add(board, BorderLayout.CENTER);
	      
	      Panel buttonPanel = new Panel();
	      buttonPanel.setBackground( new Color(220,200,180) );
	      add(buttonPanel, BorderLayout.SOUTH);
	      
	      Button higher = new Button( "Hit!" );
	      higher.addActionListener(board);
	      higher.setBackground(Color.lightGray);
	      buttonPanel.add(higher);
	      
	      Button lower = new Button( "Stand!" );
	      lower.addActionListener(board);
	      lower.setBackground(Color.lightGray);
	      buttonPanel.add(lower);
	      
	      Button newGame = new Button( "New Game" );
	      newGame.addActionListener(board);
	      newGame.setBackground(Color.lightGray);
	      buttonPanel.add(newGame);
	      
	   }  // end init()
	   
	   public Insets getInsets() {
	         // Specify how much space to leave between the edges of
	         // the applet and the components it contains.  The background
	         // color shows through in this border.
	      return new Insets(3,3,3,3);
	   }

	} // end class HighLowGUI

public class BlackjackUI extends Canvas implements ActionListener{
	BlackjackUI() {
        // Constructor.  Creates fonts and starts the first game.
     setBackground( new Color(0,120,0) );
     Font smallFont = new Font("SansSerif", Font.PLAIN, 12);
     Font bigFont = new Font("Serif", Font.BOLD, 14);
     main(null);
  }
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Deck deck;
		int money=0; // Amount of money the user has.
		int bet=0; // Amount user bets on a game.
		boolean hitSoft;
		boolean isReady=false;
		boolean testing=true;
		String name=null;
		String answer=null;
		Scanner s= new Scanner(System.in);
		System.out.println("Welcome to BlackJack...first a few preliminaries");
		System.out.println("Enter your name (no spaces)");
		name=s.nextLine();
		int deckNumber=0;
		if (testing==false){
			System.out.println("How many decks are being used? (1-4)");
			deckNumber=s.nextInt();
		}
		System.out.println("Now finally, does the dealer have to hit on soft 17? (Y or N)");
		answer=s.next();
		if(answer=="Y"){
			hitSoft=true;
		}else{
			hitSoft=false;
		}
		money = 1000; // User starts with $1000.
		Hand dealerHand = new Hand(); // The dealer's hand.
		Hand playerHand = new Hand(); // The user's hand.
		while(true){
			System.out.println(name+", you have $" + money + ".");
			do {
				System.out.println("How many dollars do you want to bet? (Enter 0 to end the game.)");
				bet=s.nextInt();
				deck = new Deck(deckNumber, testing);
				if (bet< 0||bet>money){
					System.out.println("Your value must be between $0 and $" + money + '.');
				}
			} while (bet < 0 || bet > money);
			if (bet == 0){
				break;
			}
			money=money-bet;
			deals(testing, deck, playerHand, dealerHand);
			System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
			System.out.println("Dealer: " + dealerHand.getCard(0)+ " XX");
			System.out.print(name+": ");
			for(int x = 0; x < playerHand.getNumberCards(); x++){
				System.out.print(" " + playerHand.getCard(x));
			}
			System.out.println(" (" + playerHand.handValue()+")");
			if (dealerHand.handValue() == 21) { //Checks if dealer has blackjack initially
				//System.out.println("Dealer has "+dealerHand.handValue()+", you have "+playerHand.handValue()+". Sorry, you lose.");
				System.out.println("Dealer has Blackjack. Dealer wins.");
				System.out.println(name+", you have $" + money + ".");
				do {
					System.out.println("How many dollars do you want to bet? (Enter 0 to end the game.)");
					bet=s.nextInt();
					deck = new Deck(deckNumber, testing);
					if (bet< 0||bet>money){
						System.out.println("Your value must be between $0 and $" + money + '.');
					}
				} while (bet < 0 || bet > money);
				if (bet == 0){
					break;
				}
				playerHand.clear();
				dealerHand.clear();
				money=money-bet;
				deals(testing, deck, playerHand, dealerHand);
				System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
				System.out.println("Dealer: " + dealerHand.getCard(0)+ " XX");
				System.out.print(name+": ");
				for(int x = 0; x < playerHand.getNumberCards(); x++){
					System.out.print(" " + playerHand.getCard(x));
				}
				System.out.println(" (" + playerHand.handValue()+")");
			}
			if (playerHand.handValue() == 21){ //Checks if player has blackjack initially
				//System.out.println("You have "+playerHand.handValue()+", and the Dealer has "+dealerHand.handValue()+". You Win!!!");
				System.out.println("You have Blackjack. You win.");
				money = money + 2*bet;
				System.out.println(name+", you have $" + money + ".");
				do {
					System.out.println("How many dollars do you want to bet? (Enter 0 to end the game.)");
					bet=s.nextInt();
					deck = new Deck(deckNumber, testing);
					if (bet< 0||bet>money){
						System.out.println("Your value must be between $0 and $" + money + '.');
					}
				} while (bet < 0 || bet > money);
				if (bet == 0){
					break;
				}
				playerHand.clear();
				dealerHand.clear();
				money=money-bet;
				deals(testing, deck, playerHand, dealerHand);
				System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
				System.out.println("Dealer: " + dealerHand.getCard(0)+ " XX");
				System.out.print(name+": ");
				for(int x = 0; x < playerHand.getNumberCards(); x++){
					System.out.print(" " + playerHand.getCard(x));
				}
				System.out.println(" (" + playerHand.handValue()+")");
			}
			char userAction; // Gets the User's option
			userAction=userOption();
			while(userAction!='X'){
				if (userAction == 'S'){ //when the user wants to stay
					int shoeChange=deck.cardsLeft();
					while(dealerHand.handValue()<17){
						Card newCard = deck.dealCard();
						dealerHand.addCard(newCard);
						if(hitSoft){
							for(int g=0; g<dealerHand.getNumberCards();g++){
								if(dealerHand.getCard(g).getStringValue()=="A"){
									newCard = deck.dealCard();
									dealerHand.addCard(newCard);
								}
							}
						}
					}
					if(shoeChange!=deck.cardsLeft()){
						System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
					}
					System.out.print("Dealer:");
					for(int y = 0; y < dealerHand.getNumberCards(); y++){
						System.out.print(" " + dealerHand.getCard(y));
					}
					System.out.println();
					System.out.print(name+": ");
					for(int x = 0; x < playerHand.getNumberCards(); x++){
						System.out.print(" " + playerHand.getCard(x));
					}
					System.out.println(" (" + playerHand.handValue()+")");
					if (dealerHand.handValue() == playerHand.handValue()){ //if both dealer and player have an equal hand
						System.out.println("It's a tie.");
						money=money+bet;	
					}else if (dealerHand.handValue() > playerHand.handValue() && dealerHand.handValue()<=21 || playerHand.handValue()>21){ //if dealer was closer to 21
						System.out.println("Dealer has "+dealerHand.handValue()+", you have "+playerHand.handValue()+". Sorry, you lose.");
					}else{ //if player was closer to 21
						System.out.println("You have "+playerHand.handValue()+", and the Dealer has "+dealerHand.handValue()+". You Win!!!");
						money=money+bet*2;
					}
					isReady=true;
				}else if (userAction == 'H'){ //if the user Hits, checks if they bust.
					Card newCard = deck.dealCard();
					playerHand.addCard(newCard);
					System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
					System.out.println("Dealer: " + dealerHand.getCard(0)+ " XX");
					System.out.print(name+": ");
					for(int x = 0; x < playerHand.getNumberCards(); x++){
						System.out.print(" " + playerHand.getCard(x));
					}
					System.out.println(" (" + playerHand.handValue()+")");
					if (playerHand.handValue() > 21) {
						System.out.println("Sorry... you're busted.");
						isReady=true;
					}else{
						userAction=userOption();
					}
				}else if(userAction == 'D'){//if the user wants to double down
					money=money-bet;
					bet=bet*2;
					Card newCard = deck.dealCard();
					playerHand.addCard(newCard);
					int shoeChange=deck.cardsLeft();
					while(dealerHand.handValue()<17){
						newCard = deck.dealCard();
						dealerHand.addCard(newCard);
						if(hitSoft){
							for(int g=0; g<dealerHand.getNumberCards();g++){
								if(dealerHand.getCard(g).getStringValue()=="A"){
									newCard = deck.dealCard();
									dealerHand.addCard(newCard);
								}
							}
						}
					}
					if(shoeChange!=deck.cardsLeft()){
						System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
					}
					System.out.print("Dealer:");
					for(int y = 0; y < dealerHand.getNumberCards(); y++){
						System.out.print(" " + dealerHand.getCard(y));
					}
					System.out.print(name+": ");
					for(int x = 0; x < playerHand.getNumberCards(); x++){
						System.out.print(" " + playerHand.getCard(x));
					}
					System.out.println(" (" + playerHand.handValue()+")");
					if (dealerHand.handValue() == playerHand.handValue()){ //if both dealer and player have an equal hand
						System.out.println("It's a tie.");
						money=money+bet;
					}else if (dealerHand.handValue() > playerHand.handValue() && dealerHand.handValue()<=21 || playerHand.handValue()>21){ //if dealer was closer to 21
						System.out.println("Dealer has "+dealerHand.handValue()+", you have "+playerHand.handValue()+". Sorry, you lose.");
					}else{ //if player was closer to 21
						System.out.println("You have "+playerHand.handValue()+", and the Dealer has "+dealerHand.handValue()+". You Win!!!");
						money=money+bet*2;
					}
					isReady=true;
				}
				if(isReady){
					playerHand.clear();
					dealerHand.clear();
					System.out.println(name+", you have $" + money + ".");
					do {
						System.out.println("How many dollars do you want to bet? (Enter 0 to end the game.)");
						bet=s.nextInt();
						deck = new Deck(deckNumber, testing);
						if (bet< 0||bet>money){
							System.out.println("Your value must be between $0 and $" + money + '.');
						}
					} while (bet < 0 || bet > money);
					if (bet == 0){
						break;
					}
					money=money-bet;
					deals(testing, deck, playerHand, dealerHand);
					if (dealerHand.handValue() == 21) { //Checks if dealer has blackjack initially
						//System.out.println("Dealer has "+dealerHand.handValue()+", you have "+playerHand.handValue()+". Sorry, you lose.");
						System.out.println("Dealer has Blackjack. Dealer wins.");
						System.out.println(name+", you have $" + money + ".");
						do {
							System.out.println("How many dollars do you want to bet? (Enter 0 to end the game.)");
							bet=s.nextInt();
							deck = new Deck(deckNumber, testing);
							if (bet< 0||bet>money){
								System.out.println("Your value must be between $0 and $" + money + '.');
							}
						} while (bet < 0 || bet > money);
						if (bet == 0){
							break;
						}
						playerHand.clear();
						dealerHand.clear();
						money=money-bet;
						deals(testing, deck, playerHand, dealerHand);
						System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
						System.out.println("Dealer: " + dealerHand.getCard(0)+ " XX");
						System.out.print(name+": ");
						for(int x = 0; x < playerHand.getNumberCards(); x++){
							System.out.print(" " + playerHand.getCard(x));
						}
						System.out.println(" (" + playerHand.handValue()+")");
					}
					if (playerHand.handValue() == 21){ //Checks if player has blackjack initially
						//System.out.println("You have "+playerHand.handValue()+", and the Dealer has "+dealerHand.handValue()+". You Win!!!");
						System.out.println("You have Blackjack. You win.");
						money = money + 2*bet;
						System.out.println(name+", you have $" + money + ".");
						do {
							System.out.println("How many dollars do you want to bet? (Enter 0 to end the game.)");
							bet=s.nextInt();
							deck = new Deck(deckNumber, testing);
							if (bet< 0||bet>money){
								System.out.println("Your value must be between $0 and $" + money + '.');
							}
						} while (bet < 0 || bet > money);
						if (bet == 0){
							break;
						}
						playerHand.clear();
						dealerHand.clear();
						money=money-bet;
						deals(testing, deck, playerHand, dealerHand);
						System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
						System.out.println("Dealer: " + dealerHand.getCard(0)+ " XX");
						System.out.print(name+": ");
						for(int x = 0; x < playerHand.getNumberCards(); x++){
							System.out.print(" " + playerHand.getCard(x));
						}
						System.out.println(" (" + playerHand.handValue()+")");
					}
					System.out.println("Shoe now has "+deck.cardsLeft()+" cards!");
					System.out.println("Dealer: " + dealerHand.getCard(0)+ " XX");
					System.out.print(name+": ");
					for(int x = 0; x < playerHand.getNumberCards(); x++){
						System.out.print(" " + playerHand.getCard(x));
					}
					System.out.println(" (" + playerHand.handValue()+")");
					userAction=userOption();
				isReady=false;
				}
			}
			if(userAction == 'X'){//if the user wants to exit
				break;
			}
			if (money == 0) {
				System.out.println("You ran out of money!");
				break;
			}
			playerHand.clear();
			dealerHand.clear();
		}
		System.out.println();
		System.out.println("You leave with $" + money + '.');
	} // end main()
	@SuppressWarnings("resource")
	public static char userOption(){
		char u;
		Scanner s= new Scanner(System.in);
		System.out.println("Options: Hit (H), Stand (S), Doubledown (D), Exit (X) ");
		do {
			u = Character.toUpperCase(s.next().charAt(0));
			if (u != 'H' && u != 'S' && u != 'D' && u != 'X'){
				System.out.println("Options: Hit (H), Stand (S), Doubledown (D), Exit (X) ");
			}
		}while (u != 'H' && u != 'S' && u != 'D' && u != 'X');
		return u;
	}
	public static void deals(boolean f, Deck d, Hand pH, Hand dH){
		if (f==false){
			d.shuffle();
			dH.addCard(d.dealCard());
			pH.addCard(d.dealCard());
			dH.addCard(d.dealCard());
			pH.addCard(d.dealCard());
		}else{
			dH.addCard(d.dealCard());
			pH.addCard(d.dealCard());
			dH.addCard(d.dealCard());
			pH.addCard(d.dealCard());
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}