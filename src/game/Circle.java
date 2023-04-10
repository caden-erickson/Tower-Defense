package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

/**
 * A <code>Circle</code> object represents a circle drawn on the viewable
 * window.
 * 
 * Objects of this class will store data about the circle such as its position
 * and size, and will have functionality to draw the circle and incrementally
 * update its position.
 * 
 * @author Caden Erickson
 * @version November 22, 2021
 */
public class Circle implements Animatable
{
	// Fields
	private double percentTraveled;
	private GameState state;
	private final int diameter = 26;
	private final Color ballColor = new Color(232, 174, 14);
	private final double movementRate = 0.060;

	/**
	 * Circle constructor. Objects built of this class will keep track of how far
	 * along the path they have traveled.
	 * 
	 * @param percentTraveled how far along the path the ball has gone, given as a
	 *                        percentage between 0.0 and 1.0
	 */
	public Circle(double percentTraveled, GameState state)
	{
		this.percentTraveled = percentTraveled;
		this.state = state;
	}

	/**
	 * Updates the circle's data.<br>
	 * Each update will increase the percentage of the path that the circle has
	 * traveled by a small amount. When 100% is reached, the percentage resets to 0.
	 * 
	 * @param timeElapsed unused
	 */
	public void update(double elapsedTime)
	{
		percentTraveled += elapsedTime * movementRate;
		if (percentTraveled > 1.0)
		{
			state.removeGameObject(this);
		}
	}

	/**
	 * Draws the circle.<br>
	 * Loads the path from the <code>ResourceLoader</code> object, and then draws
	 * uses the path's <code>getPathPosition()</code> method to find the position of
	 * the circle on the path, given as a coordinate point.
	 * 
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	public void draw(Graphics g, GameView view)
	{
		// Find the position of the circle
		Point p = ResourceLoader.getLoader().getPath("path.txt").getPathPosition(percentTraveled);

		// Draw the circle
		g.setColor(ballColor);
		g.fillOval(p.x - (diameter / 2), p.y - (diameter / 2), diameter, diameter);
	}
}
