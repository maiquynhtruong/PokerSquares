import java.util.Collections;
import java.util.Stack;

/**
 * RandomPokerSquaresPlayer - a simple example implementation of
 * the player interface for PokerSquares that makes random placements.
 * Author: Todd W. Neller
 */
public class RandomPokerSquaresPlayer implements PokerSquaresPlayer {

	public Stack<Integer> plays = new Stack<Integer>();
	
	@Override
	// Create a stack permutation of 25 integers (0-24) for random plays
	public void init() {
		plays.clear();
		for (int i = 0; i < 25; i++)
			plays.push(i);
		Collections.shuffle(plays); 
	}

//	public int[] getPlay(Card card, long millisRemaining) {
//		int play = plays.pop(); // get the next random position for play
//		int[] playPos = {play / 5, play % 5}; // decode it into row and column
//		return playPos; // return it
//	}
	
}
