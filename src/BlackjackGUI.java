//Robert Johns
//BlackjackGUI.java
//December 6, 2017
/*
   In this applet, the user plays a game of Blackjack.  The
   computer acts as the dealer.  The user plays by clicking
   "Hit!" and "Stand!" buttons.
   
   The programming of this applet assumes that the applet is
   set up to be about 466 pixels wide and about 346 pixels high.
   That width is just big enough to show 2 rows of 5 cards.
   The height is probably a little bigger than necessary,
   to allow for variations in the size of buttons from one platform
   to another.
   
   This file defines two classes, the applet class BlackJackGUI
   and a class BlackJackCanvas which is used in the applet.
   (This is slightly bad style, but is OK since the BlackJackCanvas
   class is not public and is not used outside this file.)
*/

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Scanner;

public class BlackjackGUI extends Applet {

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
   
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
		
		Button middle = new Button( "Double Down!" );
		middle.addActionListener(board);
		middle.setBackground(Color.lightGray);
		buttonPanel.add(middle);
      
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


class BlackjackCanvas extends Canvas implements ActionListener {

	// A class that displays the card game and does all the work
	// of keeping track of the state and responding to user events.

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Deck deck;         // A deck of cards to be used in the game.
    int bet=0; //Amount they will bet.
	Hand dealerHand;   // Hand containing the dealer's cards.
	Hand playerHand;   // Hand containing the user's cards.
	boolean hitSoft;   //boolean for hitSoft.
	String message;  // A message drawn on the canvas, which changes to reflect the state of the game.
    String name;     // Name of Person.    
    int money=1000; //Amount of money user has.                
	boolean gameInProgress;  // Set to true when a game begins and to false when the game ends.
	Font bigFont;      // Font that will be used to display the message.
	Font smallFont;    // Font that will be used to draw the cards.
   

	BlackjackCanvas() {
		// Constructor.  Creates fonts and starts the first game.
		setBackground( new Color(0,120,0) );
		smallFont = new Font("SansSerif", Font.PLAIN, 12);
		bigFont = new Font("Serif", Font.BOLD, 14);
		doNewGame();
	}	
   

	public void actionPerformed(ActionEvent evt) {
		// Respond when the user clicks on a button by calling
		// the appropriate procedure.  Note that the canvas is
		// registered as a listener in the BlackjackGUI class.
		String command = evt.getActionCommand();
		if (command.equals("Hit!")) {
			doHit();
		}else if (command.equals("Double Down!")) {
			doDouble();
		}else if (command.equals("Stand!")) {
			doStand();
		}else if (command.equals("New Game")) {
			doNewGame();
		}
	}
   

	void doHit() {
		// This method is called when the user clicks the "Hit!" button.
		// First check that a game is actually in progress.  If not, give
		// an error message and exit.  Otherwise, give the user a card.
		// The game can end at this point if the user goes over 21 or
		// if the user has taken 5 cards without going over 21.
		if (gameInProgress == false) {
			message = "Click \"New Game\" to start a new game.";
			repaint();
			return;
		}
		playerHand.addCard( deck.dealCard() );
		if ( playerHand.handValue() > 21 ) {
			message = "You've busted!  Sorry, you lose.";
			gameInProgress = false;
		}else {
			message = "You have " + playerHand.handValue() + ".  Hit, Stand, or Double Down?";
		}
		repaint();
	}
	
	void doDouble() {
		// This method is called when the user clicks the "Double Down!" button.
		// First check that a game is actually in progress.  If not, give
		// an error message and exit.  Otherwise, give the user a card.
		// The game can end at this point if the user goes over 21 or
		// if the user has taken 5 cards without going over 21.
		if (gameInProgress == false) {
			message = "Click \"New Game\" to start a new game.";
			repaint();
			return;
		}
		money=money-bet;
		bet=bet*2;
		Card newCard = deck.dealCard();
		playerHand.addCard(newCard);
		//int shoeChange=deck.cardsLeft();
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
		if (dealerHand.handValue() == playerHand.handValue()){ //if both dealer and player have an equal hand
			message="Shoe now has "+deck.cardsLeft()+" cards!\n It's a tie.";
			money=money+bet;	
		}else if (dealerHand.handValue() > playerHand.handValue() && dealerHand.handValue()<=21 || playerHand.handValue()>21){ //if dealer was closer to 21
			message="Shoe now has "+deck.cardsLeft()+" cards!\n Dealer has "+dealerHand.handValue()+", you have "+playerHand.handValue()+". Sorry, you lose.";
		}else{ //if player was closer to 21
			message="Shoe now has "+deck.cardsLeft()+" cards!\n You have "+playerHand.handValue()+", and the Dealer has "+dealerHand.handValue()+". You Win!!!";
			money=money+bet*2;
		}
		gameInProgress = false;
		repaint();
	}
   

	void doStand() {
		// This method is called when the user clicks the "Stand!" button.
		// Check whether a game is actually in progress.  If it is,
		// the game ends.  The delear takes cards until either the
		// dealer has 5 cards or more than 21 points.  Then the 
		// winner of the game is determined.
		if (gameInProgress == false) {
			message = "Click \"New Game\" to start a new game.";
			repaint();
			return;
		}
		gameInProgress = false;
		/*while (dealerHand.handValue() <= 16 && dealerHand.getNumberCards() < 5)
			dealerHand.addCard( deck.dealCard() );
		if (dealerHand.handValue() > 21)
			message = "You win!  Dealer has busted with " + dealerHand.handValue() + ".";
		else if (dealerHand.getNumberCards() == 5)
			message = "Sorry, you lose.  Dealer took 5 cards without going over 21.";
		else if (dealerHand.handValue() > playerHand.handValue())
			message = "Sorry, you lose, " + dealerHand.handValue() + " to " + playerHand.handValue() + ".";
		else if (dealerHand.handValue() == playerHand.handValue())
			message = "Sorry, you lose.  Dealer wins on a tie.";
		else
			message = "You win, " + playerHand.handValue() + " to " + dealerHand.handValue() + "!"; 
		int shoeChange=deck.cardsLeft(); */
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
			/*if(shoeChange!=deck.cardsLeft()){
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
			System.out.println(" (" + playerHand.handValue()+")");*/
			if (dealerHand.handValue() == playerHand.handValue()){ //if both dealer and player have an equal hand
				message="Shoe now has "+deck.cardsLeft()+" cards!\n It's a tie.";
				money=money+bet;	
			}else if (dealerHand.handValue() > playerHand.handValue() && dealerHand.handValue()<=21 || playerHand.handValue()>21){ //if dealer was closer to 21
				message="Shoe now has "+deck.cardsLeft()+" cards!\n Dealer has "+dealerHand.handValue()+", you have "+playerHand.handValue()+". Sorry, you lose.";
			}else{ //if player was closer to 21
				message="Shoe now has "+deck.cardsLeft()+" cards!\n You have "+playerHand.handValue()+", and the Dealer has "+dealerHand.handValue()+". You Win!!!";
				money=money+bet*2;
			}
		repaint();
	}
   
	void getInfo(){
		message = "What is your name?";
		TextField textfield1 = new TextField("Enter Name Here!");
		name=textfield1.getText();
		message = "Implement Soft 17? (Yes/No)";
		TextField textfield2 = new TextField("Yes or No Here!");
		String temp=null;
		temp=textfield2.getText();
		if(temp.equals("Yes")){
			hitSoft=true;
		}else{
			hitSoft=false;
		}
		message = "How much do you want to bet?";
		TextField textfield3 = new TextField("Only Whole Numbers Here!");
		bet=Integer.parseInt(textfield3.getText());
		repaint();
	}

	void doNewGame() {
		// Called by the constructor, and called by actionPerformed() if
		// the use clicks the "New Game" button.  Start a new game.
		// Deal two cards to each player.  The game might end right then
		// if one of the players had blackjack.  Otherwise, gameInProgress
		// is set to true and the game begins.
		if (gameInProgress) {
			// If the current game is not over, it is an error to try
			// to start a new game.
			message = "You still have to finish this game!";
			repaint();
			return;
		}
		deck = new Deck(1, false);   // Create the deck and hands to use for this game.
		dealerHand = new Hand();
		playerHand = new Hand();
		boolean beginGame=true;
		if(beginGame){
			getInfo();
			beginGame=false;
		}
		deck.shuffle();
		dealerHand.addCard( deck.dealCard() );  // Deal two cards to each player.
		playerHand.addCard( deck.dealCard() );
		dealerHand.addCard( deck.dealCard() );
		playerHand.addCard( deck.dealCard() );
		if (dealerHand.handValue() == 21) {
			message = "Sorry, you lose.  Dealer has Blackjack.";
			gameInProgress = false;
		} else if (playerHand.handValue() == 21) {
			message = "You win!  You have Blackjack.";
			gameInProgress = false;
		} else {
			message = "You have " + playerHand.handValue() + ".  Hit, Stand, or Double Down?";
			gameInProgress = true;
		}
		repaint();
	}  // end newGame();

   
	public void paint(Graphics g) {
		// The paint method shows the message at the bottom of the
		// canvas, and it draws all of the dealt cards spread out
		// across the canvas.
		
		g.setFont(bigFont);
		g.setColor(Color.green);
		g.drawString(message, 10, getSize().height - 10);
      
		g.drawString("Your Money: $ "+money, 10, getSize().height - 30);
		// Draw labels for the two sets of cards.
      
		g.drawString("Dealer's Cards:", 10, 23);
		g.drawString("Your Cards:", 10, 153);
      
		// Draw dealer's cards.  Draw first card face down if
		// the game is still in progress,  It will be revealed
		// when the game ends.
      
		g.setFont(smallFont);
		if (gameInProgress){
			drawCard(g, null, 10, 30);
		}else{
			drawCard(g, dealerHand.getCard(0), 10, 30);
		}
		for (int i = 1; i < dealerHand.getNumberCards(); i++){
			drawCard(g, dealerHand.getCard(i), 10 + i * 90, 30);
		} 
		// Draw the user's cards.

		for (int i = 0; i < playerHand.getNumberCards(); i++){
			drawCard(g, playerHand.getCard(i), 10 + i * 90, 160);
		}
	}  // end paint();
   
	public void clear(){ 
		//Clears out and recolors whole screen.
		Graphics g = getGraphics(); 
		Dimension d = getSize(); 
		Color c = getBackground(); 
		g.setColor(c); 
		g.fillRect(0,0,d.width,d.height); 
		repaint(); 
	} //end clear()

	void drawCard(Graphics g, Card card, int x, int y) {
		// Draws a card as a 80 by 100 rectangle with
		// upper left corner at (x,y).  The card is drawn
		// in the graphics context g.  If card is null, then
		// a face-down card is drawn.  (The cards are 
		// rather primitive.)
		if (card == null) {  
			// Draw a face-down card
			g.setColor(Color.blue);
			g.fillRect(x,y,80,100);
			g.setColor(Color.white);
			g.drawRect(x+3,y+3,73,93);
			g.drawRect(x+4,y+4,71,91);
		}else {
			g.setColor(Color.white);
			g.fillRect(x,y,80,100);
			g.setColor(Color.gray);
			g.drawRect(x,y,79,99);
			g.drawRect(x+1,y+1,77,97);
			if (card.getSuit() == Card.DIAMONDS || card.getSuit() == Card.HEARTS){
				g.setColor(Color.red);
			}else{
				g.setColor(Color.black);
			}
			g.drawString(card.getStringValue(), x + 10, y + 30);
         	g.drawString("of", x+ 10, y + 50);
         	g.drawString(card.getStringSuit(), x + 10, y + 70);
		}
	}  // end drawCard()
} // end class BlackJackCanvas