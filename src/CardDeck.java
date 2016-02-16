import java.awt.Cursor;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * A class to represent the deck of cards
 * Cards are displayed on the deck as they are dealt
 * User can drag the card from the deck

 */
class CardDeck extends JLabel implements DragGestureListener {

		Card currentCard;
		
		public CardDeck(ImageIcon cardImage) {
			super(cardImage);
		}

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			Cursor cursor = null;
			CardDeck cardDeck = (CardDeck) dge.getComponent();
			if (dge.getDragAction() == DnDConstants.ACTION_COPY_OR_MOVE){
				cursor = DragSource.DefaultCopyDrop;
			}
			if (currentCard != null) // if user has not dealt a card, do not let the user drag the card
				dge.startDrag(cursor, new StringSelection(cardDeck.currentCard.toString()));
		}
	}