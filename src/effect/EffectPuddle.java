package effect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import enemy.*;
import game.*;
import tower.*;

/**
 * An EffectPuddle object represents an expanding puddle (circle) drawn on the
 * screen, that acts as the fired attack from Bleach towers.
 * 
 * Objects of this class keep track of the the GameState, their origin, radius,
 * life time, and whether or not they've killed an enemy. They will also have
 * functionality to update their info and draw themselves, and to interact with
 * and destroy enemies.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class EffectPuddle extends Effect
{
	// Fields
	double radius;
	boolean used;
	double lifeTime;
	TowerBleach parent;

	/**
	 * EffectPuddle constructor. Objects built of this class will have functionality
	 * given by the Effect superclass, as well as values for radius and lifeTime,
	 * and tracking for whether or not they've killed an enemy.
	 * 
	 * @param state  the current GameState object
	 * @param origin the point from which the puddle emanates
	 */
	public EffectPuddle(GameState state, Point origin, TowerBleach parent)
	{
		super(state, origin);
		radius = 0;
		used = false;
		lifeTime = 0.0;
		this.parent = parent;
	}

	/**
	 * Updates the puddle's info (primarily radius). Also deals with timing out and
	 * killing enemies in range.
	 * 
	 * @param timeElapsed how much time has passed (seconds) since the last update
	 */
	public void update(double timeElapsed)
	{
		lifeTime += timeElapsed;
		
		// Increases up to 60
		if (radius < 60)
		{
			radius += 3;
		}
		
		// Expires after 1 second
		if (lifeTime > 1)
		{
			state.removeGameObject(this);
		}
		
		// Look at the nearest enemy, and if one is in range, kill it
		
		Enemy victim = state.nearestEnemy(origin);
		if (victim == null) return;
		
		if (origin.distance(victim.getPosition()) < radius + victim.getSize()/4 && !used)
		{
			victim.die();
			parent.incrementVictims();
			if (!(victim instanceof EnemyPink)) // it can consume unlimited pinks, but not blues or greens
			{
				used = true;
			}
		}
	}

	/**
	 * Draws the puddle with its current radius, centered at its origin point.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current GameView, unused
	 */
	public void draw(Graphics g, GameView view)
	{
		int tempR = (int)radius;
		g.setColor(new Color(0.9f, 0.9f, 0.9f, 0.65f));
		g.fillOval(origin.x-tempR, origin.y-tempR, tempR*2, tempR*2);
	}
}
