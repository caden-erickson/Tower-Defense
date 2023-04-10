package enemy;

import java.awt.Graphics;
import java.awt.Point;

import game.*;

/**
 * The <code>Enemy</code> superclass contains fields and methods used by all
 * subclasses that extend this class.
 * 
 * @author Caden Erickson
 * @version December 01, 2021
 */
public abstract class Enemy implements Animatable
{
	// Fields
	protected GameState state;
	protected double percentTraveled;
	protected Point position;
		
	protected String name;		// name of image file associated with the current enemy type
	protected double velocity;	// pixels per second to move
	protected int size;			// width and height, in pixels (enemies are resized to be square)
	protected int potency;		// how many lives are lost when the enemy reaches the end (different types are more/less dangerous)
	
	/**
	 * Enemy constructor. Objects built of classes that extend this superclass will
	 * keep track of how far along the path they have traveled. They will also have
	 * access to the GameState object to be able to call necessary methods.
	 * 
	 * @param percentTraveled how far along the path the germ has gone, given as a
	 *                        percentage between 0.0 and 1.0
	 * @state the current GameState object
	 */
	public Enemy(double percentTraveled, GameState state)
	{
		this.state = state;
		this.percentTraveled = percentTraveled;
		position = ResourceLoader.getLoader().getPath("path.txt").getPathPosition(percentTraveled);
		state.incrementEnemyCount();
	}
	
	/**
	 * Updates the Enemy's data. <br>
	 * Each update will increase the percentage of the path that the enemy has
	 * traveled by a small amount. When 100% is reached, the percentage resets to 0.
	 * 
	 * @param timeElapsed the scalar (sec) by which to multiply the velocity
	 *                    (pixels/sec)
	 */
	public void update(double elapsedTime)
	{
		percentTraveled += elapsedTime * velocity;
		if (percentTraveled > 1.0)
		{
			state.removeGameObject(this);
			state.updateLives(potency);
		}
	}
	
	/**
	 * Draws the Enemy. <br>
	 * Loads the path from the <code>ResourceLoader</code> object, and then draws
	 * uses the path's <code>getPathPosition()</code> method to find the position of
	 * the <code>Enemy</code> on the path, given as a coordinate point.
	 * 
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	public void draw(Graphics g, GameView view)
	{
		// Find the position of the ball
		position = ResourceLoader.getLoader().getPath("path.txt").getPathPosition(percentTraveled);
		view.drawCenteredImage(g, name, position, size, size);
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public int getSize()
	{
		return size;
	}
	
	/**
	 * Takes necessary action when an enemy is hit by a tower's effect, such as
	 * adding credits, creating other enemies, or removing the current enemy.
	 */
	public abstract void die();
}
