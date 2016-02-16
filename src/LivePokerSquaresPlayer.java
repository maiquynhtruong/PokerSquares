
public class LivePokerSquaresPlayer implements PokerSquaresPlayer {

	private final int SIZE = PokerSquares.SIZE;
	private Card[][] grid = new Card[SIZE][SIZE];
	private int[] numColCards = new int[SIZE];
	
	@Override
	public void init() {
		for (int row = 0; row < SIZE; row++)
			for (int col = 0; col < SIZE; col++)
				grid[row][col] = null;
		for (int col = 0; col < SIZE; col++)
			numColCards[col] = 0;
	}

}
