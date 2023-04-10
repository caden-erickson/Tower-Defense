package effect;

import java.awt.Point;

import game.*;

/**
 * The <code>Effect</code> superclass contains fields and methods used by all
 * subclasses that extend this class.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public abstract class Effect implements Animatable
{
	// Superclass fields
	protected GameState state;
	protected Point origin;

	/**
	 * Effect constructor. Objects built of this superclass will have a Point from
	 * which their animation originates. They will also have access to the GameState
	 * object to be able to call relevant methods on themselves.
	 * 
	 * @param state  the current GameState object
	 * @param origin the center point of the drawn effect
	 */
	public Effect(GameState state, Point origin)
	{
		this.state = state;
		this.origin = origin;
	}
}
