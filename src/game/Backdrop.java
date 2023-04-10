package game;

import java.awt.Graphics;

/**
 * A <code>Backdrop</code> object contains the necessary methods to draw the
 * background image of the game to the screen.
 * 
 * @author Caden Erickson
 * @version November 22, 2021
 */
public class Backdrop implements Animatable
{
	/** Constructor **/
	public Backdrop()
	{
		// Default
	}

	/** Updates info - unused **/
	public void update(double timeElapsed)
	{
		// The image just gets drawn. Nothing to update
	}

	/**
	 * Draws the backdrop.<br>
	 * Loads the image from the <code>ResourceLoader</code> object, and then draws
	 * it to the passed <code>Graphics</code> object.
	 * 
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	public void draw(Graphics g, GameView view)
	{
		g.drawImage(ResourceLoader.getLoader().getImage("path.jpg"), 0, 0, null);
	}
}