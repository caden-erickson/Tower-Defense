package effect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import enemy.*;
import game.*;
import tower.*;

/**
 * An EffectSprayParticle object (built exclusively by the EffectSpray class)
 * represents a small traveling particle drawn on the viewable window, as part
 * of an EffectSpray object fired/created by a Spray tower.
 * 
 * Objects of this class will hold all necessary info and functionality to draw
 * and move themselves in their appropriate direction, and have functionality
 * to interact with and destroy enemies.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class EffectSprayParticle extends Effect
{
	// Fields
	//geometry fields
	Point destination;
	double angle;
	
	//particle attribute fields
	double radius;
	Point position;
	Color particleColor;
	
	//state fields
	boolean used;
	boolean firstUpdate;
	TowerSpray parent;
	
//	double speed;
	
	
	/**
	 * Gatekeeper method for making objects of this class. Only EffectSpray objects
	 * should be able to make EffectSprayParticle objects, so this method requires a
	 * Password object, which is a class nested in EffectSpray, and will therefore
	 * be an object that only EffectSpray is able to create. This method will only
	 * create and return a new EffectSprayParticle object if that fifth parameter is
	 * not null. Otherwise, returns null.
	 * 
	 * @param state       passed on to constructor
	 * @param origin      passed on to constructor
	 * @param destination passed on to constructor
	 * @param angle       passed on to constructor
	 * @param token       necessary object to allow creation of a particle object
	 */
	public static EffectSprayParticle make(GameState state, Point origin, Point destination, double angle,
			TowerSpray parent, EffectSpray.Password token)
	{
		if (token != null)
			return new EffectSprayParticle(state, origin, destination, angle, parent);
		return null;
	}
	
	/**
	 * EffectSprayParticle constructor. Objects built of this class will have access
	 * to the GameState, as well as keep track of their origin point, destination
	 * point, and angle at which they are traveling. They will keep track of how far
	 * they've traveled, their color, whether or not they've killed an enemy, and
	 * whether or not they've been updated before.
	 * 
	 * @param state       the current GameState
	 * @param origin      the point from which the current spray of particles is
	 *                    emanating
	 * @param destination the point to which this spray particle is headed
	 * @param angle       the angle at which this spray particle is traveling,
	 *                    relative to the y axis
	 */
	private EffectSprayParticle(GameState state, Point origin, Point destination, double angle, TowerSpray parent)
	{
		super(state, origin);
		
		this.destination = destination;
		this.angle = angle;
		this.parent = parent;
		
		radius = 0.0;
		position = new Point(origin.x, origin.y);
		particleColor = new Color(1.0f, 1.0f, 0.8f, 1.0f);
		used = false;
		
		firstUpdate = true;
		
//		speed = Math.random()*2 + 5; // if more sporadic spraying is wanted
	}

	/**
	 * Updates the info (mainly position) of the spray particle. On the first
	 * update, the angle/trajectory of the particle will be adjusted to its correct
	 * quadrant via the <code>adjustAngle()</code> method. If the particle collides
	 * with an enemy, the enemy will be killed, and this particle will become used
	 * and transparent. Once used, it will no longer kill any more enemies.
	 * 
	 * @param timeElapsed how much time has passed (seconds) since the last update
	 */
	public void update(double timeElapsed)
	{
		// Have to correct the angle calculation to be in the correct quadrant
		if (firstUpdate)
			adjustAngle();
		
		if (!used)
		{
//			radius += speed;
			radius += 4.5;
			position.x = (int)(origin.x+Math.cos(angle)*radius);
			position.y = (int)(origin.y-Math.sin(angle)*radius);
			
			Enemy victim = state.nearestEnemy(position);
			if (victim == null) return;
			if (position.distance(victim.getPosition()) < victim.getSize()/2)
			{
				victim.die();
				parent.incrementVictims();
				used = true;
				particleColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
			}
		}
	}

	/**
	 * Draws the particle at its calculated position, in its specified color. The
	 * color changes to transparent in the update method if the particle has killed
	 * an enemy.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view unused
	 */
	public void draw(Graphics g, GameView view)
	{
		g.setColor(particleColor);
		g.fillOval(position.x, position.y, 4, 4);
	}
	
	/**
	 * This method is used to adjust the angle/trajectory of the current spray
	 * particle. When the angle is calculated in EffectSpray, the x component is
	 * used, and is made positive to control the angle to Quadrant I. This method
	 * examines the relationship between the positions of the tower and enemy to
	 * determine in which quadrant the current spray particle should be traveling,
	 * and adds/subtracts/etc values to adjust accordingly.
	 */
	public void adjustAngle()
	{
		if (destination.x >= origin.x && destination.y >= origin.y) // quadrant IV
		{
			angle = 2*Math.PI - angle;
		}
		else if (destination.x < origin.x && destination.y >= origin.y) // quadrant III
		{
			angle += Math.PI;
		}
		else if (destination.x < origin.x && destination.y <= origin.y) // quadrant II
		{
			angle = Math.PI - angle;
		}
		// otherwise it's in quadrant I anyway, don't need to add anything
		
		firstUpdate = false;
	}
}
