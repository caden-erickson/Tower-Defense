package tower;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import effect.*;
import enemy.*;
import game.*;

/**
 * A <code>TowerSpray</code> object represents a Sanitizer tower drawn on
 * the active game field.
 * 
 * Objects built of this class will store data about their position, the name of
 * their image file, and the width and height at which they're displayed, and
 * will have functionality to draw themselves.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class TowerSpray extends Tower
{
	// Fields
	double timeSinceFire;
	boolean inFocus;
	int victims;
	
	/**
	 * Metric for adjusting the x value of the position when generating
	 * a spray effect, so the effect is centered at the nozzle tip.
	 */
	int sprayLocationModifier;

	
	/**
	 * TowerSpray constructor. Objects built of this class will have functionality
	 * given by the Enemy superclass, as well as values for name, width, and height,
	 * and effect offset.
	 * 
	 * @param state the current GameState object
	 * @param x     the x coordinate
	 * @param y     the y coordinate
	 */
	public TowerSpray(GameState state, int x, int y)
	{
		super(state, x, y);
		name = "spray_right.png";
		width = 50;
		height = 50;
		timeSinceFire = 0.0;
		sprayLocationModifier = 13;
		inFocus = true;
		victims = 0;
	}

	/**
	 * Updates the timeSinceFire variable, and detects nearby enemies, firing
	 * at them and creating attack effects when in range.
	 * Also flips the image based on the position of the nearest enemy.
	 * 
	 * @param timeElapsed how many seconds since the last update
	 */
	public void update(double timeElapsed)
	{
		timeSinceFire += timeElapsed;
		
		// Find the nearest victim, and only proceed if there's one close by
		Enemy victim = state.nearestEnemy(position);
		if (victim == null)
			return;
		
		// Flips the image left and right, and adjusts the point from which the spray emanates accordingly
		if (victim.getPosition().x < position.x && position.distance(victim.getPosition()) < 60)
		{
			name = "spray_left.png";
			sprayLocationModifier = -13;
		}
		else if (victim.getPosition().x > position.x && position.distance(victim.getPosition()) < 60)
		{
			name = "spray_right.png";
			sprayLocationModifier = 13;
		}
		
		// If an enemy is in range, fire
		if (position.distance(victim.getPosition()) < 60 && timeSinceFire > 0.9)
		{
			Point sprayPoint = new Point(position.x+sprayLocationModifier, position.y-25);
			state.addGameObject(new EffectSpray(state, sprayPoint, victim.getPosition(), this));
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
