package tower;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import effect.EffectPuddle;
import enemy.Enemy;
import game.*;

/**
 * A <code>TowerBleach</code> object represents a Bleach tower drawn on the
 * active game field.
 * 
 * Objects built of this class will store data about their position, the name of
 * their image file, and the width and height at which they're displayed, and
 * will have functionality to draw themselves.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class TowerBleach extends Tower
{
	// Fields
	double timeSinceFire;
	boolean inFocus;
	int victims;
	
	/**
	 * TowerBleach constructor. Objects built of this class will have functionality
	 * given by the Enemy superclass, as well as values for name, width, height, and
	 * how long it's been since they've fired an attack.
	 * 
	 * @param state the current <code>GameState</code> object
	 * @param x     the x coordinate
	 * @param y     the y coordinate
	 */
	public TowerBleach(GameState state, int x, int y)
	{
		super(state, x, y);
		name = "bleach.png";
		width = 30;
		height = 50;
		timeSinceFire = 0.0;
		inFocus = true;
		victims = 0;
	}

	/**
	 * Updates the timeSinceFire variable, and detects nearby enemies, firing
	 * at them and creating attack effects when in range.
	 * 
	 * @param timeElapsed how many seconds since the last update
	 */
	public void update(double timeElapsed)
	{
		timeSinceFire += timeElapsed;
		
		// If an enemy is in range, fire
		Enemy victim = state.nearestEnemy(position);
		if (victim != null && position.distance(victim.getPosition()) < 100 && timeSinceFire > 1.5)
		{
			state.addGameObject(new EffectPuddle(state, position, this));
			timeSinceFire = 0;
		}
		
		if (state.isMouseClicked())
		{
			if (state.getMouseX() >= position.x - width / 2 && state.getMouseX() <= position.x + width / 2
					&& state.getMouseY() >= position.y - width / 2 && state.getMouseY() <= position.y + height / 2)
			{
				inFocus = true;
			}
			else
			{
				inFocus = false;
			}
		}
	}
	
	public void draw(Graphics g, GameView view)
	{
		if (inFocus)
		{
			g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.3f));
			g.fillRect(0, 0, 600, 600);
			
			g.setColor(new Color(0.9f, 0.9f, 0.7f, 0.3f));
			g.fillOval(position.x-60, position.y-60, 120, 120);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.PLAIN, 20));
			g.drawString("Enemies killed: "+victims, position.x + 75, position.y + 5);
		}
		view.drawCenteredImage(g, name, position, width, height);
	}
	
	public void incrementVictims()
	{
		victims++;
	}
}
