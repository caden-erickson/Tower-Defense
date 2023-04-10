package tower;

import java.awt.Graphics;
import java.awt.Point;

import game.*;

/**
 * The <code>Tower</code> superclass contains fields and methods used by all
 * subclasses that extend this class.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public abstract class Tower implements Animatable
{
	// Fields
	protected GameState state;
	protected Point position;
	
	protected String name;			// name of image file associated with the current tower type
	protected int width, height;	// width and height at which to display the image
	

	
	/**
	 * Tower constructor. Objects built of classes that extend this superclass will
	 * keep track of their x and y coordinates. They will also have access to the
	 * GameState object to be able to call relevant methods on themselves.
	 * 
	 * @param state the current <code>GameState</code> object
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Tower(GameState state, int x, int y)
	{
		this.state = state;
		position = new Point(x, y);
	}
	
	/**
	 * Draws the Tower. Loads the image--specified by the name field--from the
	 * <code>ResourceLoader</code> object, and then draws it at the position given
	 * by the x and y fields, with dimensions specified by the width and height
	 * fields. <br>
	 * Overloaded in moving tower classes.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object
	 */
	public void draw(Graphics g, GameView view)
	{
		view.drawCenteredImage(g, name, position, width, height);
	}
}