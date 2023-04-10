package screen;

import java.awt.Color;
import java.awt.Graphics;

import game.*;

/**
 * A <code>ScreenGameOver</code> object contains code to draw the "Game Over"
 * layer to the viewable window.
 * 
 * @author Caden Erickson
 * @version Decmeber 06, 2021
 */
public class ScreenGameOver implements Animatable
{
	/** Constructor **/
	public ScreenGameOver()
	{
		// Default
	}
	
	/** Updates info- unused **/
	public void update(double timeElapsed)
	{
		// The layer just gets drawn. Nothing to update
	}

	/**
	 * Draws the "Game Over" layer.<br>
	 * Loads the Game Over image from the <code>ResourceLoader</code> object, and
	 * then draws both a darkened rectangle and the loaded image to the passed
	 * <code>Graphics</code> object.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object, unused here
	 */
	public void draw(Graphics g, GameView view)
	{
		// semi-transparent rectangle to darken the screen a bit
		g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.65f));
		g.fillRect(0, 0, 600, 600);
		
		g.drawImage(ResourceLoader.getLoader().getImage("game_over.png"), 0, 0, null);
	}
}