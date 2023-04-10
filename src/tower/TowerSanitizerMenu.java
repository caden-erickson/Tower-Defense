package tower;

import game.GameState;

/**
 * A <code>TowerSanitizerMenu</code> object represents a Sanitizer tower icon
 * drawn in the menu list of purchasable towers.
 * 
 * Objects built of this class will store data about their position, the name of
 * their image file, and the width and height at which they're displayed, and
 * will have functionality to draw themselves.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class TowerSanitizerMenu extends Tower
{
	// Fields
	private static int price = 5000; // super high until I get around to implementing functionality for this tower. Should be 10
	
	/**
	 * TowerSanitizerMenu constructor. Objects built of this class will have
	 * functionality given by the Enemy superclass, as well as values for name,
	 * width, height, and price.
	 * 
	 * @param state the current GameState object
	 * @param x     the x coordinate
	 * @param y     the y coordinate
	 */
	public TowerSanitizerMenu(GameState state, int x, int y)
	{
		super(state, x, y);
		name = "sanitizer_dark.png";
		width = 60;
		height = 120;
	}
	
	/** Getter for price value, used in menu display **/
	public static int getPrice()
	{
		return price;
	}
	
	/**
	 * Updates the image to reflect whether or not the player can buy it- if they
	 * don't have enough credits, the tower appears grayed out. If they do have
	 * enough and they click the tower, a new moving object is created and credits
	 * are deducted.
	 * 
	 * @param timeElapsed unused
	 */
	public void update(double timeElapsed)
	{
		// Iff the user has enough credits, switch to the clickable-looking image
		if (state.getCredits() >= price)
		{
			name = "sanitizer.png";
		}
		else
		{
			name = "sanitizer_dark.png";
		}
		
		// If the user clicks on this object, and has enough credits, create a
		//  TowerSanitizerMoving object. Also subtract the necessary credits.
		if (state.getMouseX() >= position.x - width / 2 && state.getMouseX() <= position.x + width / 2
				&& state.getMouseY() >= position.y - width / 2 && state.getMouseY() <= position.y + height / 2
				&& state.isMouseClicked() && state.getCredits() >= price)
		{
			state.addGameObject(new TowerSanitizerMoving(state, state.getMouseX(), state.getMouseY(), price));
			state.updateCredits(price*-1);
			state.consumeMouseClick();
		}
	}
}
