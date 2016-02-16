import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;

import javax.swing.*;

/**
 * This code is the result of collaboration of Mai and Alex.
 * Date: 2016, January 26th.
 * View receives printing instructions from Model()
 * It can also receive printing instructions from Controller
 */

public class View {
    private int numberOfRows;
    private int numberOfColumns;
    private static int cardWidth = 72;
	private static int cardHeight = 96;
	/**
	 * @wbp.parser.entryPoint
	 */
	View(int size) {
        numberOfRows = size;
        numberOfColumns = size;
    }
	
	/**
	 * Redraws the graphics from scratch
	 */
	public void displayGraphics(JLabel[][] cardArray, Card[][] grid){
		// loop through the field array and draw the things in it
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
            	cardArray[i][j].setIcon(createImageIcon("./cards-images/playing-card-back.jpg", "Back of playing card"));               
            }
        }
	}
	
	/**
	 * update the cards as they are dealt
	 * @param currentCard
	 * @param deck
	 */
	public void updateCard(Controller.CardCell cell, String cardName) {
		ImageIcon image = drawCard(cardName);
		cell.setIcon(image);
	}
	
	/**
	 * Updates the deck to reflect the newly dealt card
	 * @param currentCard
	 * @param deck
	 */
	public void updateDeck(Card currentCard, CardDeck deck) {
		ImageIcon cardImage = drawCard(currentCard.toString());
		deck.setIcon(cardImage);
		deck.currentCard = currentCard;
		System.out.println("updateDeck: " + currentCard);
	}
	
	public void updateScores(int[] handScores, JLabel[] columnScores, JLabel[] rowScores) {
		int length = handScores.length/2;
		for (int i = 0; i < length; i++){
			columnScores[i].setText("" + handScores[i]);
			rowScores[i].setText("" + handScores[5 + i]);
		}
	}
	
	/**
	 * draw the card by calling another function to fetch the images and create an image icon
	 * @param card
	 * @return
	 */
	public ImageIcon drawCard(String cardName) {
		return createImageIcon("./cards-images/" + cardName + ".png", "" + cardName);
	}
	
	/**
	 * set up the control panel
	 * @param controlPanel
	 * @return
	 */
	public CardDeck setUpControlPanel(JPanel controlPanel){
		CardDeck deck = new CardDeck(createImageIcon("./cards-images/J1.png", "deck"));
		controlPanel.add(deck);
		DragSource dragSource = new DragSource();
		dragSource.createDefaultDragGestureRecognizer(deck, DnDConstants.ACTION_COPY, deck);
		return deck;
	}
	
	/**
	 * creates an ImageIcon displaying the image from the path
	 * @param path
	 * @param description
	 * @return the image
	 */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = View.class.getResource(path);
		if (imgURL != null) {
			ImageIcon picture =  new ImageIcon(imgURL, description);
			Image tempImage = picture.getImage();
			tempImage = tempImage.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
			picture = new ImageIcon(tempImage);
			return picture;
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	
	
}
