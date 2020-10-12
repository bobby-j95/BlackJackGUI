import java.io.*;
import java.nio.file.*;

import org.junit.Test;

//Robert Johns
//Deck.java
//October 21, 2017
public class Deck {
	private Card[] deck; // creates the array of decks
	private int cardsUsed; // number of cards used
	private int numberofDecks; // total number of decks
	private int cardsInShoe; // total cards in deck at the appropriate time

	public Deck(int x, boolean test) { // Create an unshuffled deck of cards.
		numberofDecks = x;
		deck = new Card[numberofDecks * 52];
		int cardCount = 0; // How many cards have been created so far.
		if (test == false) {
			for (int s = 0; s <= 3; s++) {
				for (int v = 1; v <= 13; v++) {
					for (int t = 0; t < numberofDecks; t++) {
						deck[cardCount] = new Card(v, s);
						cardCount++;
					}
				}
			}
		} else {
			String line = null;
			int suit = 0;
			int value = 0;
			Path file = Paths.get("C:\\Users\\Robert\\workspace\\project_01\\src\\deck.txt");
			try (InputStream in = Files.newInputStream(file);
					BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
				while ((line = reader.readLine()) != null) {
					if (line.length() == 2) {
						if (line.charAt(0) == 'A') {
							value = 1;
							if (line.charAt(1) == 'D') {
								suit = 2;
							} else if (line.charAt(1) == 'H') {
								suit = 1;
							} else if (line.charAt(1) == 'S') {
								suit = 0;
							} else {
								suit = 3;
							}
						} else if (line.charAt(0) == 'K') {
							value = 11;
							if (line.charAt(1) == 'D') {
								suit = 2;
							} else if (line.charAt(1) == 'H') {
								suit = 1;
							} else if (line.charAt(1) == 'S') {
								suit = 0;
							} else {
								suit = 3;
							}
						} else if (line.charAt(0) == 'Q') {
							value = 12;
							if (line.charAt(1) == 'D') {
								suit = 2;
							} else if (line.charAt(1) == 'H') {
								suit = 1;
							} else if (line.charAt(1) == 'S') {
								suit = 0;
							} else {
								suit = 3;
							}
						} else if (line.charAt(0) == 'J') {
							value = 13;
							if (line.charAt(1) == 'D') {
								suit = 2;
							} else if (line.charAt(1) == 'H') {
								suit = 1;
							} else if (line.charAt(1) == 'S') {
								suit = 0;
							} else {
								suit = 3;
							}
						} else {
							value = Character.getNumericValue(line.charAt(0));
							if (line.charAt(1) == 'D') {
								suit = 2;
							} else if (line.charAt(1) == 'H') {
								suit = 1;
							} else if (line.charAt(1) == 'S') {
								suit = 0;
							} else {
								suit = 3;
							}
						}
					} else if (line.length() == 3) {
						value = 10;
						if (line.charAt(1) == 'D') {
							suit = 2;
						} else if (line.charAt(1) == 'H') {
							suit = 1;
						} else if (line.charAt(1) == 'S') {
							suit = 0;
						} else {
							suit = 3;
						}
					}

					deck[cardCount] = new Card(value, suit);
					cardCount++;
				}
			} catch (IOException q) {
				System.err.println(q);
			}
		}
		cardsUsed = 0;
		cardsInShoe = 52 * numberofDecks;
	}

	public void shuffle() { // puts all cards in deck and shuffles
		for (int i = numberofDecks * 51; i > 0; i--) {
			int a = (int) (Math.random() * (i + 1));
			Card temp = deck[i];
			deck[i] = deck[a];
			deck[a] = temp;
		}
		cardsUsed = 0;
	}

	public int cardsLeft() {// Shows how many cards are left in the deck
		cardsInShoe = cardsInShoe - cardsUsed;
		return (numberofDecks * 52 - cardsUsed);
	}

	public Card dealCard() {// Deals 1 card. if non available then shuffle
		if (cardsUsed == numberofDecks * 52) {
			shuffle();
		}
		cardsUsed++;
		return deck[cardsUsed - 1];
	}
}