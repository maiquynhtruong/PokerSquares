import java.util.*;

/**
 * This code is the result of collaboration of Mai and Alex.
 * Date: 2016, January 26th.
 * Model receives the type of player from Controller
 * Model does all of the computations regarding hand scores, total scores.
 * Model sends the messages to View to display the messages
 */

public class Model {

	static int SIZE; //size of game board
	public PokerSquaresPlayer player = null; // current player

	// whether or not to print move-by-move transcript of the game
//	private boolean verbose = true;
	public Card[][] grid; // current game grid
	static Random random = new Random();
		
    /** Current card */
    Card currentCard;
    
    int cardsPlaced = 0;
    
    Stack<Card> deck;
	
	public Model (Card[][] grid) {
		this.grid = grid;
		SIZE = grid.length;
		this.player = new LivePokerSquaresPlayer();
	}

	public void initGame() {
		// shuffle deck
		deck = new Stack<Card>();
		for (Card card : Card.allCards)
			deck.push(card);
		Collections.shuffle(deck, random);
		
		for (int row = 0; row < SIZE; row++)
			for (int col = 0; col < SIZE; col++)
				grid[row][col] = null;
	}
	/**
	 * Play a game of Poker Squares with the given PokerSquaresPlayer
	 */
	public void playSingleMove() {
			/* deal the next card */
			Card card = deck.pop();
			currentCard = card;						
			cardsPlaced++;
	}
	
	/**
	 * Gets the scores of a single move
	 * @param currCardX
	 * @param currCardY
	 * @return total score
	 */
	public int getScoreSingleMove(int currCardX, int currCardY){
		grid[currCardX][currCardY] = currentCard;
		return getScore(grid);
	}
	
	/**
	 * Get the array of row hand scores and column hand scores
	 * @return
	 */
	public int[] getHandScoresArray() {
		return getHandScores(grid);
}
	/**
	 * Get the score of the given Card grid.
	 * @param grid Card grid
	 * @return score of given Card grid
	 */
	public static int getScore(Card[][] grid) {
		int[] handScores = getHandScores(grid);
		int totalScore = 0;
		for (int handScore : handScores)
			totalScore += handScore;
		System.out.println("totalScore: " + totalScore);
		return totalScore;
	}

	/**
	 * Get an int array with the individual hand scores of
	 * rows 0 through 4 followed by columns 0 through 4. 
	 * @param grid 2D Card array representing play grid
	 * @return an int array with the individual hand scores
	 * of rows 0 through 4 followed by columns 0 through 4. 
	 */
	public static int[] getHandScores(Card[][] grid) {
		int[] handScores = new int[2 * SIZE];
		for (int row = 0; row < SIZE; row++) {
			Card[] hand = new Card[SIZE];
			for (int col = 0; col < SIZE; col++)
				hand[col] = grid[row][col];
			handScores[row] = getHandScore(hand);
		}
		for (int col = 0; col < SIZE; col++) {
			Card[] hand = new Card[SIZE];
			for (int row = 0; row < SIZE; row++)
				hand[row] = grid[row][col];
			handScores[SIZE + col] = getHandScore(hand);
		}
		return handScores;
	}

	/**
	 * Get the score of the given Card hand.
	 * @param hand Card hand
	 * @return score of given Card hand.
	 */
	public static int getHandScore(Card[] hand) {
		// Compute counts
		int[] rankCounts = new int[Card.NUM_RANKS];
		int[] suitCounts = new int[Card.NUM_SUITS];
		for (Card card : hand)
			if (card != null) {
				rankCounts[card.getRank()]++;
				suitCounts[card.getSuit()]++;
			}

		// Compute count of rank counts
		int maxOfAKind = 0;
		int[] rankCountCounts = new int[hand.length + 1];
		for (int count : rankCounts) {
			rankCountCounts[count]++;
			if (count > maxOfAKind)
				maxOfAKind = count;
		}

		// Flush check
		boolean hasFlush = false;
		for (int i = 0; i < Card.NUM_SUITS; i++)
			if (suitCounts[i] != 0) {
				if (suitCounts[i] == hand.length)
					hasFlush = true;
				break;
			}

		// Straight check
		boolean hasStraight = false;
		boolean hasRoyal = false;
		int rank = 0;
		while (rank <= Card.NUM_RANKS - 5 && rankCounts[rank] == 0) {
			rank++;
		}

		hasStraight = (rank <= Card.NUM_RANKS - 5
				&& rankCounts[rank] == 1 && rankCounts[rank + 1] == 1
				&& rankCounts[rank + 2] == 1 && rankCounts[rank + 3] == 1
				&& rankCounts[rank + 4] == 1);

		if (rankCounts[0] == 1 && rankCounts[12] == 1
				&& rankCounts[11] == 1 && rankCounts[10] == 1
				&& rankCounts[9] == 1) 
			hasStraight = hasRoyal = true;

		// Return score
		if (hasFlush) {
			if (hasRoyal)
				return 100; // Royal Flush
			if (hasStraight)
				return 75; // Straight Flush
		}
		if (maxOfAKind == 4)
			return 50; // Four of a Kind
		if (rankCountCounts[3] == 1 && rankCountCounts[2] == 1)
			return 25; // Full House
		if (hasFlush)
			return 20; // Flush
		if (hasStraight)
			return 15; // Straight
		if (maxOfAKind == 3)
			return 10; // Three of a Kind
		if (rankCountCounts[2] == 2)
			return 5; // Two Pair
		if (rankCountCounts[2] == 1)
			return 2; // One Pair
		return 0; // Otherwise, score nothing.
	}

}
