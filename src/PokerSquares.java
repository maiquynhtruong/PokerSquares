
/* The solitaire game "Poker Squares"
 * Author: Todd W. Neller

Notes: 

A Poker Squares grid is represented as a 5-by-5 array of Card objects.
A null indicates an empty position.
In the solitaire game of Poker Squares, a deck is initially shuffled.
Each turn, the player draws a card from the deck and places it
in any empty cell of a 5-by-5 grid.  Once placed, cards may not be moved.
After the last cell is filled, each row and column are scored
according to the American point system for Poker Squares hands:

100 - Royal Flush: A T-J-Q-K-A sequence all of the same suit.
Example: TC, JC, QC, KC, AC.
75 - Straight Flush: Five cards in sequence all of the same suit.
Example: AD, 2D, 3D, 4D, 5D.
50 - Four of a Kind: Four cards of the same rank.
Example: 9C, 9D, 9H, 9S, 6H.
25 - Full House: Three cards of one rank with two cards of another rank.
Example: 7S, 7C, 7D, 8H, 8S.
20 - Flush: Five cards all of the same suit.
Example: AH, 2H, 3H, 5H, 8H.
15 - Straight: Five cards in sequence. Aces may be high or low but not both.
(Straights do not wrap around.)
Example: 8C, 9S, TH, JD, QC.
10 - Three of a Kind: Three cards of the same rank.
Example: 2S, 2H, 2D, 5C, 7S.
5 - Two Pair: Two cards of one rank with two cards of another rank.
Example: 3H, 3D, 4C, 4S, AC.
2 - One Pair: Two cards of one rank.
Example: 5D, 5H, TC, QS, AH.
(0 otherwise)

The player's total score is the sum of the scores for each
of the 10 row and column hands.

Relevant Resources: http://tinyurl.com/pokersqrs

For our purposes, a player is considered better if it has a higher
expected game score, i.e. has a higher score average over many games.

In our implementation, each turn a PokerSquaresPlayer will be passed
(1) a Card object and
(2) the number of milliseconds remaining in the game, 
and will return a length 2 integer array with the row and column
the player placed the card.  In the event that the player makes an illegal 
play or "times out", i.e. runs out of time for play,
the player loses with a final score of 0.

This file contains not only the code to run a simple demonstration
game with a random player, but also has utility functions for scoring
that will be useful for coding good players.

 */

/**
 * Click on this class to run
 * This method allows you to choose which player you want to run as.
 */
public class PokerSquares {
	
	public static final int SIZE = 5; // square grid size
	// 2013 contest maximum milliseconds per game
	public static final long GAME_MILLIS = 60000L;
	
	/**
	 * Running the program using user selection
	 */
	public static void main(String args[]) {
	}
	
}
