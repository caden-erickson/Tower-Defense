package screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;

import game.*;

/**
 * A <code>ScreenStart</code> object contains the necessary methods to draw the
 * initial starting panel of the game to the screen.
 * 
 * @author Caden Erickson
 * @version December 07, 2021
 */
public class ScreenStart implements Animatable
{
	// Fields
	GameState state;
	GameView view;
	String startButtonFile;

	/**
	 * ScreenStart constructor. The object built of this class will have access to
	 * the game state and view, so it can track mouse activity, add game objects,
	 * and change the cursor.
	 * 
	 * @param state the current GameState object
	 * @param view  the current GameView object, for adjusting the cursor
	 */
	public ScreenStart(GameState state, GameView view)
	{
		this.state = state;
		this.view = view;
		startButtonFile = "start_button.png";
	}

	/**
	 *
	 */
	public void update(double timeElapsed)
	{
		// Control for mousing over and clicking the start button
		if (mouseOverButton())
		{
			// Change the cursor and image to reflect mousing over the button
			view.setCursor(new Cursor(Cursor.HAND_CURSOR));
			startButtonFile = "start_button_dark.png";
			
			// Clicking the button sets everything off
			if (state.isMouseClicked())
			{
				// Add the initial view objects
		    	state.addGameObject(new Backdrop());
		    	state.addGameObject(new Menu(state));
		    	
		    	// Trip the inPlay flag so that the enemies will start generating
		    	state.startPlay();
		    	
		    	// Reset the timer to 0
		    	state.resetTime();
		    	
		    	// Cleanup- consume the mouse click, reset the cursor, and get rid of this object
		    	state.consumeMouseClick();
		    	view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				state.removeGameObject(this);
			}
		}
		else
		{
			// set the cursor and image back to default
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			startButtonFile = "start_button.png";
		}
	}

	/**
	 * This method detects whether the mouse is over the start button or not.
	 * 
	 * @return true or false
	 */
	private boolean mouseOverButton()
	{
		Point mouse = new Point(state.getMouseX(), state.getMouseY());
		Point center = new Point(425, 300);
		if (mouse.distance(center) <= 75)
		{
			return true;
		}
		return false;
	}

	/**
	 * Draws the black start screen, with the button centered in the window.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object, unused here
	 */
	public void draw(Graphics g, GameView view)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 850, 600);

		view.drawCenteredImage(g, startButtonFile, new Point(425, 300), 150, 150);
	}
}