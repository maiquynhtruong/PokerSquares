import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * This code is the result of collaboration of Mai and Alex.
 * Date: 2016, January 26th.
 *
 * Controller asks user what type of character they want to play as, then send the information to Model.
 */

public class Controller extends Frame implements Runnable{

	// Graphics components
	private JFrame frame; // The main frame
	public JLabel[][] cardArray; // array of JLabels for cards
	CardDeck deck; //Deck of cards
	JLabel[] rowScoresArray; // row of scores
	JLabel[] columnScoresArray; // column of scores
	JLabel totalScore;
	
	// all the three components of MVC
	public Model systemModel; 
	public View systemView;
	public Controller systemController;
	
	// controller's variables
	private boolean newGamePressed = false;
	
	/**
	 * Constructor
	 * @param systemModel
	 * @param systemView
	 */
	public Controller(Model systemModel, View systemView) {
		this.systemModel = systemModel;
		this.systemView = systemView;
		this.systemController = this;
		setTitle("Poker Squares");
        setLocation(50, 50);
		makeFrame();
		systemModel.initGame();
		systemView.displayGraphics(cardArray, systemModel.grid);
	}
	
	/**
     * Starts and controls the animation.
     */
    public void run() {
    	if (!newGamePressed && systemModel.cardsPlaced < Model.SIZE * Model.SIZE) {
	        dealACard();
	    } else {
    		systemView.displayGraphics(cardArray, systemModel.grid);
    		newGamePressed = false;
    	}
    }
	
    /**
     * Deals a card and change the image in the deck
     */
	public void dealACard(){
		systemModel.playSingleMove();
		systemView.updateDeck(systemModel.currentCard, deck);
	}
	
	/**
	 * Get the scores and update the scores displayed
	 * @param currCardX
	 * @param currCardY
	 */
	public void updateCurrentScore(int currCardX, int currCardY){
		int totalScore = systemModel.getScoreSingleMove(currCardX, currCardY);
		int[] handScores = systemModel.getHandScoresArray();
		systemView.updateScores(handScores, columnScoresArray, rowScoresArray);
		this.totalScore.setText("" + totalScore);
	}
	
	/**
	 * Updates an individual card
	 * @param cell the cell to be updated
	 * @param cardName name of the card to find the image
	 */
	public void updateCard(CardCell cell, String cardName) {
		systemView.updateCard(cell, cardName);
	}
	
	/**
	 * makes the frame, adds all the components the frame, and displays the frame
	 */
	public void makeFrame() {
		//making the frame
		frame = new JFrame("Poker Squares");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		int frameWidth = 700, frameHeight = 700; // dimensions of frame
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		
		//create content panel
		JPanel contentPanel; // panel for all the content, including the card grid, the buttons panel
		contentPanel = (JPanel)frame.getContentPane();
		contentPanel.setLayout(new BorderLayout());
		int contentWidth = 700, contentHeight = 700; // dimensions of content panel
		contentPanel.setPreferredSize(new Dimension(contentWidth, contentHeight));
		
		//create control panel and add buttons
		JPanel controlPanel; // panel for the buttons and the deck
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		JButton newGameBtn = new JButton("New Game"); //new game button
		controlPanel.add(newGameBtn);
		newGameBtn.addActionListener(new NewGameButtonHandler());
		JButton dealACardBtn = new JButton("Deal A Card"); //deal a card button
		controlPanel.add(dealACardBtn);
		dealACardBtn.addActionListener(new DealACardButtonHandler());
		deck = systemView.setUpControlPanel(controlPanel); //add the deck to control panel
		contentPanel.add(controlPanel, BorderLayout.LINE_END); //add the control panel to the content panel
		
		//create the grid of cards and scores, and add it to content panel
		int horizontalGap = 5, verticalGap = 5; //Gaps between cards
		int cardGridWidth = 620, cardGridHeight = 650; // widtha and height of each card
		JPanel cardAndScoresGrid = new JPanel(new GridLayout(Model.SIZE + 1, Model.SIZE + 1, horizontalGap, verticalGap));
		cardAndScoresGrid.setBorder(BorderFactory.createEmptyBorder(0, 20 ,0,20));
		cardAndScoresGrid.setPreferredSize(new Dimension(cardGridWidth, cardGridHeight));
		cardArray = new CardCell[Model.SIZE][Model.SIZE];
		rowScoresArray = new JLabel[5];	// array of row scores
		columnScoresArray = new JLabel[5]; // array of column scores
		for (int i = 0; i < Model.SIZE; i++) {
			for (int j = 0; j < Model.SIZE; j++) {
				cardArray[i][j] = new CardCell(i, j);
				cardAndScoresGrid.add(cardArray[i][j]);
			}
			columnScoresArray[i] = new JLabel("0", SwingConstants.CENTER);
			cardAndScoresGrid.add(columnScoresArray[i]);
		}
		for (int i = 0; i < Model.SIZE; i++) {
			rowScoresArray[i] = new JLabel("0", SwingConstants.CENTER);
			cardAndScoresGrid.add(rowScoresArray[i]);
		}
		totalScore = new JLabel("0", SwingConstants.CENTER); //the total score
		totalScore.setFont(new Font("Serif", Font.BOLD, 25));
		cardAndScoresGrid.add(totalScore);
		contentPanel.add(cardAndScoresGrid, BorderLayout.CENTER);
				
		frame.pack();
		frame.setVisible(true);
	}

	
	
	class NewGameButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			systemModel.initGame();
			systemView.displayGraphics(cardArray, systemModel.grid);
			systemModel.cardsPlaced = 0;
			for (int i = 0; i < Model.SIZE; i++) { //set the column and row scores array to be 0
				columnScoresArray[i].setText("" + 0);
				rowScoresArray[i].setText("" + 0);
			}
			totalScore.setText("" + 0); //set the total score to be 0
			Thread animationThread = new Thread(systemController);
			animationThread.start();
		}
	}
	
	class DealACardButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Dealing a card");
			Thread animationThread = new Thread(systemController);
			animationThread.start();
		}
	}
	
    
    public class CardCell extends JLabel implements DropTargetListener {

    	DropTarget dropTarget;
    	DataFlavor dataFlavor = DataFlavor.stringFlavor;
//    			new DataFlavor(Card.class,Card.class.getSimpleName());
    	int cardCellX, cardCellY;
    	
    	public CardCell(int xCoor, int yCoor){
    		super();
    		this.dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY, this, true, null);
    		this.cardCellX = xCoor;
    		this.cardCellY = yCoor;
    	}
    	@Override
    	public void dragEnter(DropTargetDragEvent dtde) {}

    	@Override
    	public void dragExit(DropTargetEvent dte) {}

    	@Override
    	public void dragOver(DropTargetDragEvent dtde) {}

    	@Override
    	public void drop(DropTargetDropEvent dtde) {
    		try {
    			Transferable transferable = dtde.getTransferable();
    			
    			if (transferable.isDataFlavorSupported(dataFlavor)) {
    				dtde.acceptDrop(DnDConstants.ACTION_COPY);
    				String cardName = (String) transferable.getTransferData(dataFlavor);
    				updateCard(this, cardName);
    				updateCurrentScore(this.cardCellX, this.cardCellY);
    				dtde.getDropTargetContext().dropComplete(true);
    				
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    			dtde.rejectDrop();
    		} catch (UnsupportedFlavorException e) {
    			e.printStackTrace();
    			dtde.rejectDrop();
    		}
    	}

    	@Override
    	public void dropActionChanged(DropTargetDragEvent dtde) {}

    }
    
 }