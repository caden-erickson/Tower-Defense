package effect;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.*;
import tower.*;

/**
 * An EffectSpray object represents a radiating arc of four small circles, that
 * acts as the fired attack from Spray towers.
 * 
 * Objects of this class keep track of the the GameState, their origin, the
 * location of the enemy fired at, their life time, and whether it's their first
 * update. They will also have functionality to create the particle objects, and
 * update and draw them.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class EffectSpray extends Effect
{
	// Fields
	Point destination;
	private List<EffectSprayParticle> particles;
	double lifeTime;
	boolean firstUpdate;
	TowerSpray parent;

	/**
	 * EffectSpray constructor. Objects built of this class will have functionality
	 * given by the Effect superclass, as well as a destination point, tracking for
	 * whether or not they've been updated, and a list of particle objects
	 * associated with this current Spray attack.
	 * 
	 * @param state       the current GameState object
	 * @param origin      the point from which the spray emanates
	 * @param destination the point of the attacked enemy
	 */
	public EffectSpray(GameState state, Point origin, Point destination, TowerSpray parent)
	{
		super(state, origin);
		this.destination = destination;
		particles = new ArrayList<EffectSprayParticle>(5);
		firstUpdate = true;
		this.parent = parent;
	}

	/**
	 * Updates the spray's info (mainly lifetime). Particle objects
	 * will be created on the the first update, and updated in ensuing
	 * updates. Also deals with timing out.
	 * 
	 * @param timeElapsed the number of seconds elapsed since the last update
	 */
	public void update(double timeElapsed)
	{
		if (firstUpdate)
		{
			generateParticles();
		}
		
		for (EffectSprayParticle p : particles)
		{
			p.update(timeElapsed);
		}
		
		lifeTime += timeElapsed;
		if (lifeTime > 0.25)
		{
			state.removeGameObject(this);
		}
	}

	/**
	 * Draws each EffectSprayParticle object in the list of particles.
	 */
	public void draw(Graphics g, GameView view)
	{
		for (EffectSprayParticle p : particles)
		{
			p.draw(g, view);
		}
	}

	/**
	 * Generates 4 EffectSprayParticle objects, their trajectory angles spaced 0.3
	 * radians apart, with their trajectories centered toward the enemy being
	 * attacked.
	 */
	public void generateParticles()
	{
		double dx = origin.x - destination.x;
		double dy = origin.y - destination.y;
		double radius = Math.sqrt(dx*dx + dy*dy);
		double angle = Math.acos(Math.abs(dx) / radius) - 0.45;
		
		for (int i = 0; i < 4; i++)
		{
			particles.add(EffectSprayParticle.make(state, origin, destination, angle, parent, new Password()));
			angle += 0.3;
		}
		
		firstUpdate = false;
	}

	/**
	 * Empty class, used only to restrict creation of EffectSprayParticle objects.
	 * An object of this class is required in order to build an EffectSprayParticle
	 * object.
	 * 
	 * @author Caden Erickson
	 * @version December 02, 2021
	 */
	protected class Password
	{
		private Password() { }
	}
}
