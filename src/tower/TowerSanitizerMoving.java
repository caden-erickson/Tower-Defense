package tower;

import java.awt.Color;
import java.awt.Graphics;

import game.*;

/**
 * A <code>TowerSanitizerMoving</code> object represents a Bleach tower icon
 * drawn to follow the mouse.
 * 
 * Objects built of this class will store data about their position, the name of
 * their image file, and the width and height at which they're displayed, and
 * will have functionality to draw themselves.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class TowerSanitizerMoving extends Tower
{
	// Fields
	private int price;
	private Color rangeColor;

	/**
	 * TowerSanitizerMoving constructor. Objects built of this class will have
	 * functionality given by the Enemy superclass, as well as values for name,
	 * width, height, price, and the color of their range circle.
	 * 
	 * @param state the current GameState object
	 * @param x     the x coordinate
	 * @param y     the y coordinate
	 * @param price the price to buy and place the tower
	 */
	public TowerSanitizerMoving(GameState state, int x, int y, int price)
	{
		super(state, x, y);
		this.price = price;
		name = "sanitizer.png";
		width = 30;
		height = 50;
		rangeColor = new Color(0.9f, 0.9f, 0.9f, 0.35f);
	}

	/**
	 * Updates the tower's position to follow the mouse. If the mouse is clicked
	 * while over the active game field, adds a TowerBleach object to the game. If
	 * the mouse is clicked while over the menu, gets rid of the moving object and
	 * reimburses the credits.
	 * 
	 * @param timeElapsed unused
	 */
	public void update(double timeElapsed)
	{
		// Follow the mouse
		position.x = state.getMouseX();
		position.y = state.getMouseY();
		
		double distanceToPath = ResourceLoader.getLoader().getPath("path.txt").nearestNodeDistance(position);
		
		if (distanceToPath > 40)
		{
			rangeColor = new Color(0.9f, 0.9f, 0.9f, 0.35f); // make the range circle white when it's a valid position
			if (state.isMouseClicked())
			{
				if (state.getMouseX() < 600) // only allow placement on the game field
				{
					state.addGameObject(new TowerSanitizer(state, position.x, position.y));
				}
				else // otherwise just refund the credits
				{
					state.updateCredits(price);
				}
				state.removeGameObject(this);
				state.consumeMouseClick();
			}
		}
		else
		{
			rangeColor = new Color(0.9f, 0.2f, 0.2f, 0.35f); // make the range circle red when it's an invalid position
		}
	}
	
	/**
	 * Draws the moving Tower. Loads the image--specified by the name field--from
	 * the <code>ResourceLoader</code> object, and then draws it at the position
	 * given by the x and y fields, with dimensions specified by the width and
	 * height fields. Also draws a translucent circle underneath it that changes
	 * color to indicate whether the current mouse position is a valid position at
	 * which to place the tower.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object
	 */
	public void draw(Graphics g, GameView view)
	{
		g.setColor(rangeColor);
		g.fillOval(position.x - 60, position.y - 60, 120, 120);
		
		view.drawCenteredImage(g, name, position, width, height);
	}
}
