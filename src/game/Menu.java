package game;

import java.awt.Graphics;

import tower.TowerBleachMenu;
import tower.TowerSanitizerMenu;
import tower.TowerSprayMenu;

import java.awt.Color;
import java.awt.Font;

/**
 * A <code>Menu</code> object contains the necessary methods to draw the
 * background image of the menu to the screen, add objects and text to display
 * pertinent GameState info, and update those elements appropriately.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class Menu implements Animatable
{
	// Fields
	private GameState state;
	private boolean objectsAdded;
	
	/**
	 * Constructor - initializes Menu object
	 * @param state the current <code>GameState</code> object
	 */
	public Menu(GameState state)
	{
		this.state = state;
		this.objectsAdded = false;
	}
	
	/** 
	 * This method only makes calls once- if no objects have been added to the menu,
	 * it adds all the <code>Tower</code> objects for menu use, and trips a flag.
	 * All future calls of this method then do nothing.
	 * 
	 * @param timeElapsed the number of seconds since the last updatey
	 */
	public void update(double timeElapsed)
	{
		// Add menu objects if this is the first update
		if (!objectsAdded)
		{
			state.addGameObject(new TowerSanitizerMenu(state, 670, 125));
			state.addGameObject(new TowerSprayMenu(state, 790, 125));
			state.addGameObject(new TowerBleachMenu(state, 670, 285));
			objectsAdded = true;
		}
	}

	/**
	 * Draws the menu. <br>
	 * Loads the image from the <code>ResourceLoader</code> object, and then draws
	 * it to the passed <code>Graphics</code> object.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object, unused here
	 */
	public void draw(Graphics g, GameView view)
	{
		// Menu background
		g.setColor(Color.WHITE);
		g.fillRect(600, 0, 5, 600);
		g.drawImage(ResourceLoader.getLoader().getImage("menu.jpg"), 605, 0, 250, 600, null);
		
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		
		// Credits counter
		int credits = state.getCredits();
		g.drawString("$"+credits, 655, 35);
		
		// Lives counter
		int lives = state.getLives();
		g.drawString("Lives: "+lives, 745, 35);
		
		// Time counter
		double time = state.getTime();
		String minutes = String.format("%02d", ((int)time)/60);
		String seconds = String.format("%02d", (int)(time%60));
		g.drawString(minutes+":"+seconds, 707, 496);
		
		// Prices
		g.setFont(new Font("Arial", Font.PLAIN, 15));
//		g.drawString("$"+TowerSanitizerMenu.getPrice(), 660, 206);
		g.drawString("$10", 660, 206); // leaving it as 10 until I implement the sanitizer tower and can make everything line up
		g.drawString("$"+TowerSprayMenu.getPrice(), 775, 206);
		g.drawString("$"+TowerBleachMenu.getPrice(), 658, 365);
	}
}
