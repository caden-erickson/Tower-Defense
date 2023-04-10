package enemy;

import effect.EffectSplat;
import game.GameState;

/**
 * An <code>EnemyPink</code> object represents a pink germ drawn on the
 * viewable window.
 * 
 * Objects of this class will store data about the germ such as its position,
 * size, velocity, and strength, and will have functionality to draw the germ
 * and incrementally update its position.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class EnemyPink extends Enemy
{
	// Fields

	/**
	 * Pink Enemy constructor. Objects built of this class will have functionality
	 * given by the Enemy superclass, as well as values for name, speed, and size.
	 * 
	 * @param percentTraveled how far along the path the germ has gone, given as a
	 *                        percentage between 0.0 and 1.0
	 * @param state           the current GameState object
	 */
	public EnemyPink(double percentTraveled, GameState state)
	{
		super(percentTraveled, state);
		name = "germ_pink.png";
		velocity = 0.040;
		size = 20;
		potency = -1;
	}

	/**
	 * When a pink enemy dies, it will give the user 1 credit, add a splat effect,
	 * and then erase itself.
	 */
	public void die()
	{
		/*
		 * When this condition wasn't here, two spray particles could call the die
		 * function on the same enemy and these statements would get called twice,
		 * leading to the enemy count decreasing by 2 for 1 enemy killed.
		 */
		if (!state.alreadyQueuedToDie(this))
		{
			state.updateCredits(1);
			state.decrementEnemyCount();
			state.addGameObject(new EffectSplat(state, position));
			state.removeGameObject(this);
		}
	}
}
