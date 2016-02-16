//import java.util.Scanner;

public class Main {
	
	public static final int SIZE = 5; // square grid size
	// 2013 contest maximum milliseconds per game
	public static final long GAME_MILLIS = 60000L;
	
	private static Card[][] grid;
    private static Model model;
    private static View view;
    private static Controller controller;
    public PokerSquaresPlayer player; // current player
    
    /**
     * Main class for starting a rabbit hunt; no parameters
     * are needed or used.
     * 
     * Note that when using the MVC design pattern, because it is
     * event-driven, you normally don't need ot do anything once the 
     * model, view, and controller are started.
     */
    public static void main(String args[]) {
        grid = new Card[SIZE][SIZE];
        model = new Model(grid);
        view = new View(SIZE);
        controller = new Controller(model, view);
    }
}
